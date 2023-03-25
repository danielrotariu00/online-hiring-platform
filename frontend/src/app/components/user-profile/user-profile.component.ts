import { Component, Inject, LOCALE_ID, OnInit, ViewChild } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { Router } from "@angular/router";
import { first } from "rxjs";
import { EducationalInstitution } from "src/app/models/educational_institution";
import { UserEducationalExperience } from "src/app/models/user_educational_experience";
import { City, Company, Country, User, UserDetails } from "../../models";
import { AccountService, DatabaseService } from "../../services";
import { DOCUMENT, formatDate } from "@angular/common";
import { UserProfessionalExperience } from "src/app/models/user_professional_experience";
import { UserSkill } from "src/app/models/user_skill";
import { Skill } from "src/app/models/skill";
import { UserLanguage } from "src/app/models/user_language";
import { Language } from "src/app/models/language";
import { UserProject } from "src/app/models/user_project";
import { LanguageLevel } from "src/app/models/language_level";
import { FileUpload } from "primeng/fileupload";

@Component({
  selector: "app-user-profile",
  templateUrl: "./user-profile.component.html",
  styleUrls: ["./user-profile.component.scss"],
})
export class UserProfileComponent implements OnInit {
  loggedUser?: User | null;
  isOwner: boolean = false;

  userId: number;
  userDetails: UserDetails;
  userProfessionalExperienceList: UserProfessionalExperience[] = [];
  userEducationalExperienceList: UserEducationalExperience[] = [];
  userSkills: UserSkill[] = [];
  userProjects: UserProject[] = [];
  userLanguages: UserLanguage[] = [];

  displayUserDetailsForm: boolean = false;
  firstName: string;
  lastName: string;
  phoneNumber: string;
  countries: Country[];
  selectedCountry: Country;
  cities: City[];
  selectedCity: City;
  profileDescription: string;
  @ViewChild(FileUpload) imageUpload!: FileUpload;

  displayUserProfessionalExperienceForm: boolean = false;
  companies: Company[];
  selectedCompany: Company;
  jobTitle: string;
  startDate: Date;
  endDate: Date;
  description: string;

  displayUserEducationalExperienceForm: boolean = false;
  educationalInstitutions: EducationalInstitution[];
  selectedEducationalInstitution: EducationalInstitution;
  speciality: string;
  title: string;
  edStartDate: Date;
  edEndDate: Date;
  edDescription: string;

  displayUserSkillForm: boolean = false;
  skills: Skill[];
  selectedSkill: Skill;

  displayUserProjectForm: boolean = false;
  pName: string;
  pStartDate: Date;
  pEndDate: Date;
  pDescription: string;

  displayUserLanguageForm: boolean = false;
  languages: Language[];
  selectedLanguage: Language;
  languageLevels: LanguageLevel[];
  selectedLanguageLevel: LanguageLevel;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private accountService: AccountService,
    private databaseService: DatabaseService,
    @Inject(LOCALE_ID) private locale: string,
    @Inject(DOCUMENT) private document: Document
  ) {}

  ngOnInit(): void {
    this.accountService.user.subscribe((x) => (this.loggedUser = x));
    this.userId = parseInt(this.route.snapshot.paramMap.get("id")!, 10);
    this.isOwner = this.loggedUser.id == this.userId;

    this.databaseService
      .getUserDetails(this.userId)
      .subscribe((userDetails) => {
        this.databaseService
          .getCityById(userDetails.cityId)
          .subscribe((city: City) => {
            userDetails.city = city;

            this.databaseService
              .getCountryById(userDetails.city.countryId)
              .subscribe((country: Country) => {
                userDetails.country = country;
              });
          });
        this.userDetails = userDetails;
      });

    this.databaseService
      .getUserProfessionalExperience(this.userId)
      .subscribe((userProfessionalExperienceList) => {
        this.userProfessionalExperienceList =
          userProfessionalExperienceList.map((userProfessionalExperience) => {
            this.databaseService
              .getCompanyById(userProfessionalExperience.companyId)
              .subscribe((company: Company) => {
                userProfessionalExperience.company = company;
              });

            userProfessionalExperience.formattedStartDate = formatDate(
              userProfessionalExperience.startDate,
              "d MMM y",
              this.locale
            );

            userProfessionalExperience.formattedEndDate = formatDate(
              userProfessionalExperience.endDate,
              "d MMM y",
              this.locale
            );

            return userProfessionalExperience;
          });
      });

    this.databaseService
      .getUserEducationalExperience(this.userId)
      .subscribe((userEducationalExperienceList) => {
        this.userEducationalExperienceList = userEducationalExperienceList.map(
          (userEducationalExperience) => {
            this.databaseService
              .getEducationalInstitutionById(
                userEducationalExperience.educationalInstitutionId
              )
              .subscribe((educationalInstitution: EducationalInstitution) => {
                userEducationalExperience.educationalInstitution =
                  educationalInstitution;
              });

            userEducationalExperience.formattedStartDate = formatDate(
              userEducationalExperience.startDate,
              "d MMM y",
              this.locale
            );

            userEducationalExperience.formattedEndDate = formatDate(
              userEducationalExperience.endDate,
              "d MMM y",
              this.locale
            );

            return userEducationalExperience;
          }
        );
      });

    this.databaseService.getUserSkills(this.userId).subscribe((userSkills) => {
      this.userSkills = userSkills.map((userSkill) => {
        this.databaseService
          .getSkillById(userSkill.skillId)
          .subscribe((skill: Skill) => {
            userSkill.skill = skill;
          });

        return userSkill;
      });
    });

    this.databaseService
      .getUserProjects(this.userId)
      .subscribe((userProjects) => {
        this.userProjects = userProjects.map((userProject) => {
          userProject.formattedStartDate = formatDate(
            userProject.startDate,
            "d MMM y",
            this.locale
          );

          userProject.formattedEndDate = formatDate(
            userProject.endDate,
            "d MMM y",
            this.locale
          );

          return userProject;
        });
      });

    this.databaseService
      .getUserLanguages(this.userId)
      .subscribe((userLanguages) => {
        this.userLanguages = userLanguages.map((userLanguage) => {
          this.databaseService
            .getLanguageById(userLanguage.languageId)
            .subscribe((language: Language) => {
              userLanguage.language = language;
            });

          this.databaseService
            .getLanguageLevelById(userLanguage.languageLevelId)
            .subscribe((languageLevel: LanguageLevel) => {
              userLanguage.languageLevel = languageLevel;
            });

          return userLanguage;
        });
      });

    this.databaseService.getCompanies().subscribe((companies: Company[]) => {
      this.companies = companies;
    });

    this.databaseService
      .getEducationalInstitutions()
      .subscribe((educationalInstitutions: EducationalInstitution[]) => {
        this.educationalInstitutions = educationalInstitutions;
      });
  }

  companyPhotoClick(companyId: number) {
    this.router.navigate([`/companies/${companyId}`]);
  }

  institutionPhotoClick(website: string) {
    this.document.location.href = website;
  }

  showUserDetailsForm() {
    this.displayUserDetailsForm = true;
    this.databaseService.getCountries().subscribe((countries: Country[]) => {
      this.countries = countries;
    });
    this.firstName = this.userDetails.firstName;
    this.lastName = this.userDetails.lastName;
    this.phoneNumber = this.userDetails.phoneNumber;
    this.selectedCountry = this.userDetails.country;
    this.databaseService
      .getCitiesByCountryId(this.selectedCountry.id)
      .subscribe((cities: City[]) => {
        this.cities = cities;
        this.selectedCity = this.userDetails.city;
      });
    this.profileDescription = this.userDetails.profileDescription;
  }

  showUserProfessionalExperienceForm() {
    this.displayUserProfessionalExperienceForm = true;
  }

  showUserEducationalExperienceForm() {
    this.displayUserEducationalExperienceForm = true;
  }

  showUserSkillForm() {
    this.displayUserSkillForm = true;
    this.databaseService.getSkills().subscribe((skills: Skill[]) => {
      this.skills = skills;
    });
  }

  showUserProjectForm() {
    this.displayUserProjectForm = true;
  }

  showUserLanguageForm() {
    this.displayUserLanguageForm = true;
    this.databaseService.getLanguages().subscribe((languages: Language[]) => {
      this.languages = languages;
    });
    this.databaseService
      .getLanguageLevels()
      .subscribe((languageLevels: LanguageLevel[]) => {
        this.languageLevels = languageLevels;
      });
  }

  saveUserDetails() {
    this.databaseService
      .saveUserDetails(
        this.loggedUser.id,
        this.firstName,
        this.lastName,
        this.phoneNumber,
        this.selectedCity.id,
        this.userDetails.address,
        this.profileDescription,
        this.userDetails.profilePictureUrl
      )
      .pipe(first())
      .subscribe({
        next: (userDetails: UserDetails) => {
          this.displayUserDetailsForm = false;
          this.databaseService
            .getCityById(userDetails.cityId)
            .subscribe((city: City) => {
              userDetails.city = city;

              this.databaseService
                .getCountryById(userDetails.city.countryId)
                .subscribe((country: Country) => {
                  userDetails.country = country;
                });
            });
          this.userDetails = userDetails;
        },
        error: (error) => {
          console.log("error");
        },
      });

    if (this.imageUpload.hasFiles()) {
      this.databaseService
        .uploadUserImage(this.loggedUser.id, this.imageUpload)
        .pipe(first())
        .subscribe({
          next: (event: any) => {
            console.log("success");
          },
          error: (error) => {
            console.log("error");
          },
        });
    }
  }

  addUserProfessionalExperience() {
    this.databaseService
      .addUserProfessionalExperience(
        this.loggedUser.id,
        this.selectedCompany.id,
        this.jobTitle,
        this.startDate,
        this.endDate,
        this.description
      )
      .pipe(first())
      .subscribe({
        next: (userProfessionalExperience: UserProfessionalExperience) => {
          this.displayUserProfessionalExperienceForm = false;
          this.databaseService
            .getCompanyById(userProfessionalExperience.companyId)
            .subscribe((company: Company) => {
              userProfessionalExperience.company = company;
            });

          userProfessionalExperience.formattedStartDate = formatDate(
            userProfessionalExperience.startDate,
            "d MMM y",
            this.locale
          );

          userProfessionalExperience.formattedEndDate = formatDate(
            userProfessionalExperience.endDate,
            "d MMM y",
            this.locale
          );
          this.userProfessionalExperienceList.push(
            ...[userProfessionalExperience]
          );
        },
        error: (error) => {
          console.log("error");
        },
      });
  }

  deleteUserProfessionalExperience(id: number) {
    this.databaseService
      .deleteUserProfessionalExperience(id)
      .pipe(first())
      .subscribe({
        next: () => {
          console.log("success");
          this.userProfessionalExperienceList =
            this.userProfessionalExperienceList.filter(
              (userProfessionalExperience) =>
                userProfessionalExperience.id != id
            );
        },
        error: (error) => {
          console.log("error");
        },
      });
  }

  addUserEducationalExperience() {
    this.databaseService
      .addUserEducationalExperience(
        this.loggedUser.id,
        this.selectedEducationalInstitution.id,
        this.speciality,
        this.title,
        this.edStartDate,
        this.edEndDate,
        this.edDescription
      )
      .pipe(first())
      .subscribe({
        next: (userEducationalExperience: UserEducationalExperience) => {
          this.displayUserEducationalExperienceForm = false;
          this.databaseService
            .getEducationalInstitutionById(
              userEducationalExperience.educationalInstitutionId
            )
            .subscribe((educationalInstitution: EducationalInstitution) => {
              userEducationalExperience.educationalInstitution =
                educationalInstitution;
            });

          userEducationalExperience.formattedStartDate = formatDate(
            userEducationalExperience.startDate,
            "d MMM y",
            this.locale
          );

          userEducationalExperience.formattedEndDate = formatDate(
            userEducationalExperience.endDate,
            "d MMM y",
            this.locale
          );
          this.userEducationalExperienceList.push(
            ...[userEducationalExperience]
          );
        },
        error: (error) => {
          console.log("error");
        },
      });
  }

  deleteUserEducationalExperience(id: number) {
    this.databaseService
      .deleteUserEducationalExperience(id)
      .pipe(first())
      .subscribe({
        next: () => {
          console.log("success");
          this.userEducationalExperienceList =
            this.userEducationalExperienceList.filter(
              (userEducationalExperience) => userEducationalExperience.id != id
            );
        },
        error: (error) => {
          console.log("error");
        },
      });
  }

  addUserSkill() {
    this.databaseService
      .addUserSkill(this.loggedUser.id, this.selectedSkill.id)
      .pipe(first())
      .subscribe({
        next: (userSkill: UserSkill) => {
          this.displayUserSkillForm = false;
          this.databaseService
            .getSkillById(userSkill.skillId)
            .subscribe((skill: Skill) => {
              userSkill.skill = skill;
            });

          this.userSkills.push(...[userSkill]);
        },
        error: (error) => {
          console.log("error");
        },
      });
  }

  deleteUserSkill(skillId: number) {
    this.databaseService
      .deleteUserSkill(this.userId, skillId)
      .pipe(first())
      .subscribe({
        next: () => {
          console.log("success");
          this.userSkills = this.userSkills.filter(
            (userSkill) => userSkill.skillId != skillId
          );
        },
        error: (error) => {
          console.log("error");
        },
      });
  }

  addUserProject() {
    this.databaseService
      .addUserProject(
        this.loggedUser.id,
        this.pName,
        this.pStartDate,
        this.pEndDate,
        this.pDescription
      )
      .pipe(first())
      .subscribe({
        next: (userProject: UserProject) => {
          this.displayUserProjectForm = false;

          userProject.formattedStartDate = formatDate(
            userProject.startDate,
            "d MMM y",
            this.locale
          );

          userProject.formattedEndDate = formatDate(
            userProject.endDate,
            "d MMM y",
            this.locale
          );
          this.userProjects.push(...[userProject]);
        },
        error: (error) => {
          console.log("error");
        },
      });
  }

  deleteUserProject(id: number) {
    this.databaseService
      .deleteUserProject(id)
      .pipe(first())
      .subscribe({
        next: () => {
          console.log("success");
          this.userProjects = this.userProjects.filter(
            (userProject) => userProject.id != id
          );
        },
        error: (error) => {
          console.log("error");
        },
      });
  }

  addUserLanguage() {
    this.databaseService
      .addUserLanguage(
        this.loggedUser.id,
        this.selectedLanguage.id,
        this.selectedLanguageLevel.id
      )
      .pipe(first())
      .subscribe({
        next: (userLanguage: UserLanguage) => {
          this.displayUserLanguageForm = false;
          this.databaseService
            .getLanguageById(userLanguage.languageId)
            .subscribe((language: Language) => {
              userLanguage.language = language;
            });

          this.databaseService
            .getLanguageLevelById(userLanguage.languageLevelId)
            .subscribe((languageLevel: LanguageLevel) => {
              userLanguage.languageLevel = languageLevel;
            });

          this.userLanguages.push(...[userLanguage]);
        },
        error: (error) => {
          console.log("error");
        },
      });
  }

  deleteUserLanguage(languageId: number) {
    this.databaseService
      .deleteUserLanguage(this.userId, languageId)
      .pipe(first())
      .subscribe({
        next: () => {
          console.log("success");
          this.userLanguages = this.userLanguages.filter(
            (userLanguage) => userLanguage.languageId != languageId
          );
        },
        error: (error) => {
          console.log("error");
        },
      });
  }

  onChangeCountry(event: any) {
    this.cities = [];

    if (this.selectedCountry) {
      this.databaseService
        .getCitiesByCountryId(this.selectedCountry.id)
        .subscribe((cities: City[]) => {
          this.cities.push(...cities);
        });
    } else {
    }
  }

  // public getLinkPicture(pictureUrl: string) {
  //   let timeStamp = new Date().getTime();
  //   return pictureUrl + "?" + timeStamp;
  // }
}

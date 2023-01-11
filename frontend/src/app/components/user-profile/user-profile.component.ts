import { Component, Inject, LOCALE_ID, OnInit } from "@angular/core";
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

  displayUserProfessionalExperienceForm: boolean = false;
  companies: Company[];
  selectedCompany: Company;
  jobTitle: string;
  startDate: Date;
  endDate: Date;
  description: string;

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
  }

  companyPhotoClick(companyId: number) {
    this.router.navigate([`/companies/${companyId}`]);
  }

  institutionPhotoClick(website: string) {
    this.document.location.href = website;
  }

  showUserProfessionalExperienceForm() {
    this.displayUserProfessionalExperienceForm = true;
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
          console.log("success");
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

  public getLinkPicture(pictureUrl: string) {
    let timeStamp = new Date().getTime();
    return pictureUrl + "?" + timeStamp;
  }
}

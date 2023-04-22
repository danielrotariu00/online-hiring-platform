package com.licenta.databasemicroservice.business.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.licenta.databasemicroservice.persistence.entity.City;
import com.licenta.databasemicroservice.persistence.entity.Country;
import com.licenta.databasemicroservice.persistence.entity.EducationalInstitution;
import com.licenta.databasemicroservice.persistence.entity.ExperienceLevel;
import com.licenta.databasemicroservice.persistence.entity.Industry;
import com.licenta.databasemicroservice.persistence.entity.JobStatus;
import com.licenta.databasemicroservice.persistence.entity.JobType;
import com.licenta.databasemicroservice.persistence.entity.Language;
import com.licenta.databasemicroservice.persistence.entity.LanguageLevel;
import com.licenta.databasemicroservice.persistence.entity.Skill;
import com.licenta.databasemicroservice.persistence.entity.WorkType;
import com.licenta.databasemicroservice.persistence.repository.CityRepository;
import com.licenta.databasemicroservice.persistence.repository.CountryRepository;
import com.licenta.databasemicroservice.persistence.repository.EducationalInstitutionRepository;
import com.licenta.databasemicroservice.persistence.repository.ExperienceLevelRepository;
import com.licenta.databasemicroservice.persistence.repository.IndustryRepository;
import com.licenta.databasemicroservice.persistence.repository.JobStatusRepository;
import com.licenta.databasemicroservice.persistence.repository.JobTypeRepository;
import com.licenta.databasemicroservice.persistence.repository.LanguageLevelRepository;
import com.licenta.databasemicroservice.persistence.repository.LanguageRepository;
import com.licenta.databasemicroservice.persistence.repository.SkillRepository;
import com.licenta.databasemicroservice.persistence.repository.WorkTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DataLoader implements ApplicationRunner {
    private static final String INITIAL_DATA_PATH = "src/main/resources/load/";

    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private IndustryRepository industryRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private EducationalInstitutionRepository educationalInstitutionRepository;
    @Autowired
    private ExperienceLevelRepository experienceLevelRepository;
    @Autowired
    private JobStatusRepository jobStatusRepository;
    @Autowired
    private JobTypeRepository jobTypeRepository;
    @Autowired
    private WorkTypeRepository workTypeRepository;
    @Autowired
    private LanguageRepository languageRepository;
    @Autowired
    private LanguageLevelRepository languageLevelRepository;
    @Autowired
    private SkillRepository skillRepository;

    public void run(ApplicationArguments args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        if(countryRepository.findAll().size() == 0) {
            List<Country> countries = mapper.readValue(Paths.get(INITIAL_DATA_PATH + "countries.json").toFile(),
                    new TypeReference<List<Country>>() {
                    });
            countries.forEach(c -> c.setId(null));
            countryRepository.saveAllAndFlush(countries);
        }

        if(cityRepository.findAll().size() == 0) {
            List<JsonNode> cityNodes = mapper.readValue(Paths.get(INITIAL_DATA_PATH + "cities.json").toFile(),
                    new TypeReference<List<JsonNode>>(){});
            List<City> cities = cityNodes.stream().map(c -> City.builder()
                    .id(null)
                    .name(c.get("name").asText())
                    .country(countryRepository.findById(c.get("country_id").asInt()).get())
                    .build()
            ).collect(Collectors.toList());
            cityRepository.saveAllAndFlush(cities);
        }

        if(educationalInstitutionRepository.findAll().size() == 0) {
            List<EducationalInstitution> educationalInstitutions = mapper.readValue(Paths.get(INITIAL_DATA_PATH + "educational-institutions.json").toFile(),
                    new TypeReference<List<EducationalInstitution>>() {
                    });
            educationalInstitutions.forEach(e -> e.setId(null));
            educationalInstitutionRepository.saveAllAndFlush(educationalInstitutions);
        }

        if(experienceLevelRepository.findAll().size() == 0) {
            List<ExperienceLevel> experienceLevels = mapper.readValue(Paths.get(INITIAL_DATA_PATH + "experience-levels.json").toFile(),
                    new TypeReference<List<ExperienceLevel>>() {
                    });
            experienceLevels.forEach(e -> e.setId(null));
            experienceLevelRepository.saveAllAndFlush(experienceLevels);
        }

        if(industryRepository.findAll().size() == 0) {
            List<Industry> industries = mapper.readValue(Paths.get(INITIAL_DATA_PATH + "industries.json").toFile(),
                    new TypeReference<List<Industry>>() {
                    });
            industries.forEach(i -> i.setId(null));
            industryRepository.saveAllAndFlush(industries);
        }

        if(jobStatusRepository.findAll().size() == 0) {
            List<JobStatus> jobStatuses = mapper.readValue(Paths.get(INITIAL_DATA_PATH + "job-statuses.json").toFile(),
                    new TypeReference<List<JobStatus>>() {
                    });
            jobStatuses.forEach(j -> j.setId(null));
            jobStatusRepository.saveAllAndFlush(jobStatuses);
        }

        if(jobTypeRepository.findAll().size() == 0) {
            List<JobType> jobTypes = mapper.readValue(Paths.get(INITIAL_DATA_PATH + "job-types.json").toFile(),
                    new TypeReference<List<JobType>>() {
                    });
            jobTypes.forEach(j -> j.setId(null));
            jobTypeRepository.saveAllAndFlush(jobTypes);
        }

        if(workTypeRepository.findAll().size() == 0) {
            List<WorkType> workTypes = mapper.readValue(Paths.get(INITIAL_DATA_PATH + "work-types.json").toFile(),
                    new TypeReference<List<WorkType>>() {
                    });
            workTypes.forEach(j -> j.setId(null));
            workTypeRepository.saveAllAndFlush(workTypes);
        }

        if(languageRepository.findAll().size() == 0) {
            List<Language> languages = mapper.readValue(Paths.get(INITIAL_DATA_PATH + "languages.json").toFile(),
                    new TypeReference<List<Language>>() {
                    });
            languages.forEach(j -> j.setId(null));
            languageRepository.saveAllAndFlush(languages);
        }

        if(languageLevelRepository.findAll().size() == 0) {
            List<LanguageLevel> languageLevels = mapper.readValue(Paths.get(INITIAL_DATA_PATH + "language-levels.json").toFile(),
                    new TypeReference<List<LanguageLevel>>() {
                    });
            languageLevels.forEach(j -> j.setId(null));
            languageLevelRepository.saveAllAndFlush(languageLevels);
        }

        if(skillRepository.findAll().size() == 0) {
            List<Skill> skills = mapper.readValue(Paths.get(INITIAL_DATA_PATH + "skills.json").toFile(),
                    new TypeReference<List<Skill>>() {
                    });
            skills.forEach(j -> j.setId(null));
            skillRepository.saveAllAndFlush(skills);
        }
    }
}
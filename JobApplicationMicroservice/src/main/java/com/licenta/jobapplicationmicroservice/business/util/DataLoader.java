package com.licenta.jobapplicationmicroservice.business.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.licenta.jobapplicationmicroservice.persistence.document.JobApplicationStatus;
import com.licenta.jobapplicationmicroservice.persistence.repository.JobApplicationStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Component
public class DataLoader implements ApplicationRunner {
    private static final String INITIAL_DATA_PATH = "src/main/resources/load/";

    @Autowired
    private JobApplicationStatusRepository jobApplicationStatusRepository;

    public void run(ApplicationArguments args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        if(jobApplicationStatusRepository.findAll().size() == 0) {
            List<JobApplicationStatus> jobApplicationStatuses = mapper.readValue(Paths.get(INITIAL_DATA_PATH + "job-application-statuses.json").toFile(),
                    new TypeReference<List<JobApplicationStatus>>() {
                    });
            jobApplicationStatusRepository.saveAll(jobApplicationStatuses);
        }

    }
}
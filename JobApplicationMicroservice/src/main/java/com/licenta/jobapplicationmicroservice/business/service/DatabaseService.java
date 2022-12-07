package com.licenta.jobapplicationmicroservice.business.service;

import com.google.gson.Gson;
import com.licenta.jobapplicationmicroservice.business.interfaces.IDatabaseService;
import com.licenta.jobapplicationmicroservice.business.model.Job;
import com.licenta.jobapplicationmicroservice.business.util.errorhandler.RestTemplateResponseErrorHandler;
import com.licenta.jobapplicationmicroservice.business.util.exception.ExceptionWithStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DatabaseService implements IDatabaseService {

    private final RestTemplate restTemplate;
    private final String URL_FORMAT = "http://localhost:23050/%s/%s";

    @Autowired
    public DatabaseService() {
        restTemplate = new RestTemplateBuilder()
                .errorHandler(new RestTemplateResponseErrorHandler())
                .build();
    }

    @Override
    public void verifyUserExists(Long userId) {
        ResponseEntity<String> response = restTemplate.getForEntity(String.format(URL_FORMAT, "users", userId), String.class);

        if(response.getStatusCode() != HttpStatus.OK)
            throw new ExceptionWithStatus(response.getBody(), response.getStatusCode());
    }

    @Override
    public Job getJob(Long jobId) {
        ResponseEntity<String> response = restTemplate.getForEntity(String.format(URL_FORMAT, "jobs", jobId), String.class);

        if(response.getStatusCode() != HttpStatus.OK)
            throw new ExceptionWithStatus(response.getBody(), response.getStatusCode());

        Gson gson = new Gson();
        return gson.fromJson(response.getBody(), Job.class);
    }
}

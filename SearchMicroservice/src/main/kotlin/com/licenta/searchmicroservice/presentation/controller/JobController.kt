package com.licenta.searchmicroservice.presentation.controller

import com.licenta.searchmicroservice.business.model.Job
import com.licenta.searchmicroservice.business.model.JobQuery
import com.licenta.searchmicroservice.business.service.JobFilterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@Validated
@RestController
@RequestMapping(value = ["/jobs"])
class JobController {

    @Autowired
    private lateinit var jobFilterService: JobFilterService

    @GetMapping
    fun getNewsfeed(
        @RequestParam(defaultValue = "") title: List<String>,
        @RequestParam(defaultValue = "") cityId: List<Int>,
        @RequestParam(defaultValue = "") countryId: List<Int>,
        @RequestParam(defaultValue = "") companyId: List<String>,
        @RequestParam(defaultValue = "") industryId: List<Int>,
        @RequestParam(defaultValue = "") companyIndustryId: List<Int>,
        @RequestParam(defaultValue = "") workTypeId: List<Int>,
        @RequestParam(defaultValue = "") jobTypeId: List<Int>,
        @RequestParam(defaultValue = "") experienceLevelId: List<Int>,
        @RequestParam(defaultValue = "") descriptionKeyword: List<String>,
        @RequestParam(defaultValue = "2000-01-01 00:00:00") postedSince: String
    ): List<Job> {

        val jobQuery = JobQuery(
            title,
            cityId,
            countryId,
            companyId,
            industryId,
            companyIndustryId,
            workTypeId,
            jobTypeId,
            experienceLevelId,
            descriptionKeyword,
            postedSince
        )

        return jobFilterService.getFilteredJobs(jobQuery)

        //todo: create date converter, refactor spark service, add interfaces, exception translator, validation,
    }
}
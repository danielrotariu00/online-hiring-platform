package com.licenta.searchmicroservice.presentation.controller

import com.licenta.searchmicroservice.business.`interface`.IJobFilterService
import com.licenta.searchmicroservice.business.model.Job
import com.licenta.searchmicroservice.business.model.JobQuery
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


@Validated
@RestController
@CrossOrigin(origins = ["http://localhost:4200"], maxAge = 3600)
@RequestMapping(value = ["/jobs"])
class JobController {

    @Autowired
    private lateinit var jobFilterService: IJobFilterService

    @GetMapping
    fun getNewsfeed(
        @RequestParam(defaultValue = "") title: List<String>,
        @RequestParam(defaultValue = "") cityId: List<Int>,
        @RequestParam(defaultValue = "") countryId: List<Int>,
        @RequestParam(defaultValue = "") companyId: List<Long>,
        @RequestParam(defaultValue = "") industryId: List<Int>,
        @RequestParam(defaultValue = "") companyIndustryId: List<Long>,
        @RequestParam(defaultValue = "") workTypeId: List<Int>,
        @RequestParam(defaultValue = "") jobTypeId: List<Int>,
        @RequestParam(defaultValue = "") experienceLevelId: List<Int>,
        @RequestParam(defaultValue = "") descriptionKeyword: List<String>,
        @RequestParam(defaultValue = "2000-01-01 00:00:00") postedSince: String,
        @RequestParam(defaultValue = "") jobStatusIdList: List<Int>,
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
            postedSince,
            jobStatusIdList
        )

        return jobFilterService.getFilteredJobs(jobQuery)
    }
}
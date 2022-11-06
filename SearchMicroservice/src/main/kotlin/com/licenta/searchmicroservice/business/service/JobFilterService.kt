package com.licenta.searchmicroservice.business.service

import com.licenta.searchmicroservice.business.model.Job
import com.licenta.searchmicroservice.business.model.JobQuery
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class JobFilterService{

    @Autowired
    private lateinit var databaseService: DatabaseService
    @Autowired
    private lateinit var sparkService: SparkService

    fun getFilteredJobs(jobQuery: JobQuery) : List<Job> {
        val jobList = databaseService.getJobs()

        return sparkService.getFilteredJobs(jobList, jobQuery)
    }
}
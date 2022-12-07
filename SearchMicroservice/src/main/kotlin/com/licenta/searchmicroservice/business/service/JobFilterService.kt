package com.licenta.searchmicroservice.business.service

import com.licenta.searchmicroservice.business.`interface`.IDatabaseService
import com.licenta.searchmicroservice.business.`interface`.IJobFilterService
import com.licenta.searchmicroservice.business.model.Job
import com.licenta.searchmicroservice.business.model.JobQuery
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class JobFilterService: IJobFilterService {

    @Autowired
    private lateinit var databaseService: IDatabaseService
    @Autowired
    private lateinit var sparkService: SparkService

    override fun getFilteredJobs(jobQuery: JobQuery) : List<Job> {
        val jobList = databaseService.getJobs()

        return sparkService.getFilteredJobs(jobList, jobQuery)
    }
}
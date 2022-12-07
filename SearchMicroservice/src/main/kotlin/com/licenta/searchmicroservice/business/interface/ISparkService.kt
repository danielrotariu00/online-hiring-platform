package com.licenta.searchmicroservice.business.`interface`

import com.licenta.searchmicroservice.business.model.Job
import com.licenta.searchmicroservice.business.model.JobQuery

interface ISparkService {
    fun getFilteredJobs(jobList: List<Job>, jobQuery: JobQuery): List<Job>
}
package com.licenta.searchmicroservice.business.`interface`

import com.licenta.searchmicroservice.business.model.Job
import com.licenta.searchmicroservice.business.model.JobQuery

interface IJobFilterService {
    fun getFilteredJobs(jobQuery: JobQuery): List<Job>
}
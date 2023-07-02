package com.licenta.searchmicroservice.business.interfaces

import com.licenta.searchmicroservice.business.model.JobDTO
import com.licenta.searchmicroservice.business.model.JobQuery

interface ISparkService {
    fun getFilteredJobs(jobDTOList: List<JobDTO>, jobQuery: JobQuery): List<JobDTO>
}
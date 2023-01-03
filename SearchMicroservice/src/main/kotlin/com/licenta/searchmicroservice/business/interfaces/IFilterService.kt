package com.licenta.searchmicroservice.business.interfaces

import com.licenta.searchmicroservice.business.model.JobQuery
import com.licenta.searchmicroservice.business.model.JobQueryResponse

interface IFilterService {
    fun getFilteredJobs(jobQuery: JobQuery, page: Int, size: Int): JobQueryResponse
}
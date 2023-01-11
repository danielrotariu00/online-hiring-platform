package com.licenta.searchmicroservice.business.interfaces

import com.licenta.searchmicroservice.business.model.JobQuery
import com.licenta.searchmicroservice.business.model.JobQueryResponse

interface IFilterService {
    fun getFilteredJobs(jobQuery: JobQuery, cached: Boolean, page: Int, size: Int): JobQueryResponse
}
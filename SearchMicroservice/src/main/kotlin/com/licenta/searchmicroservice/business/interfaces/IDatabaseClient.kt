package com.licenta.searchmicroservice.business.interfaces

import com.licenta.searchmicroservice.business.model.JobDTO

interface IDatabaseClient {
    fun getJobs(): List<JobDTO>
}
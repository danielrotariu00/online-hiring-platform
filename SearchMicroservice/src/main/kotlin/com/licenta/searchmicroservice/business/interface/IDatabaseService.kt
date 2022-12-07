package com.licenta.searchmicroservice.business.`interface`

import com.licenta.searchmicroservice.business.model.Job

interface IDatabaseService {
    fun getJobs(): List<Job>
}
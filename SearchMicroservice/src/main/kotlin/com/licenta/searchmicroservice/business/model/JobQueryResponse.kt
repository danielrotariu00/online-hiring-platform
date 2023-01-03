package com.licenta.searchmicroservice.business.model

import java.io.Serializable

data class JobQueryResponse(
    val jobList: List<JobDTO>,
    val totalElements: Long,
    val totalPages: Int
): Serializable
package com.licenta.newsfeedmicroservice.business.model

import java.io.Serializable

data class JobQueryResponse(
    val jobList: List<Job>,
    val totalElements: Long,
    val totalPages: Int
): Serializable
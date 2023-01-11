package com.licenta.newsfeed.business.model

import com.licenta.newsfeedmicroservice.business.model.Job
import java.io.Serializable

data class JobQueryResponse(
    val jobList: List<Job>,
    val totalElements: Long,
    val totalPages: Int
): Serializable
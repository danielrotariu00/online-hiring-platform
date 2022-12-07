package com.licenta.newsfeedmicroservice.business.model

import java.io.Serializable

data class Job(
    val id: Long,
    val title: String,
    val companyId: Long,
    val cityId: Int,
    val countryId: Int,
    val workTypeId: Int, // on-site, remote, hybrid
    val jobTypeId: Int, // full-time, part-time, seasonal, temporary
    val experienceLevelId: Int, // internship, entry-level, associate, mid-senior level, director
    val companyIndustryId: Long, // IT, etc
    val industryId: Int, // IT, etc
    val description: String,
    val postedAt: String
): Serializable
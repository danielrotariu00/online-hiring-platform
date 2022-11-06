package com.licenta.newsfeedmicroservice.business.model

import java.io.Serializable

data class Job(
    val id: String,
    val title: String,
    val companyId: String,
    val cityId: Int,
    val countryId: Int,
    val workTypeId: Int, // on-site, remote, hybrid
    val jobTypeId: Int, // full-time, part-time, seasonal, temporary
    val experienceLevelId: Int, // internship, entry-level, associate, mid-senior level, director
    val companyIndustryId: Int, // IT, etc
    val industryId: Int, // IT, etc
    val description: String,
    val postedAt: String
): Serializable
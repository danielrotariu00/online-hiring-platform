package com.licenta.newsfeedmicroservice.business.model

import java.io.Serializable

data class EntryResponse(
    var jobId: Long,
    var jobTitle: String,
    var companyName: String,
    var companyLogoURL: String,
    var cityName: String,
    var countryName: String,
    var workType: String,
    var postedAt: String,
): Serializable
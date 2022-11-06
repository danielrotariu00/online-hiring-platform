package com.licenta.newsfeedmicroservice.business.model

import java.io.Serializable

data class EntryResponse(
    var jobId: String,
    var jobTitle: String,
    var companyId: String,
    var cityId: Int,
    var countryId: Int,
    var workTypeId: Int,
    var postedAt: String,
): Serializable
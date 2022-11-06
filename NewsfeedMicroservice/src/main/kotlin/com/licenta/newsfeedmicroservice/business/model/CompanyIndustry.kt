package com.licenta.newsfeedmicroservice.business.model

import java.io.Serializable

data class CompanyIndustry(
    val id: String,
    val industryId: String,
    val companyId: String
): Serializable
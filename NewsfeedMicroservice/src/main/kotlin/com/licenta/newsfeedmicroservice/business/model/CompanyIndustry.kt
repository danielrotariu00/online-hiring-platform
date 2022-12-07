package com.licenta.newsfeedmicroservice.business.model

import java.io.Serializable

data class CompanyIndustry(
    val id: Long,
    val industryId: Int,
    val companyId: Long
): Serializable
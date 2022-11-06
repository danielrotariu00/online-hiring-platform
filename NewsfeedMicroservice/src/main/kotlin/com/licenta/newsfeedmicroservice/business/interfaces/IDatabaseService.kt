package com.licenta.newsfeedmicroservice.business.interfaces

import com.licenta.newsfeedmicroservice.business.model.CompanyIndustry

interface IDatabaseService {

    fun getCompanyIndustriesFollowedByUser(userId: String): List<CompanyIndustry>
}
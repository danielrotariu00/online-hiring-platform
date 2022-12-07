package com.licenta.newsfeedmicroservice.business.interfaces

import com.licenta.newsfeedmicroservice.business.model.*

interface IDatabaseService {

    fun getCompanyIndustriesFollowedByUser(userId: Long): List<CompanyIndustry>
    fun getCityById(cityId: Int): City
    fun getCompanyById(companyId: Long): Company
    fun getCountryById(countryId: Int): Country
    fun getWorkTypeById(workTypeId: Int): WorkType
}
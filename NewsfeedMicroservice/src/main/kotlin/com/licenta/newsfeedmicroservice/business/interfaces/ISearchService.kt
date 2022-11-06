package com.licenta.newsfeedmicroservice.business.interfaces

import com.licenta.newsfeedmicroservice.business.model.CompanyIndustry
import com.licenta.newsfeedmicroservice.business.model.Job

interface ISearchService {

    fun getJobs(companyIndustry: CompanyIndustry, postedSince: String): List<Job>
}
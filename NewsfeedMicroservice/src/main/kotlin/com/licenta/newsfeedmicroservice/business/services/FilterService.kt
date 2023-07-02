package com.licenta.newsfeedmicroservice.business.services

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.licenta.newsfeedmicroservice.business.model.JobQueryResponse
import com.licenta.newsfeedmicroservice.business.interfaces.IFilterService
import com.licenta.newsfeedmicroservice.business.model.CompanyIndustry
import com.licenta.newsfeedmicroservice.business.model.Job
import com.licenta.newsfeedmicroservice.business.util.exception.ExceptionWithStatus
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class FilterService: IFilterService {

    override fun getJobs(companyIndustry: CompanyIndustry, postedSince: String): List<Job> {
        val response = khttp.get("http://${System.getenv("FILTER_MICROSERVICE_HOST")}:${System.getenv("FILTER_MICROSERVICE_PORT")}/api/jobs?companyIndustryId=${companyIndustry.id}&postedSince=$postedSince")

        if (response.statusCode != 200) {
            throw ExceptionWithStatus(response.text, HttpStatus.valueOf(response.statusCode))
        }

        val typeToken = object : TypeToken<JobQueryResponse>() {}.type
        val jobQueryResponse: JobQueryResponse = Gson().fromJson(response.text, typeToken)
        return jobQueryResponse.jobList
    }
}
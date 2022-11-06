package com.licenta.newsfeedmicroservice.business.services

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.licenta.newsfeedmicroservice.business.interfaces.IDatabaseService
import com.licenta.newsfeedmicroservice.business.model.CompanyIndustry
import com.licenta.newsfeedmicroservice.business.util.exception.ExceptionWithStatus
import khttp.get
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class DatabaseService: IDatabaseService {
    override fun getCompanyIndustriesFollowedByUser(userId: String): List<CompanyIndustry> {
        // TODO: replace localhost with env variable for database microservice
        val response = get("http://localhost:23050/users/${userId}/followed-company-industries")

        if (response.statusCode != 200) {
            throw ExceptionWithStatus(response.text, HttpStatus.valueOf(response.statusCode))
        }

        val typeToken = object : TypeToken<List<CompanyIndustry>>() {}.type

        return Gson().fromJson(response.text, typeToken)
    }
}
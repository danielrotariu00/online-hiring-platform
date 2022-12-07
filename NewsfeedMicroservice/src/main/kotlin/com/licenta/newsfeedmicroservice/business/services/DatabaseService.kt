package com.licenta.newsfeedmicroservice.business.services

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.licenta.newsfeedmicroservice.business.interfaces.IDatabaseService
import com.licenta.newsfeedmicroservice.business.model.*
import com.licenta.newsfeedmicroservice.business.util.exception.ExceptionWithStatus
import khttp.get
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class DatabaseService: IDatabaseService {
    override fun getCompanyIndustriesFollowedByUser(userId: Long): List<CompanyIndustry> {
        // TODO: replace localhost with env variable for database microservice
        val response = get("http://localhost:23050/users/${userId}/followed-company-industries")

        if (response.statusCode != 200) {
            throw ExceptionWithStatus(response.text, HttpStatus.valueOf(response.statusCode))
        }

        val typeToken = object : TypeToken<List<CompanyIndustry>>() {}.type

        return Gson().fromJson(response.text, typeToken)
    }

    override fun getCityById(cityId: Int): City {
        // TODO: replace localhost with env variable for database microservice
        val response = get("http://localhost:23050/cities/${cityId}")

        if (response.statusCode != 200) {
            throw ExceptionWithStatus(response.text, HttpStatus.valueOf(response.statusCode))
        }

        val typeToken = object : TypeToken<City>() {}.type

        return Gson().fromJson(response.text, typeToken)
    }

    override fun getCompanyById(companyId: Long): Company {
        // TODO: replace localhost with env variable for database microservice
        val response = get("http://localhost:23050/companies/${companyId}")

        if (response.statusCode != 200) {
            throw ExceptionWithStatus(response.text, HttpStatus.valueOf(response.statusCode))
        }

        val typeToken = object : TypeToken<Company>() {}.type

        return Gson().fromJson(response.text, typeToken)
    }

    override fun getCountryById(countryId: Int): Country {
        // TODO: replace localhost with env variable for database microservice
        val response = get("http://localhost:23050/countries/${countryId}")

        if (response.statusCode != 200) {
            throw ExceptionWithStatus(response.text, HttpStatus.valueOf(response.statusCode))
        }

        val typeToken = object : TypeToken<Country>() {}.type

        return Gson().fromJson(response.text, typeToken)
    }

    override fun getWorkTypeById(workTypeId: Int): WorkType {
        // TODO: replace localhost with env variable for database microservice
        val response = get("http://localhost:23050/work-types/${workTypeId}")

        if (response.statusCode != 200) {
            throw ExceptionWithStatus(response.text, HttpStatus.valueOf(response.statusCode))
        }

        val typeToken = object : TypeToken<WorkType>() {}.type

        return Gson().fromJson(response.text, typeToken)
    }
}
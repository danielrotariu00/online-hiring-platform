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
    private val baseUrl = "http://${System.getenv("DATABASE_MICROSERVICE_HOST")}:${System.getenv("DATABASE_MICROSERVICE_PORT")}/api"

    override fun getCompanyIndustriesFollowedByUser(userId: Long): List<CompanyIndustry> {
        val response = get("${baseUrl}/users/${userId}/followed-company-industries")

        if (response.statusCode != 200) {
            throw ExceptionWithStatus(response.text, HttpStatus.valueOf(response.statusCode))
        }

        val typeToken = object : TypeToken<List<CompanyIndustry>>() {}.type

        return Gson().fromJson(response.text, typeToken)
    }

    override fun getCityById(cityId: Int): City {
        val response = get("${baseUrl}/cities/${cityId}")

        if (response.statusCode != 200) {
            throw ExceptionWithStatus(response.text, HttpStatus.valueOf(response.statusCode))
        }

        val typeToken = object : TypeToken<City>() {}.type

        return Gson().fromJson(response.text, typeToken)
    }

    override fun getCompanyById(companyId: Long): Company {
        val response = get("${baseUrl}/companies/${companyId}")

        if (response.statusCode != 200) {
            throw ExceptionWithStatus(response.text, HttpStatus.valueOf(response.statusCode))
        }

        val typeToken = object : TypeToken<Company>() {}.type

        return Gson().fromJson(response.text, typeToken)
    }

    override fun getCountryById(countryId: Int): Country {
        val response = get("${baseUrl}/countries/${countryId}")

        if (response.statusCode != 200) {
            throw ExceptionWithStatus(response.text, HttpStatus.valueOf(response.statusCode))
        }

        val typeToken = object : TypeToken<Country>() {}.type

        return Gson().fromJson(response.text, typeToken)
    }

    override fun getWorkTypeById(workTypeId: Int): WorkType {
        val response = get("${baseUrl}/work-types/${workTypeId}")

        if (response.statusCode != 200) {
            throw ExceptionWithStatus(response.text, HttpStatus.valueOf(response.statusCode))
        }

        val typeToken = object : TypeToken<WorkType>() {}.type

        return Gson().fromJson(response.text, typeToken)
    }
}
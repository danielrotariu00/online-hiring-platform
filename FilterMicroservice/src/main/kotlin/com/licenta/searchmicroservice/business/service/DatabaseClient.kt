package com.licenta.searchmicroservice.business.service

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.licenta.searchmicroservice.business.interfaces.IDatabaseClient
import com.licenta.searchmicroservice.business.model.JobDTO
import com.licenta.searchmicroservice.business.util.exception.ExceptionWithStatus
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class DatabaseClient: IDatabaseClient{

    override fun getJobs(): List<JobDTO> {
        val response = khttp.get("http://${System.getenv("DATABASE_MICROSERVICE_HOST")}:${System.getenv("DATABASE_MICROSERVICE_PORT")}/api/jobs")

        if (response.statusCode != 200) {
            throw ExceptionWithStatus(response.text, HttpStatus.valueOf(response.statusCode))
        }

        val typeToken = object : TypeToken<List<JobDTO>>() {}.type

        return Gson().fromJson(response.text, typeToken)
    }
}
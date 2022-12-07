package com.licenta.searchmicroservice.business.service

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.licenta.searchmicroservice.business.`interface`.IDatabaseService
import com.licenta.searchmicroservice.business.model.Job
import com.licenta.searchmicroservice.business.util.exception.ExceptionWithStatus
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class DatabaseService: IDatabaseService{

    override fun getJobs(): List<Job> {
        // TODO: replace localhost with env variable
        val response = khttp.get("http://localhost:23050/jobs")

        if (response.statusCode != 200) {
            throw ExceptionWithStatus(response.text, HttpStatus.valueOf(response.statusCode))
        }

        val typeToken = object : TypeToken<List<Job>>() {}.type

        return Gson().fromJson(response.text, typeToken)
    }
}
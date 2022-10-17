package com.licenta.newsfeed.business.services

import com.licenta.newsfeed.business.interfaces.IDatabaseService
import khttp.get
import org.springframework.stereotype.Service

@Service
class DatabaseService: IDatabaseService {
    override fun getCompaniesFollowedByUser(userId: String) {
        // TODO: replace localhost with env variable
        val response = get("http://localhost:8080/users/${userId}/followed-companies")
        println(response.statusCode)
        println(response.headers["Content-Type"])
        println(response.text)
    }
}
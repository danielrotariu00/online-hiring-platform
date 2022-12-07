package com.licenta.newsfeedmicroservice.persistence.repositories

import com.licenta.newsfeedmicroservice.persistence.entities.Call
import org.springframework.data.repository.CrudRepository

interface CallRepository : CrudRepository<Call, Long> {
    fun findByUserId(userId: Long): Call?
}
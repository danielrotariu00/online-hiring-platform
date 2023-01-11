package com.licenta.searchmicroservice.persistence.repository

import com.licenta.searchmicroservice.persistence.entity.Job
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface JobRepository: JpaRepository<Job, String> {

    fun findAllByQueryId(queryId: String, pageable: Pageable): Page<Job>
    fun deleteAllByQueryId(queryId: String);
}
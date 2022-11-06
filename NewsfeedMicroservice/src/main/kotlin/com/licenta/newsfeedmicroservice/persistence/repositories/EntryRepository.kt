package com.licenta.newsfeedmicroservice.persistence.repositories

import com.licenta.newsfeedmicroservice.persistence.entities.Entry
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository

interface EntryRepository: PagingAndSortingRepository<Entry, Int> {

    fun findAllByUserId(userId: String, pageable: Pageable): List<Entry>
}
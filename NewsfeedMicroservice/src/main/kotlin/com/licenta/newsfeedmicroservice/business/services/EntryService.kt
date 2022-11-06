package com.licenta.newsfeedmicroservice.business.services

import com.licenta.newsfeedmicroservice.persistence.entities.Entry
import com.licenta.newsfeedmicroservice.persistence.repositories.EntryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class EntryService {

    @Autowired
    private lateinit var entryRepository: EntryRepository

    fun saveEntry(entry: Entry) {
        entryRepository.save(entry)
    }

    fun getAndDeleteEntries(userId: String, limit: Int): List<Entry> {
        val entries = entryRepository.findAllByUserId(
            userId,
            PageRequest.of(0, limit, Sort.by("postedAt").descending())
        )

        entryRepository.deleteAll(entries)

        return entries
    }
}
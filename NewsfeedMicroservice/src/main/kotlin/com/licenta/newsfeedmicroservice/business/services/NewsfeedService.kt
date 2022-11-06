package com.licenta.newsfeedmicroservice.business.services

import com.licenta.newsfeedmicroservice.business.interfaces.IDatabaseService
import com.licenta.newsfeedmicroservice.business.interfaces.INewsfeedService
import com.licenta.newsfeedmicroservice.business.interfaces.ISearchService
import com.licenta.newsfeedmicroservice.business.model.Job
import com.licenta.newsfeedmicroservice.business.util.mapper.EntryMapper
import com.licenta.newsfeedmicroservice.business.util.parser.LocalDateTimeParser
import com.licenta.newsfeedmicroservice.persistence.entities.Entry
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class NewsfeedService: INewsfeedService {

    @Autowired
    private lateinit var databaseService: IDatabaseService
    @Autowired
    private lateinit var searchService: ISearchService
    @Autowired
    private lateinit var callService: CallService // todo: interface
    @Autowired
    private lateinit var entryService: EntryService // todo: interface

    override fun getNewsfeed(userId: String, maxEntries: Int): Iterable<com.licenta.newsfeedmicroservice.business.model.EntryResponse> {
        val lastCall = callService.getAndUpdateLastCallTimestamp(userId)
        val companyIndustries = databaseService.getCompanyIndustriesFollowedByUser(userId)
        val jobs = mutableListOf<Job>()

        companyIndustries.forEach {
            jobs.addAll(searchService.getJobs(it, lastCall))
        }

        // todo: mapper/converter
        jobs.map { job ->
            Entry(
                userId,
                job.id,
                job.title,
                job.companyId,
                job.cityId,
                job.countryId,
                job.workTypeId,
                LocalDateTimeParser.parse(job.postedAt)
            )
        }.forEach { entry -> entryService.saveEntry(entry) }

        return entryService.getAndDeleteEntries(userId, maxEntries)
            .map { entry -> EntryMapper.toResponse(entry) }
    }
}
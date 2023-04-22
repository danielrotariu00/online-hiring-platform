package com.licenta.newsfeedmicroservice.business.services

import com.licenta.newsfeedmicroservice.business.interfaces.IDatabaseService
import com.licenta.newsfeedmicroservice.business.interfaces.INewsfeedService
import com.licenta.newsfeedmicroservice.business.interfaces.ISearchService
import com.licenta.newsfeedmicroservice.business.model.*
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

    override fun getNewsfeed(userId: Long, maxEntries: Int): Iterable<EntryResponse> {
        val lastCall = callService.getAndUpdateLastCallTimestamp(userId)
        val companyIndustries = databaseService.getCompanyIndustriesFollowedByUser(userId)
        val jobs = mutableListOf<Job>()

        companyIndustries.forEach {
            jobs.addAll(searchService.getJobs(it, lastCall))
        }

        jobs.map { job -> job.toEntry(userId) }
            .forEach { entry -> entryService.saveEntry(entry) }

        return entryService.getAndDeleteEntries(userId, maxEntries)
            .map { entry -> entry.toResponse() }
    }

    private fun Job.toEntry(userId: Long): Entry {
        val company: Company = databaseService.getCompanyById(this.companyId)
        val city: City = databaseService.getCityById(this.cityId)
        val country: Country = databaseService.getCountryById(this.countryId)
        val workType: WorkType = databaseService.getWorkTypeById(this.workTypeId)

        return Entry(
            userId,
            this.id,
            this.title,
            company.name,
            company.photo,
            city.name,
            country.name,
            workType.name,
            LocalDateTimeParser.parse(this.postedAt)
        )
    }

    private fun Entry.toResponse(): EntryResponse {
        return EntryResponse(
            this.jobId,
            this.jobTitle,
            this.companyName,
            this.companyLogoURL,
            this.cityName,
            this.countryName,
            this.workType,
            LocalDateTimeParser.toString(this.postedAt)
        )
    }
}
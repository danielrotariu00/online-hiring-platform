package com.licenta.searchmicroservice.business.service

import com.licenta.searchmicroservice.business.interfaces.IDatabaseClient
import com.licenta.searchmicroservice.business.interfaces.IFilterService
import com.licenta.searchmicroservice.business.interfaces.ISparkService
import com.licenta.searchmicroservice.business.model.JobDTO
import com.licenta.searchmicroservice.business.model.JobQuery
import com.licenta.searchmicroservice.business.model.JobQueryResponse
import com.licenta.searchmicroservice.business.util.hash
import com.licenta.searchmicroservice.persistence.entity.Job
import com.licenta.searchmicroservice.persistence.repository.JobRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import javax.transaction.Transactional

@Service
class FilterService: IFilterService {

    @Autowired
    private lateinit var databaseClient: IDatabaseClient
    @Autowired
    private lateinit var sparkService: ISparkService
    @Autowired
    private lateinit var jobRepository: JobRepository

    @Transactional
    override fun getFilteredJobs(jobQuery: JobQuery, cached: Boolean, page: Int, size: Int) : JobQueryResponse {
        val queryId = jobQuery.hash()

        if(!cached) {
            jobRepository.deleteAllByQueryId(queryId)
        }

        if (!jobQueryIsCached(queryId)) {
            val jobList = databaseClient.getJobs()
            val filteredJobs = sparkService.getFilteredJobs(jobList, jobQuery)

            jobRepository.saveAllAndFlush(
                filteredJobs.map { job -> toEntity(queryId, job) }
            )
        }

        val cachedJobsPage = jobRepository
            .findAllByQueryId(queryId, PageRequest.of(page, size))

        return JobQueryResponse(
            cachedJobsPage.content.map { job ->  toDTO(job) },
            cachedJobsPage.totalElements,
            cachedJobsPage.totalPages
        )
    }

    private fun toEntity(queryId: String, jobDTO: JobDTO): Job {
        return Job(
            queryId = queryId,
            jobId = jobDTO.id,
            recruiterId = jobDTO.recruiterId,
            title = jobDTO.title,
            companyId = jobDTO.companyId,
            cityId = jobDTO.cityId,
            countryId = jobDTO.countryId,
            workTypeId = jobDTO.workTypeId,
            jobTypeId = jobDTO.jobTypeId,
            experienceLevelId = jobDTO.experienceLevelId,
            companyIndustryId = jobDTO.companyIndustryId,
            industryId = jobDTO.industryId,
            description = jobDTO.description,
            postedAt = jobDTO.postedAt,
            jobStatusId = jobDTO.jobStatusId
        )
    }

    private fun toDTO(job: Job): JobDTO {
        return JobDTO(
            id = job.jobId,
            recruiterId = job.recruiterId,
            title = job.title,
            companyId = job.companyId,
            cityId = job.cityId,
            countryId = job.countryId,
            workTypeId = job.workTypeId,
            jobTypeId = job.jobTypeId,
            experienceLevelId = job.experienceLevelId,
            companyIndustryId = job.companyIndustryId,
            industryId = job.industryId,
            description = job.description,
            postedAt = job.postedAt,
            jobStatusId = job.jobStatusId
        )
    }

    private fun jobQueryIsCached(queryId: String): Boolean {
        val cachedJobs = jobRepository
            .findAllByQueryId(queryId, PageRequest.of(0, 1))
            .content

        if (cachedJobs.size == 0) {
            return false
        }
        return true
    }

    @Scheduled(fixedRate = 1800000)
    fun deleteOldCachedJobs() {
        val now = LocalDateTime.now()
        val jobs = jobRepository.findAll()

        jobs.map { job ->
            val minutes = job.savedAt.until(now, ChronoUnit.MINUTES)

            if (minutes > 30) {
                jobRepository.delete(job)
            }
        }
    }
}
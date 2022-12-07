package com.licenta.searchmicroservice.business.service

import com.licenta.searchmicroservice.business.config.Spark
import com.licenta.searchmicroservice.business.`interface`.ISparkService
import com.licenta.searchmicroservice.business.model.Job
import com.licenta.searchmicroservice.business.model.JobQuery
import com.licenta.searchmicroservice.business.util.parser.LocalDateTimeParser
import org.springframework.stereotype.Service
import java.io.Serializable


@Service
class SparkService: ISparkService, Serializable {

    override fun getFilteredJobs(jobList: List<Job>, jobQuery: JobQuery) : List<Job> {
        val distributedDataset = Spark.context.parallelize(jobList)

        return distributedDataset
            .filter { job -> job.matchesQuery(jobQuery) }
            .collect()
    }

    private fun Job.matchesQuery(jobQuery: JobQuery): Boolean {
        return  this.title.containsAnyMemberOf(jobQuery.titleList) &&
                this.description.containsAnyMemberOf(jobQuery.descriptionKeywordList) &&
                jobQuery.cityIdList.isEmptyOrContains(this.cityId) &&
                jobQuery.countryIdList.isEmptyOrContains(this.countryId) &&
                jobQuery.companyIdList.isEmptyOrContains(this.companyId) &&
                jobQuery.industryIdList.isEmptyOrContains(this.industryId) &&
                jobQuery.companyIndustryIdList.isEmptyOrContains(this.companyIndustryId) &&
                jobQuery.workTypeIdList.isEmptyOrContains(this.workTypeId) &&
                jobQuery.jobTypeIdList.isEmptyOrContains(this.jobTypeId) &&
                jobQuery.experienceLevelIdList.isEmptyOrContains(this.experienceLevelId) &&
                LocalDateTimeParser.parse(this.postedAt).isAfter(LocalDateTimeParser.parse(jobQuery.postedSince)) &&
                jobQuery.jobStatusIdList.isEmptyOrContains(this.jobStatusId)
    }

    private fun String.containsAnyMemberOf(list: List<String>): Boolean {
        return list.isEmpty() || list.any { this.lowercase().contains(it.lowercase()) }
    }

    private fun List<Long>.isEmptyOrContains(element: Long): Boolean {
        return this.isEmpty() || this.contains(element)
    }

    private fun List<Int>.isEmptyOrContains(element: Int): Boolean {
        return this.isEmpty() || this.contains(element)
    }
}
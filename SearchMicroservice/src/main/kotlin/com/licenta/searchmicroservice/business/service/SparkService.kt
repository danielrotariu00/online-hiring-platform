package com.licenta.searchmicroservice.business.service

import com.licenta.searchmicroservice.business.model.Job
import com.licenta.searchmicroservice.business.model.JobQuery
import com.licenta.searchmicroservice.business.util.parser.LocalDateTimeParser
import org.apache.spark.SparkConf
import org.apache.spark.api.java.JavaSparkContext
import org.springframework.stereotype.Service
import java.io.Serializable

object Spark {
    private val sparkConf: SparkConf = SparkConf()
        .setAppName("Search Microservice")
        .setMaster("spark://localhost:7077")
        .set("spark.io.encryption.enabled", "true")
        .set("spark.io.encryption.keySizeBits","256")
        .set("spark.executor.memory", "512m")
        .setJars(arrayOf(System.getProperty("user.dir") + "/out/artifacts/SearchMicroservice_jar/SearchMicroservice.jar"))

    val context = JavaSparkContext(sparkConf)
}

@Service
class SparkService: Serializable {

    fun getFilteredJobs(jobList: List<Job>, jobQuery: JobQuery) : List<Job> {
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
                LocalDateTimeParser.parse(this.postedAt).isAfter(LocalDateTimeParser.parse(jobQuery.postedSince))
    }

    private fun String.containsAnyMemberOf(list: List<String>): Boolean {
        return list.isEmpty() || list.any { this.lowercase().contains(it.lowercase()) }
    }

    private fun List<String>.isEmptyOrContains(element: String): Boolean {
        return this.isEmpty() || this.contains(element)
    }

    private fun List<Int>.isEmptyOrContains(element: Int): Boolean {
        return this.isEmpty() || this.contains(element)
    }
}
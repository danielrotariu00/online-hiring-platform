package com.licenta.searchmicroservice.persistence.entity

import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "cached_job")
class Job (

    @Id @GeneratedValue var id: Long? = null,
    @Column(nullable = false) val queryId: String,
    @Column(nullable = false) val jobId: Long,
    @Column(nullable = false) val recruiterId: Long,
    @Column(nullable = false) val title: String,
    @Column(nullable = false) val companyId: Long,
    @Column(nullable = false) val cityId: Int,
    @Column(nullable = false) val countryId: Int,
    @Column(nullable = false) val workTypeId: Int,
    @Column(nullable = false) val jobTypeId: Int,
    @Column(nullable = false) val experienceLevelId: Int,
    @Column(nullable = false) val companyIndustryId: Long,
    @Column(nullable = false) val industryId: Int,
    @Column(nullable = false) val description: String,
    @Column(nullable = false) val postedAt: String,
    @Column(nullable = false) val jobStatusId: Int
    ) {

    @field:CreationTimestamp lateinit var savedAt: LocalDateTime
}

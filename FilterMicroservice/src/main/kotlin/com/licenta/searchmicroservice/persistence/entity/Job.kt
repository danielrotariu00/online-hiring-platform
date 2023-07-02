package com.licenta.searchmicroservice.persistence.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "cached_job")
data class Job (

    @Id @GeneratedValue var id: Long? = null,
    @Column(nullable = false) val queryId: String = "",
    @Column(nullable = false) val jobId: Long = 0,
    @Column(nullable = false) val recruiterId: Long = 0,
    @Column(nullable = false) val title: String = "",
    @Column(nullable = false) val companyId: Long = 0,
    @Column(nullable = false) val cityId: Int = 0,
    @Column(nullable = false) val countryId: Int = 0,
    @Column(nullable = false) val workTypeId: Int = 0,
    @Column(nullable = false) val jobTypeId: Int = 0,
    @Column(nullable = false) val experienceLevelId: Int = 0,
    @Column(nullable = false) val companyIndustryId: Long = 0,
    @Column(nullable = false) val industryId: Int = 0,
    @Lob @Column(nullable = false) val description: String = "",
    @Column(nullable = false) val postedAt: String = "",
    @Column(nullable = false) val jobStatusId: Int = 0,
    @Column(nullable = false) val savedAt: LocalDateTime = LocalDateTime.now()
    )

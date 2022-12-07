package com.licenta.newsfeedmicroservice.persistence.entities

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "entry_table")
class Entry (
    var userId: Long,
    var jobId: Long,
    var jobTitle: String,
    var companyName: String,
    var companyLogoURL: String,
    var cityName: String,
    var countryName: String,
    var workType: String,
    var postedAt: LocalDateTime,
    @Id @GeneratedValue var id: Long? = null
)
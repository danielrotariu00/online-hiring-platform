package com.licenta.newsfeedmicroservice.persistence.entities

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "entry_table")
class Entry (
    var userId: String,
    var jobId: String,
    var jobTitle: String,
    var companyId: String,
    var cityId: Int,
    var countryId: Int,
    var workTypeId: Int,
    var postedAt: LocalDateTime,
    @Id @GeneratedValue var id: Int? = null
)
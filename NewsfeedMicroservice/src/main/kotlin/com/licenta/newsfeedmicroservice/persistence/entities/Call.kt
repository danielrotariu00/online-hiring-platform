package com.licenta.newsfeedmicroservice.persistence.entities

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "call_table")
class Call (
    var userId: Long,
    var lastTimestamp: LocalDateTime = LocalDateTime.now(),
    @Id @GeneratedValue var id: Long? = null
)
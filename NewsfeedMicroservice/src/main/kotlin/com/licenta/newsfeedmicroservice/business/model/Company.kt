package com.licenta.newsfeedmicroservice.business.model

import java.io.Serializable

data class Company(
    val id: Long,
    val name: String,
    val photo: String
): Serializable
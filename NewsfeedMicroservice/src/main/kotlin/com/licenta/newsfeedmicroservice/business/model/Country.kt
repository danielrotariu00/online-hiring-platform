package com.licenta.newsfeedmicroservice.business.model

import java.io.Serializable

data class Country(
    val id: Int,
    val name: String,
    val photo: String
): Serializable
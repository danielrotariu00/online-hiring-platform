package com.licenta.newsfeedmicroservice.business.model

import java.io.Serializable

data class City(
    val id: Int,
    val name: String,
    val countryId: Int
): Serializable
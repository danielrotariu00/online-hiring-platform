package com.licenta.searchmicroservice.business.util.parser

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeParser {

    companion object {
        fun parse(date: String): LocalDateTime {

            return LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        }
    }
}
package com.licenta.newsfeedmicroservice.business.util.mapper

import com.licenta.newsfeedmicroservice.business.model.EntryResponse
import com.licenta.newsfeedmicroservice.business.util.parser.LocalDateTimeParser
import com.licenta.newsfeedmicroservice.persistence.entities.Entry

class EntryMapper {
    companion object {
        fun toResponse(entry: Entry) = EntryResponse(
            entry.jobId,
            entry.jobTitle,
            entry.companyId,
            entry.cityId,
            entry.countryId,
            entry.workTypeId,
            LocalDateTimeParser.toString(entry.postedAt)
        )
    }
}
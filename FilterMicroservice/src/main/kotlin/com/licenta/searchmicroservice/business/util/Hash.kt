package com.licenta.searchmicroservice.business.util

import com.licenta.searchmicroservice.business.model.JobQuery
import java.security.MessageDigest


fun JobQuery.hash(): String {
    val HEX_CHARS = "0123456789ABCDEF"
    val sb = StringBuilder()

    sb.append("t:")
    for (title in this.titleList.sorted()) {
        sb.append(title)
    }
    sb.append("c:")
    for (cityId in this.cityIdList.sorted()) {
        sb.append(cityId)
    }
    sb.append("c:")
    for (countryId in this.countryIdList.sorted()) {
        sb.append(countryId)
    }
    sb.append("c:")
    for (companyId in this.companyIdList.sorted()) {
        sb.append(companyId)
    }
    sb.append("i:")
    for (industryId in this.industryIdList.sorted()) {
        sb.append(industryId)
    }
    sb.append("c:")
    for (companyIndustryId in this.companyIndustryIdList.sorted()) {
        sb.append(companyIndustryId)
    }
    sb.append("w:")
    for (workTypeId in this.workTypeIdList.sorted()) {
        sb.append(workTypeId)
    }
    sb.append("j:")
    for (jobTypeId in this.jobTypeIdList.sorted()) {
        sb.append(jobTypeId)
    }
    sb.append("e:")
    for (experienceLevelId in this.experienceLevelIdList.sorted()) {
        sb.append(experienceLevelId)
    }
    sb.append("d:")
    for (descriptionKeyword in this.descriptionKeywordList.sorted()) {
        sb.append(descriptionKeyword)
    }
    sb.append("p:").append(this.postedSince);
    sb.append("j:")
    for (jobStatusId in this.jobStatusIdList.sorted()) {
        sb.append(jobStatusId)
    }

    val bytes = MessageDigest
        .getInstance("SHA-512")
        .digest(sb.toString().toByteArray())
    val result = StringBuilder(bytes.size * 2)

    bytes.forEach {
        val i = it.toInt()
        result.append(HEX_CHARS[i shr 4 and 0x0f])
        result.append(HEX_CHARS[i and 0x0f])
    }

    return result.toString()
}

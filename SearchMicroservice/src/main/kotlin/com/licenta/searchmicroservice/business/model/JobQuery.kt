package com.licenta.searchmicroservice.business.model

import java.io.Serializable

data class JobQuery(
   val titleList: List<String>,
   val cityIdList: List<Int>,
   val countryIdList: List<Int>,
   val companyIdList: List<String>,
   val industryIdList: List<Int>,
   val companyIndustryIdList: List<Int>,
   val workTypeIdList: List<Int>,
   val jobTypeIdList: List<Int>,
   val experienceLevelIdList: List<Int>,
   val descriptionKeywordList: List<String>,
   val postedSince: String
): Serializable
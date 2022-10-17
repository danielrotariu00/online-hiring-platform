package com.licenta.newsfeed.business.interfaces

interface IDatabaseService {

    fun getCompaniesFollowedByUser(userId: String);
}
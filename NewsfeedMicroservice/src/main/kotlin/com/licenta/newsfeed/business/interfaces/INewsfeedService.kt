package com.licenta.newsfeed.business.interfaces

import com.licenta.newsfeed.business.model.NewsfeedResponse


interface INewsfeedService {
    fun getNewsfeed(userId: String): Iterable<NewsfeedResponse>
}
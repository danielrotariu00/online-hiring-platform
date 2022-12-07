package com.licenta.newsfeedmicroservice.business.interfaces

import com.licenta.newsfeedmicroservice.business.model.EntryResponse


interface INewsfeedService {
    fun getNewsfeed(userId: Long, maxEntries: Int): Iterable<EntryResponse>
}
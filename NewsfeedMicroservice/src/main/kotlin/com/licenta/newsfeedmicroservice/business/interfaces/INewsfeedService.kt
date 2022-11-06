package com.licenta.newsfeedmicroservice.business.interfaces

import com.licenta.newsfeedmicroservice.business.model.EntryResponse


interface INewsfeedService {
    fun getNewsfeed(userId: String, maxEntries: Int): Iterable<EntryResponse>
}
package com.licenta.newsfeedmicroservice.presentation.controllers

import com.licenta.newsfeedmicroservice.business.interfaces.INewsfeedService
import com.licenta.newsfeedmicroservice.business.model.EntryResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.constraints.NotEmpty

@Validated
@RestController
@RequestMapping(value = ["/users/{userId}/newsfeed"])
class NewsfeedController {

    @Autowired
    private lateinit var newsfeedService: INewsfeedService


    @GetMapping
    fun getNewsfeed(
        @NotEmpty @PathVariable userId: String,
        @RequestParam(defaultValue = "10") maxEntries: Int
    ): Iterable<EntryResponse> {

        return newsfeedService.getNewsfeed(userId, maxEntries)
    }
}
package com.licenta.newsfeedmicroservice.presentation.controllers

import com.licenta.newsfeedmicroservice.business.interfaces.INewsfeedService
import com.licenta.newsfeedmicroservice.business.model.EntryResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.constraints.Min

@Validated
@RestController
@CrossOrigin
@RequestMapping(value = ["/api/users/{userId}/newsfeed"])
class NewsfeedController {

    @Autowired
    private lateinit var newsfeedService: INewsfeedService


    @GetMapping
    fun getNewsfeed(
        @Min(1) @PathVariable userId: Long,
        @RequestParam(defaultValue = "10") maxEntries: Int
    ): Iterable<EntryResponse> {

        return newsfeedService.getNewsfeed(userId, maxEntries)
    }
}
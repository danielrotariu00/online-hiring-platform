package com.licenta.newsfeed.presentation.controllers

import com.licenta.newsfeed.business.interfaces.INewsfeedService
import com.licenta.newsfeed.business.model.NewsfeedResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.constraints.NotEmpty

@Validated
@RestController
@RequestMapping(value = ["/users/{userId}/newsfeed"])
class UserController {

    @Autowired
    private lateinit var newsfeedService: INewsfeedService


    @GetMapping
    fun getNewsfeed(@NotEmpty @PathVariable userId: String): Iterable<NewsfeedResponse> {
        return newsfeedService.getNewsfeed(userId)
    }
}
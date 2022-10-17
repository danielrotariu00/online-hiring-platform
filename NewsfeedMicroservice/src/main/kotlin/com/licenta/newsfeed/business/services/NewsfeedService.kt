package com.licenta.newsfeed.business.services

import com.licenta.newsfeed.business.interfaces.IDatabaseService
import com.licenta.newsfeed.business.interfaces.INewsfeedService
import com.licenta.newsfeed.business.model.NewsfeedResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class NewsfeedService: INewsfeedService {

    @Autowired
    private lateinit var databaseService: IDatabaseService

    override fun getNewsfeed(userId: String): Iterable<NewsfeedResponse> {
        // get newsfeed for user x at t0
        // ...
        // get newsfeed for user x at t1
        // 1. compute the next entries
        //      a. get all company industries the user x follows
        //      b. for each(company=z, industry=t) call search microservice to get the jobs that satisfy the conditions:
        //          - posted by company z
        //          - posted in industry t
        //          - posted since t0
        //          - sorted by timestamp
        //      c. save entries in persistence
        // 3. return min(y, entries_saved)
        // 4. delete returned entries

        // poc:

        databaseService.getCompaniesFollowedByUser(userId);

    }
}
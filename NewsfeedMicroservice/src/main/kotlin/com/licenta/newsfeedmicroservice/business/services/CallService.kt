package com.licenta.newsfeedmicroservice.business.services

import com.licenta.newsfeedmicroservice.persistence.entities.Call
import com.licenta.newsfeedmicroservice.persistence.repositories.CallRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class CallService {

    @Autowired
    private lateinit var callRepository: CallRepository

    fun getAndUpdateLastCallTimestamp(userId: Long): String {
        var call = callRepository.findByUserId(userId)
        val output: String

        if (call == null) {
            call = Call(userId)
            output = "2000-01-01 00:00:00"
        } else {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            output = call.lastTimestamp.format(formatter)
            call.lastTimestamp = LocalDateTime.now()
        }

        callRepository.save(call)

        return output
    }
}
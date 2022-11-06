package com.licenta.newsfeedmicroservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NewsfeedMicroserviceApplication

fun main(args: Array<String>) {
	runApplication<NewsfeedMicroserviceApplication>(*args)
}

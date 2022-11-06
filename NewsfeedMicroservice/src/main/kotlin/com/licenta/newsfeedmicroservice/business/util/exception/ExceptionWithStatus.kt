package com.licenta.newsfeedmicroservice.business.util.exception

import org.springframework.http.HttpStatus

class ExceptionWithStatus(errorMessage: String?, private val status: HttpStatus) : RuntimeException(errorMessage)
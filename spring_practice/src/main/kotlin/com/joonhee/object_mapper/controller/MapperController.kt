package com.joonhee.object_mapper.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.File

@RestController
@RequestMapping("/object-mapper")
class MapperController {
    @PostMapping("/basic-request-body")
    fun basicRequestBody(
        @RequestBody request: BasicRequest
    ): BasicRequest {
        return request
    }
}

class BasicRequest(
    val name: String,
    val age: Int
)
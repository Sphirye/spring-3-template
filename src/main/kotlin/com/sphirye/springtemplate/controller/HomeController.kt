package com.sphirye.springtemplate.controller

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController {

    @GetMapping("/")
    fun home(): ResponseEntity<String> {
        return ResponseEntity.status(200).body("Hello World!")
    }

}
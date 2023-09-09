package com.humanassist.registration.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/test")
@RestController
public class TestAPI {

    @GetMapping
    public String test() {
        return "Hello, this is authenticated";
    }
}

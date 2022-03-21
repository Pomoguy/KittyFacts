package com.bpmn2.kittyfacts.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Demo {

    @GetMapping("/check")
    public String check() {
        return "Application is alive";
    }
}

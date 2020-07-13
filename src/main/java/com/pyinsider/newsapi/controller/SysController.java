package com.pyinsider.newsapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys")
public class SysController {
    @RequestMapping("/health")
    public String getHealth() {
        return "Hey I am Fine";
    }
}

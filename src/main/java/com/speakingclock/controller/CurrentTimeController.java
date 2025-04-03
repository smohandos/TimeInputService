package com.speakingclock.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.speakingclock.service.TimeService;

@RestController
public class CurrentTimeController {

    private final TimeService timeService;

    public CurrentTimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping("/currentTime")
    public String getCurrentTimeInWords() {
        return timeService.getCurrentTimeInWords();
    }
}

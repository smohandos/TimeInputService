package com.speakingclock.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.speakingclock.exception.InvalidTimeFormatException;
import com.speakingclock.service.TimeService;

@RestController
public class TimeInputController {

    private final TimeService timeInputService;

    public TimeInputController(TimeService timeInputService) {
        this.timeInputService = timeInputService;
    }

    @GetMapping("/userInputTime")
    public String inputTime(@RequestParam String time) throws InvalidTimeFormatException{
        return timeInputService.getUserInputTimeInWords(time);
    }
}

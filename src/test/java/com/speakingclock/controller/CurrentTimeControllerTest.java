package com.speakingclock.controller;

import com.speakingclock.service.TimeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
public class CurrentTimeControllerTest {

    @Mock
    private TimeService timeService;

    @InjectMocks
    private CurrentTimeController currentTimeController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        // Initialize MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(currentTimeController).build();
    }

    @Test
    public void testGetCurrentTimeInWords() throws Exception {
        // Mock the behavior of the timeService
        String mockedTimeInWords = "Ten O'Clock";
        when(timeService.getCurrentTimeInWords()).thenReturn(mockedTimeInWords);

        // Perform GET request and verify the response
        mockMvc.perform(get("/currentTime"))
                .andExpect(status().isOk())  // Verify status code 200
                .andExpect(content().string(mockedTimeInWords));  // Verify the response content

        // Verify that the timeService method was called exactly once
        verify(timeService, times(1)).getCurrentTimeInWords();
    }
}

package com.speakingclock.service;

import com.speakingclock.exception.InvalidTimeFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TimeServiceTest {

    @Mock
    private RestTemplate restTemplate;  // Mock the RestTemplate

    @InjectMocks
    private TimeService timeService;  // Inject the mock RestTemplate into TimeService

    @BeforeEach
    public void setUp() {
        // Initialize the mock objects using MockitoAnnotations
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCurrentTimeInWords() {
        // Prepare a mocked response from the RestTemplate
        String expectedResponse = "Five O'Clock PM";
        String url = "http://localhost:8082/convertCurrentTime";

        // Mock the behavior of the RestTemplate
        when(restTemplate.getForObject(url, String.class)).thenReturn(expectedResponse);

        // Call the service method
        String actualResponse = timeService.getCurrentTimeInWords();

        // Verify the response
        assertEquals(expectedResponse, actualResponse);

        // Verify that the RestTemplate was called once
        verify(restTemplate, times(1)).getForObject(url, String.class);
    }

    @Test
    public void testGetUserInputTimeInWords_validFormat() throws InvalidTimeFormatException {
        // Prepare valid input time and mocked response
        String validTime = "15:30";
        String expectedResponse = "Three Thirty PM";
        String url = "http://localhost:8082/convertUserInputTime?time=" + validTime;

        // Mock the behavior of the RestTemplate
        when(restTemplate.getForObject(url, String.class)).thenReturn(expectedResponse);

        // Call the service method
        String actualResponse = timeService.getUserInputTimeInWords(validTime);

        // Verify the response
        assertEquals(expectedResponse, actualResponse);

        // Verify that the RestTemplate was called once
        verify(restTemplate, times(1)).getForObject(url, String.class);
    }

    @Test
    public void testGetUserInputTimeInWords_invalidFormat() {
        // Prepare invalid time input
        String invalidTime = "25:00";  // Invalid time format (hour > 23)

        // Call the service method and assert that it throws InvalidTimeFormatException
        InvalidTimeFormatException exception = assertThrows(InvalidTimeFormatException.class, () -> {
            timeService.getUserInputTimeInWords(invalidTime);
        });

        // Verify the exception message
        assertEquals("Invalid time format: 25:00", exception.getMessage());
    }

    @Test
    public void testIsValidTimeFormat() {
        // Valid time formats
        assertTrue(timeService.isValidTimeFormat("23:59"));
        assertTrue(timeService.isValidTimeFormat("00:00"));
        assertTrue(timeService.isValidTimeFormat("15:30"));

        // Invalid time formats
        assertFalse(timeService.isValidTimeFormat("25:00"));
        assertFalse(timeService.isValidTimeFormat("12:60"));
        assertFalse(timeService.isValidTimeFormat("99:99"));
    }
}

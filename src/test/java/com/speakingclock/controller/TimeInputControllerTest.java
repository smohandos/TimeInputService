package com.speakingclock.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.speakingclock.exception.InvalidTimeFormatException;
import com.speakingclock.service.TimeService;

@WebMvcTest(TimeInputController.class)  // Test only the web layer (controller)
public class TimeInputControllerTest {

	@MockBean
	private TimeService timeInputService;  // Mock the TimeService and inject it into the context

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testInputTime_validFormat() throws Exception {
		// Prepare mock service response for valid time input
		String validTime = "15:30";
		String timeInWords = "Three Thirty PM";

		// Mock the behavior of the service
		when(timeInputService.getUserInputTimeInWords(validTime)).thenReturn(timeInWords);

		// Perform GET request with valid time and verify the response
		mockMvc.perform(get("/userInputTime").param("time", validTime))
		.andExpect(status().isOk())  // Verify status code 200
		.andExpect(content().string(timeInWords));  // Verify the response content

		// Verify that the timeInputService method was called once
		verify(timeInputService, times(1)).getUserInputTimeInWords(validTime);
	}

	@Test
	public void testInputTime_invalidFormat() throws Exception {
		// Prepare invalid time input
		String invalidTime = "25:00";  // Invalid hour (greater than 24)

		// Simulate that the service throws InvalidTimeFormatException
		when(timeInputService.getUserInputTimeInWords(invalidTime)).thenThrow(InvalidTimeFormatException.class);

		// Perform GET request with invalid time and verify that an error is returned
		mockMvc.perform(get("/userInputTime").param("time", invalidTime))
		.andExpect(status().isBadRequest());  // Should return 400 Bad Request
	}
}

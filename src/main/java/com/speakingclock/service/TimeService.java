package com.speakingclock.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.speakingclock.exception.InvalidTimeFormatException;

@Service
public class TimeService {

	private final RestTemplate restTemplate;

	public TimeService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public String getCurrentTimeInWords() {
		String url = "http://localhost:8082/convertCurrentTime";
		return restTemplate.getForObject(url, String.class);
	}

	public String getUserInputTimeInWords(String time) throws InvalidTimeFormatException {

		if (!isValidTimeFormat(time)) {
			throw new InvalidTimeFormatException("Invalid time format: " + time);
		}

		String url = "http://localhost:8082/convertUserInputTime?time=" + time;
		return restTemplate.getForObject(url, String.class);
	}

	public boolean isValidTimeFormat(String time) {
		String regex = "([01]?[0-9]|2[0-3]):([0-5][0-9])"; // Matches 00:00 to 23:59
		return time.matches(regex);
	}
}

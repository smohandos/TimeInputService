package com.speakingclock.exception;

public class InvalidTimeFormatException extends Exception {

    // Constructor with no arguments
    public InvalidTimeFormatException() {
        super("Invalid time format.");
    }

    // Constructor with a custom error message
    public InvalidTimeFormatException(String message) {
        super(message);
    }

    // Constructor with custom message and cause
    public InvalidTimeFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor with cause
    public InvalidTimeFormatException(Throwable cause) {
        super(cause);
    }
}

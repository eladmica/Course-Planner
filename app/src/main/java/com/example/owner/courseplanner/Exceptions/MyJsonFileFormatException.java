package com.example.owner.courseplanner.Exceptions;

/**
 * Represents exception raised when there is an error with the format of the JSON file
 */
public class MyJsonFileFormatException extends Exception {
    public MyJsonFileFormatException() {
        super();
    }

    public MyJsonFileFormatException(String msg) {
        super(msg);
    }
}

package de.htw_berlin.pickyourread.exceptions;

public class InvalidISBNException extends Exception {
    public InvalidISBNException(String errorMessage) {
        super(errorMessage);
    }
}

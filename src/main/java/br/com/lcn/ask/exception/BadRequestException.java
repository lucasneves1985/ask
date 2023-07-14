package br.com.lcn.ask.exception;

public class BadRequestException extends Exception {
    public BadRequestException(String errorMessage) {
        super(errorMessage);
    }
    public BadRequestException() {

    }
}

package br.com.lcn.ask.exception;

public class NotFoundException extends Exception {

    public NotFoundException(String errorMessage) {
        super(errorMessage);
    }

    public NotFoundException() {

    }
}

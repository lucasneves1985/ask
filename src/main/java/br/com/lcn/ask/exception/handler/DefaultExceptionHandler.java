package br.com.lcn.ask.exception.handler;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {

        ErrorDetails error = ErrorDetails.builder()
                .title("API Error")
                .details("Ocorreram erros ao executar requisição")
                .status(status.value())
                .developerMessage(ex.getMessage())
                .build();

        return super.handleExceptionInternal(ex, error, headers, status, request);
    }


    @ExceptionHandler(value = {RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleAllUncaughtException(RuntimeException ex, WebRequest request) {
        System.out.println(ex.getClass());

        ErrorDetails error = ErrorDetails.builder()
                .title("API Error")
                .details("Ocorreram erros ao executar requisição")
                .status(500)
                .developerMessage(ex.getMessage())
                .build();
        return ResponseEntity.internalServerError().body(error);
    }

    @ExceptionHandler(value = {TransactionSystemException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleTransactionSystemException(TransactionSystemException ex, WebRequest request) {

        ErrorDetails error = ErrorDetails.builder()
                .title("API Error")
                .details("Ocorreram erros ao executar requisição")
                .status(400)
                .developerMessage(ex.getMessage())
                .build();
        return ResponseEntity.internalServerError().body(error);
    }

}

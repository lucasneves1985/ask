package br.com.lcn.ask.exception.handler;

import br.com.lcn.ask.exception.BadRequestException;
import br.com.lcn.ask.exception.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.stream.Collectors;


@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        ErrorDetailsValidation error = ErrorDetailsValidation.builder()
                .title("API Error")
                .details("Ocorreram erros de validação")
                .status(400)
                .field(fieldErrors.stream().map(FieldError::getField).collect(Collectors.toSet()))
                .validationMessage(fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.toSet()))
                .build();


        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            org.springframework.http.converter.HttpMessageNotReadableException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        ErrorDetails error = ErrorDetails.builder()
                .title("API Error")
                .details("Ocorreram erros ao executar a requisição")
                .status(status.value())
                .developerMessage(ex.getMessage())
                .build();

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler({ BadRequestException.class })
    public ResponseEntity<Object> handleBadRequestException(final Exception ex, final WebRequest request) {

        System.out.println("Entrou no BadRequest");
        ErrorDetails error = ErrorDetails.builder()
                .title("API Error")
                .details(ex.getMessage())
                .status(400)
                .developerMessage(ex.getMessage())
                .build();
        return new ResponseEntity<Object>(error, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ NotFoundException.class })
    public ResponseEntity<Object> handleNotFoundException(final Exception ex, final WebRequest request) {
        System.out.println("Entrou no not found");
        ErrorDetails error = ErrorDetails.builder()
                .title("API Error")
                .details(ex.getMessage())
                .status(404)
                .developerMessage(ex.getMessage())
                .build();
        return new ResponseEntity<Object>(error, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
}

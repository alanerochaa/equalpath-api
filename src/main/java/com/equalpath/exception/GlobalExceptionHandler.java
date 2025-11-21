package com.equalpath.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 – Not Found
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.NOT_FOUND.value(),
                        "Recurso não encontrado",
                        ex.getMessage(),
                        request.getDescription(false)
                )
        );
    }

    // 400 – Validation Error
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex,
                                                          WebRequest request) {

        Map<String, String> erros = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String campo = ((FieldError) error).getField();
            String mensagem = error.getDefaultMessage();
            erros.put(campo, mensagem);
        });

        return ResponseEntity.badRequest().body(
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        "Erro de validação",
                        "Um ou mais campos estão inválidos.",
                        request.getDescription(false),
                        erros
                )
        );
    }

    // 400 – JSON malformado / erro de enum / corpo inválido
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleUnreadable(HttpMessageNotReadableException ex,
                                                          WebRequest request) {

        String msg = ex.getMostSpecificCause() != null
                ? ex.getMostSpecificCause().getMessage()
                : ex.getMessage();

        // tratamento elegante pra quando o erro for enum inválido
        if (msg.contains("not one of the values accepted")) {
            msg = "Valor fornecido é inválido para o campo ENUM.";
        }

        return ResponseEntity.badRequest().body(
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        "Corpo da requisição inválido",
                        msg,
                        request.getDescription(false)
                )
        );
    }

    // 409 – Data conflict (unique, foreign key, etc)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleConflict(DataIntegrityViolationException ex,
                                                        WebRequest request) {

        String raiz = ex.getMostSpecificCause() != null
                ? ex.getMostSpecificCause().getMessage()
                : ex.getMessage();

        String mensagem = "Conflito ao processar a requisição. Verifique dados únicos ou relacionamentos.";

        if (raiz.toLowerCase().contains("unique")) {
            mensagem = "Conflito de dados: já existe um registro com essas informações.";
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.CONFLICT.value(),
                        "Conflito de dados",
                        mensagem,
                        request.getDescription(false)
                )
        );
    }


    // 500 – Generic Internal Error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex, WebRequest request) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Erro interno",
                        ex.getMessage(),
                        request.getDescription(false)
                )
        );
    }

    public record ErrorResponse(
            LocalDateTime timestamp,
            int status,
            String error,
            String message,
            String path,
            Map<String, String> fieldErrors
    ) {
        public ErrorResponse(LocalDateTime timestamp, int status, String error, String message, String path) {
            this(timestamp, status, error, message, path, null);
        }
    }
}

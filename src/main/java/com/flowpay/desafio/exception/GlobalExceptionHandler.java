package com.flowpay.desafio.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice(basePackages = "com.flowpay.desafio.api")
public class GlobalExceptionHandler {

    // Captura erros de IDs não encontrados (404)
    @ExceptionHandler(AtendenteNaoCadastradoException.class)
    public ResponseEntity<Object> handleNotFound(AtendenteNaoCadastradoException e) {
        return buildErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Captura erros de regra de negócio, como lotação de atendente (422)
    @ExceptionHandler(AtendenteNaoDisponivelException.class)
    public ResponseEntity<Object> handleBusinessError(AtendenteNaoDisponivelException e) {
        return buildErrorResponse(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    // Captura qualquer outro erro genérico (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralError(Exception e) {
        return buildErrorResponse("Ocorreu um erro interno no servidor.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Método auxiliar para padronizar o JSON de erro
    private ResponseEntity<Object> buildErrorResponse(String message, HttpStatus status) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        return new ResponseEntity<>(body, status);
    }
}
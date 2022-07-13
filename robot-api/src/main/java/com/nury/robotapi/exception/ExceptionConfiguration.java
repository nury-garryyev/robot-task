package com.nury.robotapi.exception;

import com.nury.robotapi.model.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
class ExceptionConfiguration {

    @ExceptionHandler(RobotException.class)
    public ResponseEntity<Response> handleRobotExceptions(Exception e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(new Response(e.getMessage()), status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleExceptions(Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return new ResponseEntity<>(new Response(e.getMessage()), status);
    }
}

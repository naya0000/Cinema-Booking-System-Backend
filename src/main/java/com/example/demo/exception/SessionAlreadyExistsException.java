package com.example.demo.exception;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SessionAlreadyExistsException extends RuntimeException {
    public SessionAlreadyExistsException(LocalTime startTime,LocalDate sessionDate, Integer movieId) {
        super(String.format("Session with sessionDate='%s', startTime='%s', movieId='%d' already exists", sessionDate, startTime, movieId));
    }
}
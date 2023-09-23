package com.example.demo.exception;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SeatAlreadyExistsException extends RuntimeException {
    public SeatAlreadyExistsException(String seatRow, String seatNumber, Integer movieId, Integer sessionId) {
        super(String.format("Seat with seatRow='%s', seatNumber='%s', movieId='%d', sessionId='%d' already exists", seatRow, seatNumber, movieId, sessionId));
    }
}

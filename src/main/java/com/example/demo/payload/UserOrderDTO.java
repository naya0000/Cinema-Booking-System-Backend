package com.example.demo.payload;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.example.demo.enums.CancelStatus;
import com.example.demo.enums.PaymentMethod;
import com.example.demo.enums.TicketCategory;
import com.example.demo.model.Movie;
import com.example.demo.model.Session;
import com.example.demo.model.User;

import lombok.Data;

@Data
public class UserOrderDTO {
	private Integer id;
	private String orderDate;
    private Integer quantity;
    private PaymentMethod payment;
    private TicketCategory ticket;
    private Integer user;
    private String movie;
    private LocalTime startTime;
    private LocalDate sessionDate;
    private List <String> seatsNumber;
    private float totalAmount;
    private CancelStatus canceled;
}

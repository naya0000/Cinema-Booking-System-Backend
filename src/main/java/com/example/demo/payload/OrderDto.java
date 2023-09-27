package com.example.demo.payload;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.example.demo.enums.CancelStatus;
import com.example.demo.enums.OrderStatus;
import com.example.demo.enums.PaymentMethod;
import com.example.demo.enums.TicketCategory;
import com.example.demo.model.Movie;
import com.example.demo.model.Seat;
import com.example.demo.model.Session;
import com.example.demo.model.User;

import lombok.Data;

@Data
public class OrderDto { //use when create order (receive OrderDto)
    private String orderDate;
    private Integer quantity;
    private PaymentMethod payment;
    private TicketCategory ticket;
    private User user;
    private Movie movie;
    private Session session;
    private List <Long> seatsId;
    private float totalAmount;
    private CancelStatus canceled;
    private OrderStatus status;

   
}

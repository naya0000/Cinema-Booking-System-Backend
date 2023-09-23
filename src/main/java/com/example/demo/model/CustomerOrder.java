package com.example.demo.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import jakarta.validation.constraints.Min;

import com.example.demo.enums.CancelStatus;
import com.example.demo.enums.OrderStatus;
import com.example.demo.enums.PaymentMethod;
import com.example.demo.enums.TicketCategory;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "customer_order")
public class CustomerOrder {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String orderDate;
	private Integer quantity;
	
	@Enumerated(EnumType.STRING)
    private PaymentMethod payment;
	
	@Enumerated(EnumType.STRING)
	private TicketCategory ticket;
	
	//@JsonIgnore //no serialization
	@JsonBackReference(value="movie-order")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "movie_id")
	private Movie movie;

//	@JsonIgnore
	@JsonBackReference(value="user-order")
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")//foreign key
    private User user;
	
//	@JsonIgnore
	@JsonBackReference(value="session-order")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "session_id")
	private Session session;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "orders_seats", 
			joinColumns = @JoinColumn(name = "order_id"), 
			inverseJoinColumns = @JoinColumn(name = "seat_id")
	)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	private List<Seat> seats;
	
	private float totalAmount;
	
	@Enumerated(EnumType.STRING)
	private CancelStatus canceled;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
//	 @ManyToMany(fetch = FetchType.LAZY)
//	 private List<Seat> seats;
	
	@Override
    public String toString() {
        return "CustomerOrder{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", quantity=" + quantity +
                ", payment=" + payment +
                ", movie=" + (movie != null ? movie.getId() : "null") + // Assuming there's a getTitle() method in Movie
                ", user=" + (user != null ? user.getUsername() : "null") +     // Assuming there's a getName() method in User
                ", session=" + (session != null ? session.getId(): "null") + // Assuming there's a getSessionInfo() method in Session
                ", seats=" + seats +
                ", totalAmount="+ totalAmount +
                '}';
    }

}

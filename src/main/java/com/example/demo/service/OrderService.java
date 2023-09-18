package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.enums.CancelStatus;
import com.example.demo.model.CustomerOrder;
import com.example.demo.model.Role;
import com.example.demo.model.Seat;
import com.example.demo.model.User;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.SeatRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.payload.OrderDto;

@Service
public class OrderService {
	@Autowired
	private UserService userService;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private SeatService seatService;

	public CustomerOrder createOrder(OrderDto request) {
		CustomerOrder order = new CustomerOrder();
		//order.setPrice(request.getPrice());
		order.setOrderDate(request.getOrderDate());
		order.setPayment(request.getPayment());
		order.setTicket(request.getTicket());
		order.setQuantity(request.getQuantity());
		order.setMovie(request.getMovie());
		order.setSession(request.getSession());
		order.setUser(request.getUser());
		order.setTotalAmount(request.getTotalAmount());
		order.setCanceled(request.getCanceled());
//		System.out.println("request.getMovie(): "+request.getMovie());
//		System.out.println("request.getSession(): "+request.getSession());
		List<Long> seatsId = request.getSeatsId();
		//System.out.println("seatsId: "+seatsId);
		
		List <Seat> seats = seatService.updateSeatsAvailable(seatsId, 0);
		order.setSeats(seats);
		System.out.println("seats: "+seats);
		System.out.println("order: "+order);
		return orderRepository.save(order);
	}

	public CustomerOrder updateOrder(Integer id, CustomerOrder request) {
		CustomerOrder order = orderRepository.findById(id).orElse(null);
		if (order != null) {
//			order.setPrice(request.getPrice());
			order.setOrderDate(request.getOrderDate());
			order.setQuantity(request.getQuantity());
			order.setPayment(request.getPayment());
//			order.setUser(request.getUser());
//			order.setMovie(request.getMovie());
//			order.setSession(request.getSession());
			order.setTotalAmount(request.getTotalAmount());
			return orderRepository.save(order);
		}
		return null;
	}

	public boolean deleteOrder(Integer id) {
		CustomerOrder existingOrder = orderRepository.findById(id).orElse(null);
		if (existingOrder != null) {
			orderRepository.deleteById(id);
			return true;
		}
		return false;
	}

	public CustomerOrder getOrderById(Integer id) {
		return orderRepository.findById(id).orElse(null);
	}

	public List<CustomerOrder> getAllOrders() {
		return orderRepository.findAll();
	}

	public CustomerOrder updateOrderStatus(Integer id, CancelStatus canceled) {
		CustomerOrder order = orderRepository.findById(id).orElse(null);
		order.setCanceled(canceled);
		List <Seat> seats = order.getSeats();
		List <Long> seatsId = new ArrayList<>();
		for(Seat seat: seats) {
			seatsId.add(seat.getId());
		}
		seatService.updateSeatsAvailable(seatsId, 1);
		orderRepository.save(order);
		return order;
	}

}
 
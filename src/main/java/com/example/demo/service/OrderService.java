package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.enums.CancelStatus;
import com.example.demo.enums.OrderStatus;
import com.example.demo.exception.APIException;
import com.example.demo.mapper.UserOrderMapper;
import com.example.demo.model.CustomerOrder;
import com.example.demo.model.Role;
import com.example.demo.model.Seat;
import com.example.demo.model.User;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.SeatRepository;
import com.example.demo.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

import com.example.demo.payload.OrderDto;
import com.example.demo.payload.UserOrderDTO;

@Service
@Slf4j
public class OrderService {
	@Autowired
	private UserService userService;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private SeatService seatService;
	@Autowired
	private UserOrderMapper userOrderMapper;
	
	public CustomerOrder createOrder(OrderDto request) {
		CustomerOrder order = new CustomerOrder();
		// order.setPrice(request.getPrice());
		order.setOrderDate(request.getOrderDate());
		order.setPayment(request.getPayment());
		order.setTicket(request.getTicket());
		order.setQuantity(request.getQuantity());
		order.setMovie(request.getMovie());
		order.setSession(request.getSession());
		order.setUser(request.getUser());
		order.setTotalAmount(request.getTotalAmount());
		order.setCanceled(request.getCanceled());
		order.setStatus(request.getStatus());
//		System.out.println("request.getMovie(): "+request.getMovie());
//		System.out.println("request.getSession(): "+request.getSession());
		List<Long> seatsId = request.getSeatsId();
		// System.out.println("seatsId: "+seatsId);

		List<Seat> seats = seatService.updateSeatsAvailable(seatsId, 0);
		order.setSeats(seats);
		log.info("seats: {}", seats);// no System.out
		System.out.println("seats: " + seats);
		System.out.println("order: " + order);
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

	public void deleteOrder(Integer id) {
		CustomerOrder existingOrder = orderRepository.findById(id).orElse(null);
		if (existingOrder == null) {
			throw new APIException(HttpStatus.NOT_FOUND, "找不到該訂單");
		}
			orderRepository.deleteById(id);
	}

	public CustomerOrder getOrderById(Integer id) {
		return orderRepository.findById(id).orElse(null);
	}

	public List<UserOrderDTO> getAllOrders() {
		// Map CustomerOrder entities to UserOrderDTOs
		List<CustomerOrder> orders = orderRepository.findAll();
		List<UserOrderDTO> userOrderDTOs = orders.stream()
				.map(order -> userOrderMapper.updateUserOrderDTO(order, new UserOrderDTO()))
				.collect(Collectors.toList());
		return userOrderDTOs;
	}

	public CustomerOrder updateCancelStatus(Integer id, CancelStatus canceled) {
		CustomerOrder order = orderRepository.findById(id).orElse(null);
		System.out.println("order:"+order);
		order.setCanceled(canceled);
		System.out.println("canceled:"+canceled);

		List<Seat> seats = order.getSeats();
		System.out.println("seats:"+seats);

		List<Long> seatsId = new ArrayList<>();
	
		for (Seat seat : seats) {
			seatsId.add(seat.getId());
		}
		System.out.println("seatsId:"+seatsId);
		seatService.updateSeatsAvailable(seatsId, 1); // set the seats to available after user canceled the order
		orderRepository.save(order);
		return order;
	}
	public CustomerOrder updateOrderStatus(Integer id, OrderStatus status) {
		CustomerOrder order = orderRepository.findById(id).orElse(null);
		order.setStatus(status);
		List<Seat> seats = order.getSeats();
		List<Long> seatsId = new ArrayList<>();
		for (Seat seat : seats) {
			seatsId.add(seat.getId());
		}
		seatService.updateSeatsAvailable(seatsId, 1); // set the seats to available after admin canceled the order
		orderRepository.save(order);
		return order;
	}

}

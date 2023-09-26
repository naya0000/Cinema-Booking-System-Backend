package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.enums.CancelStatus;
import com.example.demo.enums.OrderStatus;
import com.example.demo.exception.APIException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.UserOrderMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.model.CustomerOrder;
import com.example.demo.model.Movie;
import com.example.demo.model.User;
import com.example.demo.payload.OrderDto;
import com.example.demo.payload.UserOrderDTO;
import com.example.demo.payload.UserResponse;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

//@RequestMapping("/orders")

@RestController
@CrossOrigin(origins = "*")
public class OrderController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserOrderMapper userOrderMapper;
//	@Autowired
//	private MovieService movieService;

	@PostMapping("/users/{user_id}/orders")
	public ResponseEntity<String> createUserOrder(@PathVariable Integer user_id, @RequestBody OrderDto request) {

		User user = userRepository.findById(user_id).orElse(null);
		System.out.println("request:" + request);
		if (user == null) {
			return ResponseEntity.notFound().build();// 404
		}
		request.setUser(user);

		CustomerOrder order = orderService.createOrder(request);

		if (order != null)
			return ResponseEntity.ok("create order ok");// 200 created
		else
			return ResponseEntity.badRequest().body("Failed to create order");// 400
	}

//	@GetMapping("/users/{username}/orders")
//	public ResponseEntity <List<CustomerOrder>> getUserOrders(@PathVariable String username) {
//		
//		User user = userService.getUserByUsername(username);
//
//		if (user == null) {
//			return ResponseEntity.notFound().build();//404
//		}
//		return  ResponseEntity.ok(user.getOrders());
//	}
	@GetMapping("/users/{user_id}/orders")
	public ResponseEntity<List<UserOrderDTO>> getUserOrders(@PathVariable Integer user_id) {

		User user = userRepository.findById(user_id).orElse(null);

		if (user == null) {
			return ResponseEntity.notFound().build();// 404
		}

		List<CustomerOrder> userOrders = user.getOrders();

		// Map CustomerOrder entities to UserOrderDTOs
		List<UserOrderDTO> userOrderDTOs = userOrders.stream()
				.map(order -> userOrderMapper.updateUserOrderDTO(order, new UserOrderDTO()))
				.collect(Collectors.toList());

		return ResponseEntity.ok(userOrderDTOs);

	}

	@PutMapping("/orders/{id}")
	public ResponseEntity<CustomerOrder> updateOrder(@PathVariable Integer id, @RequestBody CustomerOrder request) {

		CustomerOrder order = orderService.updateOrder(id, request);
		if (order != null) {
			return ResponseEntity.ok(order);
		} else {
			return ResponseEntity.notFound().build(); // 404 Not Found
		}
	}

	@DeleteMapping("/orders/{id}") // cancel order
	public ResponseEntity<?> deleteOrder(@PathVariable Integer id) {
		try {
			orderService.deleteOrder(id);
			return ResponseEntity.ok("刪除訂單成功");
		}catch(APIException e) {
			return ResponseEntity.status(e.getStatus()).body(e.getMessage());
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
		}
	
	}

	@GetMapping("/orders/{id}")
	public ResponseEntity<CustomerOrder> getOrderById(@PathVariable Integer id) {
		CustomerOrder order = orderService.getOrderById(id);

		if (order != null) {
			return ResponseEntity.ok(order);
		}
		return ResponseEntity.notFound().build(); // 404 Not Found
	}

	@GetMapping("/orders")
	public ResponseEntity<List<UserOrderDTO>> getAllOrders() {

		List<UserOrderDTO> orders = orderService.getAllOrders();
		return ResponseEntity.ok(orders);
	}

	@PutMapping("/orders/canceledStatus/{id}")
	public ResponseEntity<String> updateCancelStatus(@PathVariable Integer id, @RequestBody CancelStatus canceled) {
		
		CustomerOrder order = orderService.updateCancelStatus(id, canceled);

		if (order != null)
			return ResponseEntity.ok("update cancel status ok");
		else {
			return ResponseEntity.notFound().build(); // 404 Not Found
		}
	}
	@PutMapping("/orders/status/{id}")
	public ResponseEntity<String> updateOrderStatus(@PathVariable Integer id, @RequestBody OrderStatus status) {

		CustomerOrder order = orderService.updateOrderStatus(id, status);

		if (order != null)
			return ResponseEntity.ok("update order status ok");
		else {
			return ResponseEntity.notFound().build(); // 404 Not Found
		}
	}
}

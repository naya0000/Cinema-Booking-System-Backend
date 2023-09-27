package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.CustomerOrder;


@Repository
public interface OrderRepository extends JpaRepository<CustomerOrder,Integer>{
//	@Query("SELECT o FROM CustomerOrder o " + "ORDER BY TO_DATE(o.orderDate, 'MM/DD/YYYY, HH:MI:SS AM') DESC")
	@Query(value = "SELECT * FROM customer_order ORDER BY STR_TO_DATE(order_date, '%m/%d/%Y, %h:%i:%s %p') DESC", nativeQuery = true)
	List<CustomerOrder>findAllOrderedByOrderDate();
}

package com.example.demo.payload;

import java.util.Set;

//import com.example.demo.enums.AccountStatus;
import com.example.demo.model.Role;

import lombok.Data;

@Data
public class UserStatusDTO {
	private Integer id;
	private boolean locked;
//	private AccountStatus status;
}

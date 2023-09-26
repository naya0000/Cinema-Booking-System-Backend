package com.example.demo.payload;

import lombok.Data;

@Data
public class ResetPasswordByAdminDTO {
	private Integer id;
	private String newPassword;
}

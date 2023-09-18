package com.example.demo.payload;

import java.util.Set;

import com.example.demo.model.Role;

import lombok.Data;

@Data
public class UserRolesDTO {
	
    private Set <Role> roles; 
    public UserRolesDTO() {
    }

    public UserRolesDTO(Set<Role> roles) {
        this.roles = roles;
    }
}

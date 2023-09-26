package com.example.demo.model;

import java.util.List;
import java.util.Set;

//import com.example.demo.enums.AccountStatus;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String username;
	private String password;
	private String phoneNumber;
	//private List<UserAuthority> authorities;
	// private String email;

	@JsonManagedReference(value="user-order")
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<CustomerOrder> orders;

	@ManyToMany(fetch = FetchType.EAGER)// If use FetchType.LAZY, error: Access to XMLHttpRequest at 'http://localhost:8080/users/logout' from origin 'http://localhost:3000' has been blocked by CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resource.
	@JoinTable(
			name = "users_roles", 
			joinColumns = @JoinColumn(name = "user_id"), 
			inverseJoinColumns = @JoinColumn(name = "role_id")
	)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	private Set<Role> roles;
	
	private boolean locked;  
//	@Enumerated(EnumType.STRING)
//	private AccountStatus status; // this attribute has been deleted in db

	
	@Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", orders=" + (orders != null ? orders.toString() : "null") +
                //", roles=" + (roles != null ? roles.toString() : "null") +
                '}';
    }
}

package com.example.demo.model;

import jakarta.persistence.*;

import lombok.Data;

@Data
@Entity
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	@Override
	public String toString() {
		return "Role{" + "id=" + id + ", name='" + name + '\'' + '}';
	}
//    @ManyToMany(mappedBy = "roles")
//    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
//    private List<User> users ;

}
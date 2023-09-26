package com.example.demo.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.model.User;

public class SpringUser implements UserDetails {
	private User user;

	public SpringUser(User user) {
		this.user = user;
	}
	public Integer getId() {
        return user.getId();
    }

    public String getName() {
        return user.getName();
    }
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		 return appUser.getAuthorities().stream()
//	                .map(auth -> new SimpleGrantedAuthority(auth.name()))
//	                .collect(Collectors.toList());
//	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		 return user.getRoles().stream()
	                .map(auth -> new SimpleGrantedAuthority(auth.getName()))
	                .collect(Collectors.toList());
	}
	@Override
	public String getPassword() {
		  return user.getPassword();
	}

	@Override
	public String getUsername() {
		  return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return !user.isLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}

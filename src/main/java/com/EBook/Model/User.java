package com.EBook.Model;


import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
private int id;
	@NotBlank(message="User Name can't be empty")
	@Size(min=3, max=20,message="user name between 3 to 20 only")
private String name;
@Column(unique = true)
@NotBlank(message="Please enter Email")
@Email(message="Invalid email",regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
private String email;
@NotNull(message="please enter password")
@Size(min=3,message="password must be 3 to 20 character")
private String password;
@Column(length=200)
private String address;
@Column(length=200)
private String imageName;
private boolean active;
private String role;



@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
	
    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + this.role);
    return Collections.singleton(authority);
}
@Override
public String getUsername() {
	// TODO Auto-generated method stub
	return this.email;
}

@Override
public String getPassword(){
    return this.password;
}

@Override
public boolean isAccountNonExpired() {
	// TODO Auto-generated method stub
	return true;
}
@Override
public boolean isAccountNonLocked() {
	// TODO Auto-generated method stub
	return true;
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

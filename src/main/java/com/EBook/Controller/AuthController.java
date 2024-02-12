package com.EBook.Controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EBook.Exception.ApiResponse;
import com.EBook.Model.User;
import com.EBook.Security.JWTHelper;
import com.EBook.Security.JwtRequest;
import com.EBook.Security.JwtResponse;
import com.EBook.Service.UserService;


@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private UserService userService;
	

	   @Autowired
	    private UserDetailsService userDetailsService;

	    @Autowired
	    private AuthenticationManager manager;


	    @Autowired
	    private JWTHelper helper;

	    private Logger logger = LoggerFactory.getLogger(AuthController.class);


	    @PostMapping("/login")
	    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {

	        this.doAuthenticate(request.getEmail(), request.getPassword());


	        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
	        String token = this.helper.generateToken(userDetails);
	        User user=(User)userDetails;
	        user.setPassword("");
	        JwtResponse response = JwtResponse.builder()
	                .jwtToken(token)
	                .user(user)
	                .build();
	        
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }
	    
	    
	    

	    private void doAuthenticate(String email, String password) {

	        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
	        try {
	            manager.authenticate(authentication);

	        } catch (BadCredentialsException e) {
	     System.out.println("invalid password");
	            throw new BadCredentialsException(" Invalid Password  !!");
	        }
	       
	        catch(Exception ex) {
	        	System.out.println("invalid email");
	        	 throw new BadCredentialsException("Invalid email ");
	        }

	    }

//	    @ExceptionHandler(BadCredentialsException.class)
//	    public String exceptionHandler() {
//	    
//	        return "Credentials Invalid!! username and password";
//	    }
	    
	    @ExceptionHandler(BadCredentialsException.class)
	    public ResponseEntity<ApiResponse> exceptionHandler(BadCredentialsException ex) {
	        String errorMessage = ex.getMessage();
	        ApiResponse apiResponse=new ApiResponse(errorMessage,true);
	        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.BAD_REQUEST);
	    }
	    
	 
	    @PostMapping("/register")
		public ResponseEntity<User> createUser(@Validated @RequestBody User user) throws IOException {
			User user1 = this.userService.createUser(user);
			return new ResponseEntity<>(user1, HttpStatus.CREATED);
		}
	    
	    
}

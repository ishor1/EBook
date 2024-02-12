package com.EBook.ServiceImp;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.EBook.Exception.ResourceNotFoundException;
import com.EBook.Model.User;
import com.EBook.Repository.UserRepository;
import com.EBook.Service.UserService;
import com.EBook.helper.PageableResponse;

@Service
public class UserServiceImp implements UserService {
@Autowired
private UserRepository userRepo;

@Autowired
private PasswordEncoder passworderEncoder;
	@Override
	public User createUser(User user) {
		if(user.getEmail()=="ishor@gmail.com") {
			user.setRole("ADMIN");
		}
		user.setPassword(this.passworderEncoder.encode(user.getPassword()));
		user.setActive(true);		
		user.setRole("NORMAL");
		user.setImageName("default.png");
		return this.userRepo.save(user);
	}

	@Override
	public User updateUser(User user, int userId) {
	User DBuser=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user not found"));
	DBuser.setName(user.getName());
	DBuser.setEmail(user.getEmail());
	DBuser.setPassword(user.getPassword());
	DBuser.setAddress(user.getAddress());
	DBuser.setImageName(user.getImageName());	
	return this.userRepo.save(DBuser);
	}

	@Override
	public void deleteUser(int userId) {
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found"));
		this.userRepo.delete(user);
	}
	
	public void deleteAllUser() {
		this.userRepo.deleteAll();
	}

	@Override
	public User getUserById(int userId) {
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found"));
		return user;
	}

	@Override
	public User getUserByEmail(String email) {
	User user=this.userRepo.findByEmail(email);
		return user;
	}

	@Override
	//in parameter: sortBy variable hold detail-> base on parameter the operation perform sorting
	public PageableResponse<User> getAllUserByPageNo(int pageNumber,int pageSize,String sortBy,String sortDir) {
		//Here creating Sort object and add sortBy variable on that object.
		//kiske base pe sort karna chahte ho eg: name , title etc uske base pe asending and descending sort ka obj banalo.
		Sort sort = (sortDir.equalsIgnoreCase("decs")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize,sort);
        
		Page<User> page=this.userRepo.findAll(pageable);
	      List<User> users = page.getContent();
        PageableResponse<User> pageableResponse = new PageableResponse<>();
        pageableResponse.setContent(users);
        pageableResponse.setPageNumber(page.getNumber());
        pageableResponse.setPageSize(page.getSize());
        pageableResponse.setTotalPages(page.getTotalPages());
        pageableResponse.setTotalElements(page.getTotalElements());
        pageableResponse.setLastPage(page.isLast());
		
		return pageableResponse;
	}

	

}

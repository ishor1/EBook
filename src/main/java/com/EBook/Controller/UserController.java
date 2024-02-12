package com.EBook.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.EBook.Exception.ApiResponse;
import com.EBook.Model.User;
import com.EBook.Service.FileService;
import com.EBook.Service.UserService;
import com.EBook.helper.PageableResponse;



@RestController
@RequestMapping("/user")
public class UserController {
	
	   @Value("${user.profile.image.path}")
	    private String imageUploadPath;
	@Autowired
	private UserService userService;

	@Autowired
	private FileService fileService;
	
	
// create
	@PostMapping
	public ResponseEntity<User> createUser(@Validated @RequestBody User user) throws IOException {
		user.setImageName("default.png");
		User user1 = this.userService.createUser(user);
		return new ResponseEntity<>(user1, HttpStatus.CREATED);
	}

//update
	@PutMapping("/{userId}")
	public ResponseEntity<User> updateUser(@Validated @PathVariable("userId") int userId,@RequestBody User user){
		User user1 = this.userService.updateUser(user, userId);
		return new ResponseEntity<>(user1, HttpStatus.OK);
	}
	
//delete
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")		
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable int userId){
		this.userService.deleteUser(userId);
		ApiResponse apiResponse=new ApiResponse("user delete successfully",true);
		return new ResponseEntity<>(apiResponse,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping
	public ResponseEntity<ApiResponse> deleteAllUser(){
		this.userService.deleteAllUser();
		ApiResponse apiResponse=new ApiResponse("All user delete successfully",true);
		return new ResponseEntity<>(apiResponse,HttpStatus.OK);
	}
	
//get User
//	Pageable implements
	@GetMapping
	public ResponseEntity<PageableResponse<User>> getAllUser(
            @RequestParam(value = "pageNumber",defaultValue = "1",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "2",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "name",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
			){
		PageableResponse<User> allUsersWithPageDetails=this.userService.getAllUserByPageNo(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<>(allUsersWithPageDetails,HttpStatus.OK);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<User> getUser(@PathVariable int userId){
	return new ResponseEntity<>(this.userService.getUserById(userId),HttpStatus.OK) 	;
	}
	
@PutMapping("/{userId}/img")
public ResponseEntity<ApiResponse> uploadUserImage(@PathVariable int userId,@RequestParam("userImage") MultipartFile image) throws IOException {
String message="Profile not Update";
	if(!image.isEmpty()) {
		User user = userService.getUserById(userId);
	String imageName = fileService.uploadFile(image,imageUploadPath);
	if(imageName!="fileNotSaved") {	
//   //Saving image file in name in user table
   user.setImageName(imageName);
   User user1 = userService.updateUser(user,userId);
   message="successfully update";
	}
}
	ApiResponse apiResponse=new ApiResponse(message,true);
   return new ResponseEntity<>(apiResponse,HttpStatus.CREATED);
}





}






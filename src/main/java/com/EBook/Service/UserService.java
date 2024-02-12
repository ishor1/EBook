package com.EBook.Service;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import com.EBook.Model.User;
import com.EBook.helper.PageableResponse;



public interface UserService{
    //create
    User createUser(User user);

    //update
    User updateUser(User User,int userId);

    //delete
    void deleteUser(int userId);

    void deleteAllUser();
    
    //getAllUser

    PageableResponse<User> getAllUserByPageNo(int pageNumber, int pageSize,String sortBy,String sortDir);

    //getSingleUser
    User getUserById(int userId);

    //getSingleUserByEmail
    User getUserByEmail(String email);


    //searchUser
//    List<User> searchUser(String keyword);

}

package com.EBook.Exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(){
        super("Resource not found Exception");
     }

     public ResourceNotFoundException(String message){
         super(message);
     }


}








//anything not found then this exception throws
//to use exception then ...........    ()-> new ResourceNotFoundException("user","id",userid)
//example to use.   userRepository.findById(userid).orElseThrow(()-> new ResourceNotFoundException("user","id",userid));











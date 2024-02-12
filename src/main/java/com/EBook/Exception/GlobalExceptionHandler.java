package com.EBook.Exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;




@RestControllerAdvice
public class GlobalExceptionHandler {
	//jaha jaha resource not found exception aayega oha ye method execute hoga
	@ExceptionHandler(ResourceNotFoundException.class)
public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler( ResourceNotFoundException ex){
	String message=ex.getMessage();
	ApiResponse apiResponse =new ApiResponse(message,false);
	return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
}
	
    //MethodArgumentNotValidException  hibernate column wala
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex){
              List<ObjectError> allErrors =  ex.getBindingResult().getAllErrors();
              Map<String,Object> response = new HashMap<>();
              allErrors.stream().forEach(ObjectError ->{
                      String message = ObjectError.getDefaultMessage();
                      String feild = ((FieldError)ObjectError).getField();
                      response.put(feild,message);
              });
              return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
    
    
    @ExceptionHandler(BadApiRequest.class)
    public ResponseEntity<ApiResponse> handleBadApiRequest(BadApiRequest ex){
//        logger.info("Bad Api Reqiest !!");
        String message=ex.getMessage();
        ApiResponse responseMessage = new ApiResponse(message,true);
        return new ResponseEntity<>(responseMessage,HttpStatus.BAD_REQUEST);
    }
}

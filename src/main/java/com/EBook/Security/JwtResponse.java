package com.EBook.Security;


import com.EBook.Model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class JwtResponse {
private String jwtToken;
private User user;
}


//you can only send user. userName not necessery because userName already present in user
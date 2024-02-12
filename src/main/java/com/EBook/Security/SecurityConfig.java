package com.EBook.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableGlobalMethodSecurity(prePostEnabled = true)//ise hum koi bhi url ko authenticate kar sakte hai ex: user delete method
@Configuration
public class SecurityConfig {
	
	  @Autowired
	    private JWTAthenticationEntryPoint point;
	    @Autowired
	    private JwtAuthenticationFilter filter;
	    @Autowired
	    private PasswordEncoder passwordEncoder;
	    @Autowired
	    private UserDetailsService userDetailsService;
	
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
	http.csrf(csrf->csrf.disable()).cors(cors->cors.disable())
	.authorizeHttpRequests(auth->auth
//			.requestMatchers("/auth/login").hasRole("ADMIN").permitAll()
//			 .antMatchers(HttpMethod.GET, "/public/**").permitAll()
			 .requestMatchers(HttpMethod.GET).permitAll()
			.requestMatchers("/auth/login").permitAll()
			.requestMatchers("/auth/register").permitAll()
			.anyRequest().authenticated()
			)
	.exceptionHandling(ex->ex.authenticationEntryPoint(point))
	.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	;
	
	http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
}


//db 
public DaoAuthenticationProvider daoAuthenticationProvider() {
	DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
	daoAuthenticationProvider.setUserDetailsService(userDetailsService);
	daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
	return daoAuthenticationProvider();
}

}

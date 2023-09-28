package com.yg.exam.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class MySecurirtConfig {
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	

    @Autowired
    private JwtAuthenticationFilter filter;
	
	@Bean
	  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	  {
		
		http
				
			.csrf()
			.disable()
			.cors()
			.disable()
			.authorizeHttpRequests()
			.requestMatchers("/generate-token","/user/").permitAll()
			.requestMatchers(HttpMethod.OPTIONS).permitAll()
			.anyRequest()
	          .authenticated()
	        .and()
	        .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
	        .and()
	          .sessionManagement()
	          .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
			
		 http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
	        
	        http.authenticationProvider(daoAuthenticationProvider());
	        DefaultSecurityFilterChain builddDefaultSecurityFilterChain= http.build();
	        return builddDefaultSecurityFilterChain;
		
			
	  }
	
	@Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception
    {
    	return authenticationConfiguration.getAuthenticationManager();
    }
    
    @Bean
    PasswordEncoder passwordEncoder()
    {
    	return new BCryptPasswordEncoder();
    	
    }
    
    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider()
    {
    	 DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
         provider.setUserDetailsService(this.userDetailsServiceImpl);
         provider.setPasswordEncoder(passwordEncoder());
         return provider;
    }

}

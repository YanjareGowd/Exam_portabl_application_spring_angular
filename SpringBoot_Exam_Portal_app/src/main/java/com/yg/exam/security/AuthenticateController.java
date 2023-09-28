package com.yg.exam.security;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yg.exam.entity.JwtRequest;
import com.yg.exam.entity.JwtResponse;
import com.yg.exam.entity.User;
import com.yg.exam.service.impl.UserServiceImpl;

@RestController
@CrossOrigin("*")
public class AuthenticateController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private UserServiceImpl userService;
	
	//generate token
	@PostMapping("/generate-token")
	public ResponseEntity<JwtResponse> generateToken(
			@RequestBody JwtRequest request
			) throws Exception
	{
		
		try {
			
			this.authenticate(request.getUsername(),request.getPassword());
			
		} catch (UsernameNotFoundException e) {
			e.printStackTrace();
			throw new Exception("User not found");

		}
		
		
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		
		String token = this.jwtTokenHelper.generateToken(userDetails);
		
		JwtResponse response=new JwtResponse();
		
		response.setToken(token);
		
		return new ResponseEntity<JwtResponse>(response,HttpStatus.OK);
	}
	
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	private void authenticate(String username,String password) throws Exception {
		
		UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(username, password);
		try {
			
			this.authenticationManager.authenticate(authenticationToken);
			
		} catch (DisabledException e) {
			
			throw new Exception("User disabled" + e.getMessage());

		}catch (BadCredentialsException e) {
			
			throw new Exception("Invaild Credential"+e.getMessage());
		}
		
	}
	
	//returns the deatils of current user
	
	@GetMapping("/current-user")
	public User getCurrentUser(Principal principal)
	{
		return ((User)this.userDetailsService.loadUserByUsername(principal.getName()));
	}

}

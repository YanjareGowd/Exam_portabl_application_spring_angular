package com.yg.exam.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
	
		
		// 1.get token
		
		String requestToken=request.getHeader("Authorization");
		
		//Bearer 42eu65i67g
		
		System.out.println(requestToken);
		
		String username=null;
		
		String token=null;
		
		//get token
		if(requestToken!=null && requestToken.startsWith("Bearer"))
		{
			
			//get token without Bearer
			token= requestToken.substring(7);
			
			try {
				
				username =this.jwtTokenHelper.getUsernameFromToken(token);
				
			} catch (IllegalArgumentException e) {
				
				System.out.println("Unable to get Jwt token");
				
			}catch (ExpiredJwtException e) {
				
				System.out.println("jwt token has expired");
			}catch (MalformedJwtException e) {
       
               System.out.println("Some changed has done in token !! Invalid Token");
                
            } catch (Exception e) {
                e.printStackTrace();

            }

			
			
		}
		else {
			System.out.println("Jwt token does not begin with Bearer");
		}
		
		//once we get the token, now validate
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {


            //fetch user detail from  
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            Boolean validateToken = this.jwtTokenHelper.validateToken(token, userDetails);
            if (validateToken) {

                //set the authentication
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);


            } else {
                System.out.println("Validation fails !! ");
            }


        }
		
		filterChain.doFilter(request, response);
	}

}

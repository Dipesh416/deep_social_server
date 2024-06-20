package com.deep.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deep.config.JwtProvider;
import com.deep.models.User;
import com.deep.repository.UserRepository;
import com.deep.request.LoginRequest;
import com.deep.response.AuthResponse;
import com.deep.services.CustomerUserDetailsService;
import com.deep.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private  UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private CustomerUserDetailsService coustomerUserDetails;
	
	@PostMapping("/signup")
	public AuthResponse createUser(@RequestBody  User user) throws Exception {
		User isExist = userRepository.findByEmail(user.getEmail());
		
		if(isExist!=null) {
			throw new Exception("email already exist with another account ");
		}
		
		User newUser = new User();
		
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setEmail(user.getEmail());
		newUser.setGender(user.getGender());
		newUser.setPassword(passwordEncoder.encode(user.getPassword()));
		
		User saveduser = userRepository.save(newUser);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(saveduser.getEmail(),saveduser.getPassword());
		
		String token = JwtProvider.generateToken(authentication);
		
		AuthResponse res= new AuthResponse(token,"Register Sucess");
		
		
		return res;
		
	}
	
	@PostMapping("/signin")
	public AuthResponse signIn(@RequestBody LoginRequest loginRequest) {
		
		Authentication authentication = authenicate(loginRequest.getEmail(),loginRequest.getpassword());
		
		String token = JwtProvider.generateToken(authentication);
		
		AuthResponse res= new AuthResponse(token,"login Sucess");
		
		
		return res;
		
		
	}
	
	private Authentication authenicate(String email, String password) {
		
		UserDetails userDeatils = coustomerUserDetails.loadUserByUsername(email);
		
		if(userDeatils == null) {
			throw new BadCredentialsException("invalis username ");
		}
		if(!passwordEncoder.matches(password, userDeatils.getPassword())) {
			throw new BadCredentialsException("invalid user name or password");
		}
		
		return new UsernamePasswordAuthenticationToken(userDeatils, null, userDeatils.getAuthorities());
	}
}

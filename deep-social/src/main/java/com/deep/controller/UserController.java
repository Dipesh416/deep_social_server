package com.deep.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deep.exception.UserException;
import com.deep.models.User;
import com.deep.repository.UserRepository;
import com.deep.services.UserService;

@RestController
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserService userService;
	
	
	
	@GetMapping("/api/user")
	public List<User> getUser() {
		
		List<User> users = userRepository.findAll()	;	
		return  users;
	}
	
	@GetMapping("/api/user/{userId}")
	public User getUserId (@PathVariable("userId") Integer userId) throws UserException {
				
			User user = userService.findUserById(userId);
		
		return user;
	}
	
	
	
	@PutMapping("/api/user")
	public User updateUser(@RequestHeader("Authorization") String jwt, @RequestBody User user) throws UserException {
		
		User reqUser = userService.findUserByJwt(jwt);
		
		User updateUser = userService.updateUser(user, reqUser.getId());
		
		return updateUser;
	}
	
	@PutMapping("/api/user/follow/{userId2}")
	public User followUserHandler(@RequestHeader("Authorization") String jwt, @PathVariable Integer userId2) throws UserException {
		 
		User reqUser = userService.findUserByJwt(jwt);
		
		User user = userService.followUser(reqUser.getId(), userId2);
		return user;
	}
	
	@GetMapping("/api/user/search")
	public List<User> searchUser(@RequestParam("query") String query){
		
		List<User> users = userService.searchUser(query);
		
		return users;
	}
	
	@GetMapping("api/user/profile")
	public User gtUserFromToken(@RequestHeader("Authorization") String jwt) {
		
		User user = userService.findUserByJwt(jwt);
		
		user.setPassword(null);
		return user;
		
	}
//	@DeleteMapping("/user/{userid}")
//	public String deleteUser(@PathVariable("userid") Integer userid ) throws Exception {
//		
//		Optional<User> user1 = userRepository.findById(userid);
//		
//		if(user1.isEmpty()) {
//			throw new Exception("user does not exist with id" +userid);
//		}
//		userRepository.delete(user1.get());
//		return "user deleted with id "+ userid;
//	}
}

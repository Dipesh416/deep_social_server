package com.deep.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.deep.exception.ReelException;
import com.deep.exception.UserException;
import com.deep.models.Reels;
import com.deep.models.User;
import com.deep.services.ReelsService;
import com.deep.services.UserService;

@RestController
public class ReelsController {

	@Autowired
	private ReelsService reelsService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/api/reels")
	public Reels createReels(
			@RequestBody Reels reel,
			@RequestHeader("Authorization" )String jwt) {
		
		User reqUser = userService.findUserByJwt(jwt);
		
		Reels createReels = reelsService.createReels(reel, reqUser);
		
		return createReels;
		
	}
	
	@GetMapping("/api/reels")
	public List<Reels> findAllReels() {
		
		
		List<Reels> createReels = reelsService.findAllReels();
		
		return createReels;
		
	}
	
	@GetMapping("/api/reels/user/{userId}")
	public List<Reels> findUsersReels(@PathVariable("userId") Integer userId) throws ReelException, UserException {
		
		
		List<Reels> createReels = reelsService.findUsersReel(userId);
		
		return createReels;
		
	}
}

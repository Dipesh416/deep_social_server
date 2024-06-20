package com.deep.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.deep.exception.StoryException;
import com.deep.exception.UserException;
import com.deep.models.Story;
import com.deep.models.User;
import com.deep.services.StoryService;
import com.deep.services.UserService;

@RestController
public class StroyController {

	@Autowired
	private StoryService storyService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/api/story")
	public Story createStory(@RequestHeader("Authorization") String jwt,@RequestBody Story story) {
		
		User reqUser = userService.findUserByJwt(jwt);
		
		Story createdStory = storyService.createStory(story, reqUser);
		
				
		return createdStory;
		
	}
	
	@GetMapping("/api/story/user/{userId}")
	public List<Story> findUserStory(@PathVariable("userId") Integer userId) throws StoryException, UserException {
		
		List<Story> createdStory = storyService.findStoryByUserId( userId);
		
				
		return createdStory;
		
	}
}

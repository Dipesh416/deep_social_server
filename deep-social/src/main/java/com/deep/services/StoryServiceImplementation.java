package com.deep.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deep.exception.StoryException;
import com.deep.exception.UserException;
import com.deep.models.Story;
import com.deep.models.User;
import com.deep.repository.StoryRepository;

@Service
public class StoryServiceImplementation implements StoryService {

	
	@Autowired
	private StoryRepository storyRepository;
	
	@Autowired
	private UserService userService;
	
	@Override
	public Story createStory(Story story,User  user) {
		
		Story createStory = new Story();
		
		createStory.setCaption(story.getCaption());
		createStory.setImage(story.getImage());
		createStory.setTimestamp(LocalDateTime.now());
		createStory.setUser(user);
		
		return storyRepository.save(createStory);
	}

	@Override
	public List<Story> findStoryByUserId(Integer userId) throws StoryException, UserException {
		
		User user = userService.findUserById(userId);
		
		
		return storyRepository.findByUserId(userId);
	}

}

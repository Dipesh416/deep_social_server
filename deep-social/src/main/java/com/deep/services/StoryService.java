package com.deep.services;

import java.util.List;

import com.deep.exception.StoryException;
import com.deep.exception.UserException;
import com.deep.models.Story;
import com.deep.models.User;

public interface StoryService {

	public Story createStory(Story story ,User user);
	
	public List<Story> findStoryByUserId(Integer userId) throws StoryException, UserException;
}

package com.deep.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deep.exception.ReelException;
import com.deep.exception.UserException;
import com.deep.models.Reels;
import com.deep.models.User;
import com.deep.repository.ReelsRepository;


@Service
public class ReelsServiceImplementation implements ReelsService {

	@Autowired
	private ReelsRepository reelsRepository;
	
	@Autowired
	private UserService userService;
	@Override
	
	
	public Reels createReels(Reels reel, User user) {
		Reels createReel =  new Reels();
		
		createReel.setTitle(reel.getTitle());
		createReel.setVideo(reel.getVideo());
		createReel.setUser(user);
		
		
		return reelsRepository.save(createReel);
	}

	@Override
	public List<Reels> findAllReels() {

		
		return reelsRepository.findAll();
	}

	@Override
	public List<Reels> findUsersReel(Integer userId) throws ReelException, UserException {
		
		userService.findUserById(userId);
		
		return reelsRepository.findByUserId(userId);
	}

}

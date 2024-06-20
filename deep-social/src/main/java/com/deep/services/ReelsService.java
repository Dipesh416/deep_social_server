package com.deep.services;



import java.util.List;

import org.springframework.stereotype.Service;

import com.deep.exception.ReelException;
import com.deep.exception.UserException;
import com.deep.models.Reels;
import com.deep.models.User;


public interface ReelsService {

	public Reels createReels(Reels reel, User user);
	
	public List<Reels>  findAllReels();
	
	public List<Reels> findUsersReel(Integer userId) throws ReelException, UserException;
	
}

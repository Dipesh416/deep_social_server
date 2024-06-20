package com.deep.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deep.config.JwtProvider;
import com.deep.exception.UserException;
import com.deep.models.User;
import com.deep.repository.UserRepository;

import io.jsonwebtoken.Jwt;


@Service
public class UserServiceImplementation  implements UserService{
	
	 @Autowired
	 UserRepository userRepository;
	 
	@Override
	public User registerUser(User user) {
		
		User newUser = new User();
		
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setEmail(user.getEmail());
		newUser.setId(user.getId());
		newUser.setPassword(user.getPassword());
		newUser.setGender(user.getGender());
		
		User saveduser = userRepository.save(newUser);
		
		return saveduser;
		
	}

	@Override
	public User findUserById(Integer userId) throws UserException {
		
		Optional<User> user = userRepository.findById(userId);
		
		if(user.isPresent()) {
			return user.get();
		}
		throw  new UserException("user does not exist with id "+userId);

	}

	@Override
	public User findUserByEmail(String email) {

		User user = userRepository.findByEmail(email);
		
		return user;
	}

	@Override
	public User followUser(Integer reqUserId, Integer userId2) throws UserException {
		
		User reqUser = findUserById(reqUserId);
		
		User user2 = findUserById(userId2);
		
		user2.getFollwers().add(reqUser.getId());
		reqUser.getFollowing().add(user2.getId());
		
		userRepository.save(reqUser);
		userRepository.save(user2);
		
		return reqUser;
	}

	@Override
	public User updateUser(User user,Integer userId) throws UserException {
		
		Optional<User> user1 = userRepository.findById(userId);
		
		if(user1.isEmpty()) {
			throw new UserException("user does not exist with id  " +userId);
		}
		
		User oldUser = user1.get();
		
		if(user.getFirstName()!=null) {
			oldUser.setFirstName(user.getFirstName());
		}
		if(user.getLastName()!=null) {
			oldUser.setLastName(user.getLastName());
		}
		if(user.getEmail()!=null) {
			oldUser.setEmail(user.getEmail());
		}
		if(user.getPassword()!=null) {
			oldUser.setPassword(user.getPassword());
		}
		if(user.getGender()!=null) {
			oldUser.setGender(user.getGender());
		}
		
		User saveUser = userRepository.save(oldUser);
		return saveUser;
	}

	@Override
	public List<User> searchUser(String query) {
		
		return userRepository.searchUser(query);
	}

	@Override
	public User findUserByJwt(String jwt) {
		
		String email = JwtProvider.getEmailFromJwtToken(jwt);
		
		User user =userRepository.findByEmail(email);
		return user;
	}

}

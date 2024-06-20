package com.deep.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deep.exception.PostException;
import com.deep.exception.UserException;
import com.deep.models.Post;
import com.deep.models.User;
import com.deep.repository.PostReposetory;
import com.deep.repository.UserRepository;

@Service
public class PostServiceImplementation implements PostService {

	@Autowired
	PostReposetory postRepostery;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public Post createPost(Post post, Integer userId) throws PostException, UserException {
		
		User user = userService.findUserById(userId);
		Post newPost = new Post();
		
		newPost.setCaption(post.getCaption());
		newPost.setImage(post.getImage());
		newPost.setVideo(post.getVideo());
		newPost.setUser(user);
		newPost.setCreatedAt( LocalDateTime.now());
		
		postRepostery.save(newPost);
		return newPost ;
	}

	@Override
	public String deletePost(Integer postId, Integer userId) throws PostException, UserException {
		
		Post post = findPostById(postId);
		User user = userService.findUserById(userId);
		
		if(post.getUser().getId()!=user.getId()) {
			throw new PostException("you cant delet ANOTHER POST");
			
		}
		postRepostery.delete(post);
		return "post deleted succesfully";
	}

	@Override
	public List<Post> findPostByUserId(Integer userId) {
		
		
		return postRepostery.findPostByUserId(userId);
	}

	@Override
	public Post findPostById(Integer postId) throws PostException {
		 
		Optional<Post> opt = postRepostery.findById(postId);
		
		if(opt.isEmpty()) {
			throw new PostException("post not found with id "+postId);
		}
		
	
		return opt.get();
	}

	@Override
	public List<Post> findAllPost() {
		
		return postRepostery.findAll();
	}

	@Override
	public Post savedPost(Integer postId, Integer userId) throws PostException, UserException {
		
		Post post = findPostById(postId);
		User user = userService.findUserById(userId);
		
		if(user.getSavedPost().contains(post)) {
			user.getSavedPost().remove(post);
		}
		else {
			user.getSavedPost().add(post);
		}
		userRepository.save(user);
		return post;
	}

	@Override
	public Post likePost(Integer postId, Integer userId) throws PostException, UserException {
		
		Post post = findPostById(postId);
		User user = userService.findUserById(userId);
		
		
		if(post.getLiked().contains(user)) {
			
			post.getLiked().remove(user);
	

		}
		else {
			post.getLiked().add(user);
			
		}
		return postRepostery.save(post);
	}

}

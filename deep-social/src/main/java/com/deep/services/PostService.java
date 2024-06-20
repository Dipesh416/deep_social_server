package com.deep.services;

import java.util.List;

import com.deep.exception.PostException;
import com.deep.exception.UserException;
import com.deep.models.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;

public interface PostService {

	Post createPost(Post post,Integer userId) throws PostException, UserException;
	
	String deletePost(Integer postId, Integer userId) throws PostException, UserException;
	
	List<Post> findPostByUserId(Integer userId);
	
	Post findPostById(Integer postId) throws PostException;
	
	List<Post> findAllPost();
	
	@JsonIgnore
	Post savedPost(Integer postId, Integer userId) throws PostException, UserException;
	
	Post likePost(Integer postId, Integer userId) throws PostException, UserException;
	
	
}

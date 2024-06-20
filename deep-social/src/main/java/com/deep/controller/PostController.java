package com.deep.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.deep.exception.PostException;
import com.deep.exception.UserException;
import com.deep.models.Post;
import com.deep.models.User;
import com.deep.response.ApiResponse;
import com.deep.services.PostService;
import com.deep.services.UserService;

@RestController
public class PostController {
	
	@Autowired
	PostService postService;
	
	@Autowired
	UserService userService;
	
	@PostMapping("/api/posts")
	public ResponseEntity<Post> createPost(
			@RequestHeader("Authorization") String jwt, 
			@RequestBody Post post) throws PostException, UserException{
		
		User reqUser = userService.findUserByJwt(jwt);
		
		Post createdPost = postService.createPost(post, reqUser.getId());
		
		return  new ResponseEntity< Post>(createdPost,HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("/api/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(
			@RequestHeader("Authorization") String jwt, 
			@PathVariable Integer postId
			) throws PostException, UserException{
		
		User reqUser = userService.findUserByJwt(jwt);
		
		String msg = postService.deletePost(postId, reqUser.getId());
		
		ApiResponse res = new ApiResponse(msg, true);
		
		return  new ResponseEntity<ApiResponse>(res,HttpStatus.OK);
		
	}
	
	
	@GetMapping("/api/posts/{postId}")
	public ResponseEntity<Post> findPostByPostId(@PathVariable Integer postId) throws PostException{
		
		Post post = postService.findPostById(postId);

		return new ResponseEntity<Post>(post,HttpStatus.OK);
		
	}
	
	@GetMapping("/api/posts/user/{userId}")
	public ResponseEntity<List<Post>> findPostByUserId(@PathVariable Integer userId){
		
		List<Post> post = postService.findPostByUserId(userId);
		
		return new ResponseEntity<List<Post>>(post,HttpStatus.OK);
		
	}
	
	@GetMapping("/api/posts")
	public ResponseEntity<List<Post>> findAllPost(){
		
		List<Post> post = postService.findAllPost();
		
		return new ResponseEntity<List<Post>>(post,HttpStatus.OK);
		
	}
	
	@PutMapping("/api/posts/save/{postId}")
	public ResponseEntity<Post> savePost(
			@RequestHeader("Authorization") String jwt, 
			@PathVariable Integer postId) throws PostException, UserException{
		
		User reqUser = userService.findUserByJwt(jwt);
		
		 Post post = postService.savedPost(postId, reqUser.getId());
		
			return  new ResponseEntity<>(post,HttpStatus.OK);

	}
	
	@PutMapping("/api/posts/like/{postId}")
	public ResponseEntity<Post> likePost(
			@RequestHeader("Authorization") String jwt, 
			@PathVariable Integer postId) throws PostException, UserException{
		
		User reqUser = userService.findUserByJwt(jwt);
		
		 Post post = postService.likePost(postId, reqUser.getId());
		
			return  new ResponseEntity<>(post,HttpStatus.OK);

	}
}

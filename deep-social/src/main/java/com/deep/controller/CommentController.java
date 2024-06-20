package com.deep.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.deep.exception.CommentException;
import com.deep.exception.PostException;
import com.deep.exception.UserException;
import com.deep.models.Comment;
import com.deep.models.User;
import com.deep.services.CommentService;
import com.deep.services.UserService;

@RestController
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/api/comment/post/{postId}")
	public Comment createComment(@RequestBody Comment comment,
			 @RequestHeader("Authorization") String jwt,
			 @PathVariable("postId") Integer postId) throws CommentException, UserException, PostException {
		
		User user = userService.findUserByJwt(jwt);
		
		Comment createdComment = commentService.createComment(comment, postId, user.getId());
		
		return createdComment;
		
	}
	
	@PutMapping("/api/comment/like/{commentId}")
	public Comment likeComment(
			 @RequestHeader("Authorization") String jwt,
			 @PathVariable("commentId") Integer commentId) throws CommentException, UserException {
		
		User user = userService.findUserByJwt(jwt);
		
		Comment likeComment = commentService.likeComments( commentId, user.getId());
		
		return likeComment;
		
	}
	
	@GetMapping("/api/comment/{commentId}")
	
	public Comment findCommentById(
			 @PathVariable("commentId") Integer commentId) throws CommentException {	
		
		return commentService.findCommentById(commentId);
		
	}
}

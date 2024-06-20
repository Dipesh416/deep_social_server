package com.deep.services;import org.hibernate.annotations.Comments;

import com.deep.exception.CommentException;
import com.deep.exception.PostException;
import com.deep.exception.UserException;
import com.deep.models.Comment;

public interface CommentService {

	public Comment createComment(Comment comment, Integer postId, Integer userId) throws CommentException, UserException, PostException;
	
	public Comment likeComments(Integer commentId, Integer userId) throws CommentException, UserException;
	
	public Comment findCommentById(Integer commentId) throws CommentException;
	
	
}

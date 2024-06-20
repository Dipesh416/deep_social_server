package com.deep.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.hibernate.annotations.Comments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import com.deep.exception.CommentException;
import com.deep.exception.PostException;
import com.deep.exception.UserException;
import com.deep.models.Comment;
import com.deep.models.Post;
import com.deep.models.User;
import com.deep.repository.CommentRepository;
import com.deep.repository.PostReposetory;

@Service
public class CommentServiceImplementation implements CommentService {

	@Autowired
	private PostService postService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private PostReposetory postReposetory;
	
	@Override
	public Comment createComment(Comment comment, Integer postId, Integer userId) throws CommentException, UserException, PostException {
		
		User user= userService.findUserById(userId);
		
		Post post =  postService.findPostById(postId);
		
		comment.setUser(user);
		comment.setContent(comment.getContent());
		comment.setCreatedAt(LocalDateTime.now());
		
		Comment savedComment = commentRepository.save(comment);
		
		post.getComments().add(savedComment);
		
		postReposetory.save(post);
		return savedComment;
	}


	@Override
	public Comment findCommentById(Integer commentId) throws CommentException {

		Optional<Comment> opt = commentRepository.findById(commentId);
		
		if(opt.isEmpty()) {
			throw new CommentException("Comment not exist");
		}
		
		return opt.get();
	}

	@Override
	public Comment likeComments(Integer commentId, Integer userId) throws CommentException, UserException {
		
		Comment comment = findCommentById(commentId);
		
		User user = userService.findUserById(userId);
		
		if(!comment.getLiked().contains(user)) {
			comment.getLiked().add(user);
		}
		else {
			comment.getLiked().remove(user);
		}
		
		return commentRepository.save(comment);
	}
}

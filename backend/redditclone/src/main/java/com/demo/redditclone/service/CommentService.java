package com.demo.redditclone.service;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.redditclone.dto.CommentDto;
import com.demo.redditclone.exceptions.PostNotFoundException;
import com.demo.redditclone.exceptions.SpringBootRedditException;
import com.demo.redditclone.mapper.CommentMapper;
import com.demo.redditclone.model.Comment;
import com.demo.redditclone.model.Notification;
import com.demo.redditclone.model.Post;
import com.demo.redditclone.model.User;
import com.demo.redditclone.repository.CommentRepository;
import com.demo.redditclone.repository.PostRepository;
import com.demo.redditclone.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class CommentService {
	private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;
    
    private static final String POST_URL = "";

    @Transactional
    public void save(CommentDto commentDto) {
    	Post post = postRepository.findById(commentDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentDto.getPostId().toString()));
    	
    	 commentRepository.save(commentMapper.mapDtoToComment(commentDto, post, authService.getCurrentUser()));

    	String message = mailContentBuilder.build(post.getUser().getUserName() + " posted a comment on your post." + POST_URL);
        sendCommentNotification(message, post.getUser());
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new Notification(user.getUserName() + " Commented on your post", user.getEmail(), message));
    }

    public boolean containsSwearWords(String comment) {
        if (comment.contains("shit")) {
            throw new SpringBootRedditException("Comments contains unacceptable language");
        }
        
        return false;
    }

    @Transactional(readOnly = true)
    public CommentDto getComment(Long id) {
    	Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new SpringBootRedditException("No comment found with ID - " + id));
    	
        return commentMapper.mapCommentToDto(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentDto> getAllComments() {
        return commentRepository.findAll()
                .stream()
                .map(commentMapper::mapCommentToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<CommentDto> getCommentsByPost(Long postId) {
    	Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId.toString()));
    	
        return commentRepository.findByPost(post).stream().map(commentMapper::mapCommentToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<CommentDto> getCommentsByUsername(String username) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapCommentToDto)
                .collect(toList());
    }
}

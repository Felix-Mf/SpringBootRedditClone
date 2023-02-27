package com.demo.redditclone.controller;

import static org.springframework.http.ResponseEntity.status;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.redditclone.dto.CommentDto;
import com.demo.redditclone.service.CommentService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/comments/")
@AllArgsConstructor
public class CommentController {
	private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody CommentDto commentDto) {
    	commentService.save(commentDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllComments() {
        return status(HttpStatus.OK).body(commentService.getAllComments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getComment(@PathVariable Long id) {
        return status(HttpStatus.OK).body(commentService.getComment(id));
    }

    @GetMapping("by-post/{id}")
    public ResponseEntity<List<CommentDto>> getCommentsByPost(Long id) {
        return status(HttpStatus.OK).body(commentService.getCommentsByPost(id));
    }

    @GetMapping("by-user/{name}")
    public ResponseEntity<List<CommentDto>> getCommentsByUsername(String username) {
        return status(HttpStatus.OK).body(commentService.getCommentsByUsername(username));
    }
}

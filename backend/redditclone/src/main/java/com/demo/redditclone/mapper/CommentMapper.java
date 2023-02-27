package com.demo.redditclone.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.demo.redditclone.dto.CommentDto;
import com.demo.redditclone.model.Comment;
import com.demo.redditclone.model.Post;
import com.demo.redditclone.model.User;

@Mapper(componentModel = "spring")
public interface CommentMapper {
	
	@Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
	@Mapping(target = "userName", expression = "java(comment.getUser().getUserName())")
    CommentDto mapCommentToDto(Comment comment);


	@Mapping(target = "commentId", ignore = true)
	@Mapping(target = "creatDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "text", source = "commentDto.text")
    @Mapping(target = "post", source = "post")
    Comment mapDtoToComment(CommentDto commentDto, Post post, User user);
}

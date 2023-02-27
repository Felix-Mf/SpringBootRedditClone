package com.demo.redditclone.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
	private Long commentId;
    private Long postId;
    private Instant createDate;
    private String text;
    private String userName;
}

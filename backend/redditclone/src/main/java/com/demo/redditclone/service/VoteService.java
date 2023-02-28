package com.demo.redditclone.service;

import static com.demo.redditclone.model.VoteType.UPVOTE;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.redditclone.dto.VoteDto;
import com.demo.redditclone.exceptions.PostNotFoundException;
import com.demo.redditclone.exceptions.SpringBootRedditException;
import com.demo.redditclone.model.Post;
import com.demo.redditclone.model.Vote;
import com.demo.redditclone.repository.PostRepository;
import com.demo.redditclone.repository.VoteRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VoteService {

	private final VoteRepository voteRepository;
	private final PostRepository postRepository;
	private final AuthService authService;

	@Transactional
	public void vote(VoteDto voteDto) {
		Post post = postRepository.findById(voteDto.getPostId())
				.orElseThrow(() -> new PostNotFoundException("Post Not Found with ID - " + voteDto.getPostId()));
		Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
				authService.getCurrentUser());

		if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
			throw new SpringBootRedditException("You have already " + voteDto.getVoteType() + "'d for this post");
		}

		if (UPVOTE.equals(voteDto.getVoteType())) {
			post.setVoteCount(post.getVoteCount() + 1);
		} else {
			post.setVoteCount(post.getVoteCount() - 1);
		}

		voteRepository.save(mapToVote(voteDto, post));
		postRepository.save(post);
	}

	private Vote mapToVote(VoteDto voteDto, Post post) {
		return Vote.builder().voteType(voteDto.getVoteType()).post(post).user(authService.getCurrentUser()).build();
	}
}

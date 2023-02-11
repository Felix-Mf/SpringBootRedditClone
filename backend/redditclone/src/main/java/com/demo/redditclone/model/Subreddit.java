package com.demo.redditclone.model;

import java.time.Instant;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Subreddit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long subredditId;
	@jakarta.validation.constraints.NotBlank(message = "Subreddit Name can not be empty or null")
	private String subredditName;
	@jakarta.validation.constraints.NotBlank(message = "Description is required")
	private String description;
	private Instant createDate;
	@OneToMany(fetch = FetchType.LAZY)
	private List<Post> posts;
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
}


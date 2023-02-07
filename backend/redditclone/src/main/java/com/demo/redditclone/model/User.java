package com.demo.redditclone.model;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@jakarta.validation.constraints.NotBlank(message = "User Name is required")
	private String name;
	@jakarta.validation.constraints.NotBlank(message = "Password is required")
	private String password;
	@jakarta.validation.constraints.Email
	@jakarta.validation.constraints.NotBlank(message = "Email is required")
	private String Email;
	private Instant createDate;
	private boolean enabled;
}
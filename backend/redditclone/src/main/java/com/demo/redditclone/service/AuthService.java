package com.demo.redditclone.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.redditclone.dto.RegisterRequest;
import com.demo.redditclone.model.Notification;
import com.demo.redditclone.model.User;
import com.demo.redditclone.model.VerificationToken;
import com.demo.redditclone.repository.UserRepository;
import com.demo.redditclone.repository.VerificationTokenRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final VerificationTokenRepository verificationTokenRepository;
	private final MailService mailService;

	@Transactional
	public void signup(RegisterRequest registerRequest) {
		User user = new User();
		user.setEmail(registerRequest.getEmail());
		user.setUserName(registerRequest.getUsername());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setCreateDate(Instant.now());
		user.setEnabled(false);
		userRepository.save(user);

		String token = generateVerificationToken(user);
		
		mailService.sendMail(new Notification("Please activate your account", user.getEmail(),
				"Thank you for signing up, " +
				"please click on the link to activate your account: " +
				"http://localhost:8080/api/auth/accountVerification/" + token));
	}

	private String generateVerificationToken(User user) {
		String token = UUID.randomUUID().toString();
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setUser(user);

		verificationTokenRepository.save(verificationToken);
		return token;
	}
}
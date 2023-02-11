package com.demo.redditclone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder,
			UserDetailsService userDetailService) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(userDetailService)
				.passwordEncoder(passwordEncoder()).and().build();
	}

	@Bean
	public UserDetailsService userDetailsService(BCryptPasswordEncoder bCryptPasswordEncoder) {
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//		manager.createUser(
//				User.withUsername("user").password(bCryptPasswordEncoder.encode("userPass")).roles("USER").build());
//		manager.createUser(User.withUsername("admin").password(bCryptPasswordEncoder.encode("adminPass"))
//				.roles("USER", "ADMIN").build());
		return manager;
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http.csrf()
	      .disable()
	      .authorizeHttpRequests()
	      .requestMatchers("/api/auth/**")
          .permitAll()
//	      .requestMatchers(HttpMethod.DELETE)
//	      .hasRole("ADMIN")
//	      .requestMatchers("/admin/**")
//	      .hasAnyRole("ADMIN")
//	      .requestMatchers("/user/**")
//	      .hasAnyRole("USER", "ADMIN")
//	      .requestMatchers("/login/**")
//	      .anonymous()
	      .anyRequest()
	      .authenticated();
//	      .and()
//	      .httpBasic()
//	      .and()
//	      .sessionManagement()
//	      .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	    return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
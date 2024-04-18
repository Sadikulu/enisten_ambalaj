package com.kss.security;

import com.kss.domains.User;
import com.kss.exception.ResourceNotFoundException;
import com.kss.exception.message.ErrorMessage;
import com.kss.repository.UserRepository;
import com.kss.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) {
		User user =  userRepository.findByEmail(email).orElseThrow(()->
				new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND_MESSAGE));
		return UserDetailsImpl.build(user);
	}

}

package com.kss.security;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthTokenFilter extends OncePerRequestFilter {

	public AuthTokenFilter(JwtUtils jwtUtils, UserDetailsService userDetailsService) {
		this.jwtUtils = jwtUtils;
		this.userDetailsService = userDetailsService;
	}

	private JwtUtils jwtUtils;

	private UserDetailsService userDetailsService;

	private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

	public AuthTokenFilter() {

	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwtToken = parseJwt(request);
		try {
			if (jwtToken != null && jwtUtils.validateJwtToken(jwtToken)) {
				String email = jwtUtils.getEmailFromToken(jwtToken);
				UserDetails userDetails = userDetailsService.loadUserByUsername(email);
				// Valide edilen User bilgilerini SecurityContext'e gönderiyoruz
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		} catch (Exception e) {
			logger.error("User not Found{} :",e.getMessage());
		}
		filterChain.doFilter(request, response);

	}

	private String parseJwt(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
			return header.substring(7);
		}
		return null;
	}

	// filtrelenmemesini istediğim end-pointler
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		AntPathMatcher antPathMatcher = new AntPathMatcher();
		return antPathMatcher.match("/register", request.getServletPath())
				|| antPathMatcher.match("/login", request.getServletPath());
	}

}

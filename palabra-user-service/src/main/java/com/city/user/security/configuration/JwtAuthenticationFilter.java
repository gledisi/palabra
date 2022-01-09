package com.city.user.security.configuration;

import com.city.user.exceptions.InvalidTokenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import sun.net.www.MessageHeader;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	
	private static final String AUTHORIZATION_SCHEMA = "Bearer";

	private final AuthenticationManager authenticationManager;

	private final AuthenticationEntryPoint authenticationEntryPoint;
	
	private final RequestMatcher ignoreMatcher;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
			AuthenticationEntryPoint authenticationEntryPoint, RequestMatcher ignoreMatcher) {
		this.authenticationManager = authenticationManager;
		this.authenticationEntryPoint = authenticationEntryPoint;
		this.ignoreMatcher = ignoreMatcher;
	}

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		try {
			String authorizationToken = validateRequestAndGetToken(request);
			LOGGER.debug("Trying to authenticate user by Token. Token: {}", authorizationToken);
			Authentication authenticationResult = getAuthenticationFromToken(authorizationToken);
			SecurityContext context = SecurityContextHolder.createEmptyContext();
			context.setAuthentication(authenticationResult);
			SecurityContextHolder.setContext(context);
			LOGGER.debug("AuthenticationFilter is passing request down the filter chain");
		} catch (AuthenticationException e) {
			SecurityContextHolder.clearContext();
			LOGGER.error("Internal authentication service exception", e);
			authenticationEntryPoint.commence(request, response, e);
			return;
		}
		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return ignoreMatcher.matches(request);
	}

	private String validateRequestAndGetToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String tokenWs = request.getParameter("token");
		if(tokenWs!=null){
			return tokenWs;
		}
		if (token == null) {
			throw new InvalidTokenException("Authorization header not found");
		}
		if (!token.contains(AUTHORIZATION_SCHEMA)) {
			throw new InvalidTokenException("Authorization schema not found");
		}
		token = token.substring(AUTHORIZATION_SCHEMA.length()).trim();
		return token;
	}

	private Authentication getAuthenticationFromToken(String authenticationToken) {
		Authentication authenticationRequest = new PalabraJwtAuthentication(authenticationToken);

		return authenticationManager.authenticate(authenticationRequest);
	}

}

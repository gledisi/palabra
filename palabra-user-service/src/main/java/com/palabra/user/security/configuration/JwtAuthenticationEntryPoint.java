package com.palabra.user.security.configuration;

import com.palabra.user.dto.ApiErrorResponse;
import com.palabra.user.exceptions.InvalidTokenException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Autowired
	private ObjectMapper mapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		ApiErrorResponse errorDetails;

		if (authException instanceof InvalidTokenException) {
			errorDetails = new ApiErrorResponse("InvalidTokenException", HttpStatus.UNAUTHORIZED);
		} else {
			errorDetails = new ApiErrorResponse(authException.getMessage(), HttpStatus.FORBIDDEN);
		}
		response.setStatus(errorDetails.getStatus().value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		mapper.writeValue(response.getWriter(), errorDetails);
	}
}

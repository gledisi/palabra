package com.palabra.user.security.configuration;

import com.palabra.user.entity.UserRole;
import com.palabra.user.security.model.PalabraTokenDetails;
import com.palabra.user.security.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;


@Component
@Slf4j
public class PalabraAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private JwtService jwtService;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) {
		PalabraJwtAuthentication jwtAuthentication = (PalabraJwtAuthentication) authentication;
		String authenticationToken = (String) jwtAuthentication.getCredentials();
		String code = jwtAuthentication.getCode();
		PalabraTokenDetails tokenDetails = jwtService.parseToken(authenticationToken);
		if(shouldVerifyCode(tokenDetails.getAuthorities())){
			UserDetails userDetail =checkCode(tokenDetails.getMobile(),code);
			return new PalabraJwtAuthentication(userDetail);
		}
		return new PalabraJwtAuthentication(tokenDetails);
	}

	private boolean shouldVerifyCode(Collection<? extends GrantedAuthority> authorities){
		for (GrantedAuthority s : authorities){
			String auth = s.getAuthority();
			if(auth.equals(UserRole.VERIFY_CODE.name())){
				return true;
			}
		}
		return false;
	}

	private UserDetails checkCode(String mobile, String code){
		UserDetails user=userDetailsService.loadUserByUsername(mobile);
		if (user == null) {
			log.debug("Failed to authenticate since no credentials provided");
			throw new BadCredentialsException("Bad credentials");
		} else {
			if (!passwordEncoder.matches(code, user.getPassword())) {
				log.debug("Failed to authenticate since password does not match stored value");
				throw new BadCredentialsException("Bad credentials");
			}
		}
		return user;
	}



	@Override
	public boolean supports(Class<?> authentication) {
		return (PalabraJwtAuthentication.class.isAssignableFrom(authentication));
	}
}
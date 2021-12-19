package com.palabra.user.security.configuration;

import com.palabra.user.security.model.PalabraTokenDetails;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

public class PalabraJwtAuthentication extends AbstractAuthenticationToken {
	
	private static final long serialVersionUID = 1L;
	private String authenticationToken;
	private String code;
	private transient PalabraTokenDetails tokenDetails;

	public PalabraJwtAuthentication(String authenticationToken) {
		super(AuthorityUtils.NO_AUTHORITIES);
		this.authenticationToken = authenticationToken;
		this.setAuthenticated(false);
	}

	public PalabraJwtAuthentication(String authenticationToken,String code) {
		super(AuthorityUtils.NO_AUTHORITIES);
		this.authenticationToken = authenticationToken;
		this.code = code;
		this.setAuthenticated(false);
	}

	public PalabraJwtAuthentication(PalabraTokenDetails tokenDetails) {
		super(tokenDetails.getAuthorities());
		this.eraseCredentials();
		this.tokenDetails = tokenDetails;
		super.setAuthenticated(true);
	}

	public PalabraJwtAuthentication(UserDetails userDetail) {
		super(userDetail.getAuthorities());
		setDetails(userDetail);
		super.setAuthenticated(true);
	}

	@Override
	public void setAuthenticated(boolean authenticated) {
		if (authenticated) {
			throw new IllegalArgumentException(
					"Cannot set this token to trusted. Use constructor which takes a GrantedAuthority list instead");
		}
		super.setAuthenticated(false);
	}

	public String getCode() {
		return code;
	}

	@Override
	public Object getCredentials() {
		return authenticationToken;
	}

	@Override
	public Object getPrincipal() {
		return this.tokenDetails;
	}

	@Override
	public void eraseCredentials() {
		super.eraseCredentials();
		this.authenticationToken = null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((authenticationToken == null) ? 0 : authenticationToken.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PalabraJwtAuthentication other = (PalabraJwtAuthentication) obj;
		if (authenticationToken == null) {
			if (other.authenticationToken != null)
				return false;
		} else if (!authenticationToken.equals(other.authenticationToken)) {
			return false;
		}
			
		return true;
	}


}

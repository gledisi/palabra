package com.city.user.security.model;


import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PalabraTokenDetails {

	private final String id;
	private final String mobile;
	private final List<PalabraAuthority> authorities;
	private final boolean valid;

}

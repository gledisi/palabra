package com.city.user.security.model;

import com.city.user.entity.UserRole;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

public class PalabraAuthority implements GrantedAuthority, Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private UserRole authority;

	public PalabraAuthority() {
		super();
	}

	public PalabraAuthority(Long id, UserRole role) {
		super();
		this.id = id;
		this.authority = role;
	}

	public static PalabraAuthority notUser(){
		return new PalabraAuthority(0L,UserRole.NOT_USER);
	}
	public static PalabraAuthority user(){return new PalabraAuthority(1L,UserRole.USER);}
	public static PalabraAuthority toVerifyCode(){
		return new PalabraAuthority(3L,UserRole.VERIFY_CODE);
	}

	@Override
	public String getAuthority() {
		return authority.name();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Authority [id=" + id + ", authority=" + authority + "]";
	}

}

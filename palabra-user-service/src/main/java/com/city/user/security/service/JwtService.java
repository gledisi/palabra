package com.city.user.security.service;

import com.city.palabra.config.SecurityProperties;
import com.city.user.exceptions.InvalidTokenException;
import com.city.user.security.model.PalabraAuthority;
import com.city.user.security.model.PalabraClaim;
import com.city.user.security.model.PalabraTokenDetails;
import com.city.user.security.model.PalabraUserDetail;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtService {

	@Autowired
	private SecurityProperties properties;

	@Autowired
	private ObjectMapper objectMapper;

	public String issueToken(PalabraUserDetail userDetail) {
		String id = generateTokenIdentifier();
		ZonedDateTime issuedDate = ZonedDateTime.now();
		ZonedDateTime expirationDate = calculateExpirationDate(issuedDate);
		// @formatter:off
			return Jwts.builder()
					.setId(id)
					.setSubject(userDetail.getUsername())
					.setIssuedAt(Date.from(issuedDate.toInstant()))
					.setExpiration(Date.from(expirationDate.toInstant()))
					.claim(PalabraClaim.ROLES, userDetail.getAuthorities())
					.claim(PalabraClaim.USER_ID, userDetail.getUserId())
					.signWith(SignatureAlgorithm.HS256, properties.getJwtSecret())
					.compact();
			// @formatter:on
	}

	public String issueSmsCodeToken(String mobile) {
		String id = generateTokenIdentifier();
		ZonedDateTime issuedDate = ZonedDateTime.now();
		ZonedDateTime expirationDate = calculateSmsExpirationDate(issuedDate);
		// @formatter:off
		return Jwts.builder()
				.setId(id)
				.setSubject(mobile)
				.setIssuedAt(Date.from(issuedDate.toInstant()))
				.setExpiration(Date.from(expirationDate.toInstant()))
				.claim(PalabraClaim.ROLES, Collections.singletonList(PalabraAuthority.toVerifyCode()))
				.signWith(SignatureAlgorithm.HS256, properties.getJwtSecret())
				.compact();
		// @formatter:on
	}

	private ZonedDateTime calculateExpirationDate(ZonedDateTime issuedDate) {
		return issuedDate.plusSeconds(properties.getJwtValidFor());
	}

	private ZonedDateTime calculateSmsExpirationDate(ZonedDateTime issuedDate) {
		return issuedDate.plusSeconds(properties.getSmsJwtValidFor());
	}

	private String generateTokenIdentifier() {
		return UUID.randomUUID().toString();
	}

	public PalabraTokenDetails parseToken(String token) {
		// @formatter:off
			try {
				Claims claims = Jwts.parser()
						.setSigningKey(properties.getJwtSecret())
						.parseClaimsJws(token)
						.getBody();
				return PalabraTokenDetails.builder()
						.id(extractTokenIdFromClaims(claims))
						.userId(extractUserIdFromClaims(claims))
						.mobile(extractMobileFromClaims(claims))
						.authorities(extractAuthoritiesFromClaims(claims))
						.valid(true)
						.build();

			} catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException e) {
				throw new InvalidTokenException("Invalid token", e);
			} catch (ExpiredJwtException e) {
				throw new InvalidTokenException("Expired token", e);
			} catch (InvalidClaimException e) {
				throw new InvalidTokenException("Invalid value for claim \"" + e.getClaimName() + "\"", e);
			} catch (Exception e) {
				throw new InvalidTokenException("Invalid token", e);
			}
			// @formatter:on
	}

	@SuppressWarnings({ "unchecked" })
	private List<PalabraAuthority> extractAuthoritiesFromClaims(@NotNull Claims claims) {
		List<Object> parsedObject = (ArrayList<Object>) claims.getOrDefault(PalabraClaim.ROLES, new ArrayList<>());
		return parsedObject.stream().map(obj -> objectMapper.convertValue(obj, PalabraAuthority.class))
				.collect(Collectors.toList());

	}

	private String extractTokenIdFromClaims(@NotNull Claims claims) {
		return (String) claims.get(Claims.ID);
	}

	private String extractUserIdFromClaims(@NotNull Claims claims) {
		String userId = (String) claims.getOrDefault(PalabraClaim.USER_ID,"");
		return userId;
	}

	private String extractMobileFromClaims(@NotNull Claims claims) {
		return claims.getSubject();
	}

}

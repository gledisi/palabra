package com.palabra.user.security;

import com.palabra.palabra.config.SecurityProperties;
import com.palabra.user.security.configuration.JwtAuthenticationEntryPoint;
import com.palabra.user.security.configuration.JwtAuthenticationFilter;
import com.palabra.user.security.configuration.PalabraAuthenticationProvider;
import com.palabra.user.util.Endpoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.stream.Collectors;


@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class PalabraSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private SecurityProperties properties;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private PalabraAuthenticationProvider jwtAuthenticationProvider;

	@Autowired
	private JwtAuthenticationEntryPoint authenticationEntryPoint;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
		return new JwtAuthenticationFilter(authenticationManagerBean(), authenticationEntryPoint, getRequestMatcher());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		auth.authenticationProvider(jwtAuthenticationProvider);
	}

	@Override
	public void configure(WebSecurity http) {
		http.ignoring().antMatchers(getDisabledUrlPaths());
		http.ignoring().antMatchers(HttpMethod.OPTIONS);
	}

	@Bean
	public CorsFilter corsConfigurationSource() {
		final CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOrigin(properties.getAllowedOrigins());
		configuration.addAllowedOrigin("http://localhost:4200");

		configuration.setAllowedHeaders(
				Arrays.asList(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE, HttpHeaders.CACHE_CONTROL));
		configuration.setAllowedMethods(
				Arrays.stream(HttpMethod.values()).map(HttpMethod::name).collect(Collectors.toList()));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration(Endpoints.ALL, configuration);
		return new CorsFilter(source);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http.csrf().disable();

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.authorizeRequests().antMatchers(Endpoints.SEND_CODE).permitAll().antMatchers(Endpoints.LOGIN).permitAll().antMatchers(Endpoints.SEARCH).permitAll()
				.anyRequest().authenticated();

		http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);

		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

		http.headers().frameOptions().disable();

		// @formatter:on
	}

	private RequestMatcher getRequestMatcher() {
		return new OrRequestMatcher(new AntPathRequestMatcher(Endpoints.SEND_CODE, HttpMethod.POST.toString()),
				new AntPathRequestMatcher(Endpoints.LOGIN, HttpMethod.POST.toString()),
				new AntPathRequestMatcher(Endpoints.SEARCH, HttpMethod.GET.toString()));
	}

	private String[] getDisabledUrlPaths() {
		return new String[] { Endpoints.CSS_RESOURCES, Endpoints.SWAGGER3,Endpoints.IMG_RESOURCES, Endpoints.JS_RESOURCES,
				Endpoints.API_DOCS,Endpoints.API_DOCS_CONFIG, Endpoints.SWAGGER_RESOURCES, Endpoints.CONFIGURATION_UI,
				Endpoints.CONFIGURATION_SECURITY, Endpoints.SWAGGER, Endpoints.WEBJARS };
	}
}

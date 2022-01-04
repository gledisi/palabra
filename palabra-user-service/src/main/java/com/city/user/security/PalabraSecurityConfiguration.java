package com.city.user.security;

import com.city.palabra.config.SecurityProperties;
import com.city.user.security.configuration.JwtAuthenticationEntryPoint;
import com.city.user.security.configuration.JwtAuthenticationFilter;
import com.city.user.security.configuration.PalabraAuthenticationProvider;
import com.city.user.util.Endpoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
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
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOriginPattern(properties.getAllowedOrigins());
		config.setAllowedMethods(Arrays.asList("POST", "OPTIONS", "GET", "DELETE", "PUT"));
		config.setAllowedHeaders(Arrays.asList("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization"));
		source.registerCorsConfiguration(Endpoints.ALL, config);
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.authorizeRequests().antMatchers(Endpoints.SEND_CODE).permitAll()
				.antMatchers(Endpoints.LOGIN).permitAll()
				.antMatchers(Endpoints.SEARCH).permitAll()
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

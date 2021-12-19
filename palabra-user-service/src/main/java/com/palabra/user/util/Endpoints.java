package com.palabra.user.util;

public class Endpoints {

	private Endpoints() {
		super();
	}

	//All
	public static final String ALL = "/**";

	//My Application Endpoints
	public static final String BASE_API_V1 = "/v1/api/";
	public static final String SEARCH = BASE_API_V1+"search/**";

	//USER
	public static final String LOGIN = BASE_API_V1+"login";
	public static final String SEND_CODE = BASE_API_V1+"sendCode";
	public static final String USERS = BASE_API_V1+"users";


	//resources
	public static final String CSS_RESOURCES = "/css/**";
	public static final String IMG_RESOURCES = "/img/**";
	public static final String JS_RESOURCES = "/js/**";
	public static final String API_DOCS = "/v3/api-docs";
	public static final String API_DOCS_CONFIG = "/v3/api-docs/**";
	public static final String SWAGGER_RESOURCES = "/swagger-resources/**";
	public static final String CONFIGURATION_UI = "/configuration/ui/**";
	public static final String CONFIGURATION_SECURITY = "/configuration/security";
	public static final String SWAGGER = "/swagger-ui.html";
	public static final String SWAGGER3 = "/swagger-ui/**";
	public static final String WEBJARS = "/webjars/**";
}

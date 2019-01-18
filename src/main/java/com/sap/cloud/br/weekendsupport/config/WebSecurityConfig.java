package com.sap.cloud.br.weekendsupport.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.sap.xs2.security.commons.SAPOfflineTokenServicesCloud;

@Configuration
@EnableWebSecurity
@EnableResourceServer
public class WebSecurityConfig extends ResourceServerConfigurerAdapter {

	private static final String DISPLAY_SCOPE_SOM = "UserRole";
	private static final String DISPLAY_SCOPE_SOC = "AdminRole";
	public static final String REGEX_TENANT_INDEX = "(!t\\d+)?.";
	private static final String XSAPPNAME = "weekend-support";
	public static final String USER_SCOPE = XSAPPNAME + "." + DISPLAY_SCOPE_SOM;
	public static final String ADMIN_SCOPE = XSAPPNAME + "." + DISPLAY_SCOPE_SOC;

	// configure Spring Security, demand authentication and specific scopes

	@Override
	public void configure(HttpSecurity http) throws Exception { //

		String hasScopeSOMAdmin = "#oauth2.hasScopeMatching('" + XSAPPNAME + REGEX_TENANT_INDEX + DISPLAY_SCOPE_SOM
				+ "')";

		String hasScopeSOCAdmin = "#oauth2.hasScopeMatching('" + XSAPPNAME + REGEX_TENANT_INDEX + DISPLAY_SCOPE_SOC
				+ "')";

		//to enable cors
		http.cors();

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER).and().authorizeRequests()
				.antMatchers("POST", "/abc/def").access(hasScopeSOCAdmin)
				.antMatchers("PUT", "/abc/def").access(hasScopeSOCAdmin)

				.antMatchers("POST", "/efg/**").access(hasScopeSOMAdmin)
				.antMatchers("PUT", "/efg/**").access(hasScopeSOMAdmin)
				.antMatchers("GET", "/efg/**").access(hasScopeSOMAdmin)
				.antMatchers("DELETE", "/efg/**").access(hasScopeSOMAdmin);



//		 http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
	}

	//for cors configuration
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		final CorsConfiguration configuration = new RegexCorsConfiguration();

		List<String> allowedMethods = Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH");

		// The value of the 'Access-Control-Allow-Origin' header in the response
		// must not be the wildcard '*' when the request's credentials mode is
		// 'include'.
		configuration.addAllowedOrigin("https://weekend-ui.cfapps.sap.hana.ondemand.com");
		configuration.addAllowedOrigin("https://(.*)-weekend.cfapps.sap.hana.ondemand.com");
//		configuration.addAllowedOrigin("https://*.cfapps.sap.hana.ondemand.com");
		configuration.addAllowedOrigin("http://localhost:3000");

		configuration.setAllowedMethods(allowedMethods);

		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	// configure offline verification which checks if any provided JWT was
	// properly signed

	//this is required
	@Bean
	protected SAPOfflineTokenServicesCloud offlineTokenServices() {
		return new SAPOfflineTokenServicesCloud();
	}
}
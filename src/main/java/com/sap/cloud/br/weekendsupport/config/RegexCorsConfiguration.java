package com.sap.cloud.br.weekendsupport.config;

import org.springframework.web.cors.CorsConfiguration;

public class RegexCorsConfiguration extends CorsConfiguration {

	@Override
	public String checkOrigin(String requestOrigin) {
		String result = super.checkOrigin(requestOrigin);
		return result != null ? result : checkOriginWithRegularExpression(requestOrigin);
	}

	private String checkOriginWithRegularExpression(String requestOrigin) {
		return getAllowedOrigins().stream()
				.filter(requestOrigin::matches)
				.map(pattern -> requestOrigin)
				.findFirst()
				.orElse(null);
	}
}



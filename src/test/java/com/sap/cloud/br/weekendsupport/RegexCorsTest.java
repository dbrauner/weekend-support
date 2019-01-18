package com.sap.cloud.br.weekendsupport;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class RegexCorsTest {


	@Test
	public void cors() {
		String result = checkOriginWithRegularExpression("https://beta-weekend.cfapps.sap.hana.ondemand.com");

		System.out.println(result);
	}

	private String checkOriginWithRegularExpression(String requestOrigin) {
		return getAllowedOrigins().stream()
				.filter(requestOrigin::matches)
				.map(pattern -> requestOrigin)
				.findFirst()
				.orElse(null);
	}

	private List<String> getAllowedOrigins() {
		List<String> cors = new ArrayList<String>();
		cors.add("https://weekend-ui.cfapps.sap.hana.ondemand.com");
		cors.add("(http|https)://(.*)-weekend.cfapps.sap.hana.ondemand.com");
		//		configuration.addAllowedOrigin("https://*.cfapps.sap.hana.ondemand.com");
		cors.add("http://localhost:3000");
		return cors;
	}
}

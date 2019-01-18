package com.sap.cloud.br.weekendsupport.controller;

import org.springframework.http.MediaType;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello {


	@RequestMapping("/")
	public String say() {
		return "Hello World";
	}

	@RequestMapping("/efg/")
	public String admin() {
		return "Admin rest";
	}

	@RequestMapping(path = "/token", produces = MediaType.APPLICATION_JSON_VALUE)
	public String get(@RequestHeader("Authorization") String authorization) {
		// TODO DO NEVER EXPOSE THIS DATA IN PRODUCTION!!!
		String BEARER = "Bearer";

		if (!authorization.isEmpty() && authorization.startsWith(BEARER)) {

			String tokenContent = authorization.replaceFirst(BEARER, "").trim();

			// Decode JWT token
			Jwt decodedJwt = JwtHelper.decode(tokenContent);

			return decodedJwt.getClaims();
		}
		return "";
	}
}

package com.sap.cloud.br.weekendsupport.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello {


	@RequestMapping("/")
	public String say() {
		return "Hello World";
	}
}

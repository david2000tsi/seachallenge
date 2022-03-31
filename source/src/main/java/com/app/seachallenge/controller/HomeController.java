package com.app.seachallenge.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController implements ErrorController {
	
	private static final String PATH = "/error";
	private static final String BAD_REQUEST_ERR = "BAD_REQUEST_ERR";

	@RequestMapping(value=PATH)
	public String error() {
		return BAD_REQUEST_ERR;
	}
	
	@Override
	public String getErrorPath() {
		return PATH;
	}
}

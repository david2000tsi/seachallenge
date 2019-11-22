package com.app.seachallenge.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController implements ErrorController {
	
	private static final String PATH = "/error";

	@RequestMapping(value=PATH)
	public String error() {
		return UtilsController.getErrorJson(-999, "BAD_REQUEST_ERR");
	}
	
	@Override
	public String getErrorPath() {
		return PATH;
	}
}

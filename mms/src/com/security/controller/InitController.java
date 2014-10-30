package com.security.controller;

import com.jfinal.core.Controller;

public class InitController extends Controller {
	public void index() {

		System.out.println("访问初始化！");
		

		render("/login.html");
	}
	
	

}

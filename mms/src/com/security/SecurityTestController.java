package com.security;

import com.jfinal.core.Controller;

public class SecurityTestController extends Controller {
	
	public void index(){
		renderText("I am index testing !");
	}
	
	public void test(){
		renderText("I am test testing !");
	}

}

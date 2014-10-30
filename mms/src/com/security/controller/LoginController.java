package com.security.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.jfinal.core.Controller;
import com.tools.DictionaryTool;
import com.tools.NICTool;
import com.tools.model.Base_dictionary;

public class LoginController extends Controller {

	public void index() {

		String usernameTemp = getPara("username");
		String passwordTemp = getPara("password");
		HttpServletRequest request = getRequest();
		
		String clientIP = NICTool.getIpAddress(request);
		String clientMAC = NICTool.getMACAddress(clientIP);

		System.out.println("usernameTemp:" + usernameTemp);
		System.out.println("passwordTemp:" + passwordTemp);
		
		
		
		System.out.println("clientIP:" + clientIP);
		System.out.println("clientMAC:" + clientMAC);

		List<Base_dictionary> base_dictionaryList = DictionaryTool
				.findByModuleAndType(DictionaryTool.BSSPROJECT,
						DictionaryTool.PROJECTSTATUS);

		setAttr("usernameTemp", usernameTemp);
		setAttr("passwordTemp", passwordTemp);
		setAttr("base_dictionaryList", base_dictionaryList);

		renderJsp("/index.jsp");
	}

}

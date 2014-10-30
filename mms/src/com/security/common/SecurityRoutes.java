package com.security.common;

import com.jfinal.config.Routes;
import com.security.SecurityTestController;
import com.security.controller.HomeController;
import com.security.controller.InitController;
import com.security.controller.LoginController;

/**
 * 授权系统路由配置类
 * @author GongYuqian
 * 2014年10月17日 下午7:36:14
 */
public class SecurityRoutes extends Routes {

	public void config() {
		add("/", InitController.class);
		
		add("/login", LoginController.class);
		
	}

}

package com.tools;

import java.util.Properties;

import com.alibaba.druid.wall.WallFilter;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.cache.EhCache;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.ViewType;
import com.security.common.SecurityRoutes;
import com.security.model.Security_user;
import com.tools.model.Base_dictionary;

/**
 * 工程基础配置类
 * 
 * @author GongYuqian 2014年10月17日 下午7:36:32
 */
public class BaseConfig extends JFinalConfig {

	public Properties loadPropertyFile(String pro, String dev) {
		try {
			return super.loadPropertyFile(pro);
		} catch (Exception e) {
			return super.loadPropertyFile(dev);
		}
	}

	/**
	 * 常量配置
	 */
	public void configConstant(Constants me) {
		loadPropertyFile("db_config_pro.txt","db_config_dev.txt"); // 加载数据库配置文件

		me.setViewType(ViewType.JSP);

		me.setDevMode(getPropertyToBoolean("devMode", false));


	}

	/**
	 * 访问路由配置
	 */
	public void configRoute(Routes me) {
		me.add(new SecurityRoutes()); // 授权系统路由配置
		

	}

	/**
	 * 插件配置
	 */
	public void configPlugin(Plugins me) {

		String jdbcUrl = getProperty("jdbcUrl");
		String driver = getProperty("driverClass");
		String username = getProperty("username");
		String password = getProperty("password");

		DruidPlugin druidPluginMySql = new DruidPlugin(jdbcUrl, username,
				password, driver);
		WallFilter wallFilter = new WallFilter();
		wallFilter.setDbType("mysql");
		druidPluginMySql.addFilter(wallFilter);
		me.add(druidPluginMySql);

		// ActiveRecord插件
		ActiveRecordPlugin arpMysql = new ActiveRecordPlugin("mysql",
				druidPluginMySql);

		arpMysql.setCache(new EhCache());
		arpMysql.addMapping("base_dictionary", "base_dictionary_id",
				Base_dictionary.class);
		arpMysql.addMapping("security_user", "security_user_id",
				Security_user.class);
		me.add(arpMysql);

		// 加载Shiro插件
		// me.add(new ShiroPlugin(routes));

		// 缓存插件
		me.add(new EhCachePlugin());

	}

	/**
	 * 拦截器配置
	 */
	public void configInterceptor(Interceptors me) {

	}

	/**
	 * 处理器配置
	 */
	public void configHandler(Handlers me) {

	}

	/**
	 * 系统启动后回调方法
	 */
	public void afterJFinalStart() {
		BaseDataLoad bascDataLoad = new BaseDataLoad();
		bascDataLoad.LoadBaseDictionaryToCache(); // 初始化基础字典配置信息至缓存
	}

	/**
	 * 系统关闭前回调方法
	 */
	public void beforeJFinalStop() {
		System.out.println("-------------系统关闭前回调方法---------------");
	}
	
	public static void main(String[] args) {
		JFinal.start("webapp", 80, "/", 5);
	}

}

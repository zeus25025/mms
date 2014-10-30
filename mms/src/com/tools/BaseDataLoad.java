package com.tools;

import java.util.List;

import com.jfinal.plugin.ehcache.CacheKit;
import com.tools.model.Base_dictionary;

public class BaseDataLoad {
	
	/**
	 * 初始化基础字典配置信息方法
	 */
	public void LoadBaseDictionaryToCache() {
		// 查询所有有效的基础字典配置信息
		String sql = "select * from base_dictionary where status = 1 ";
		List<Base_dictionary> base_dictionaryList = CacheKit.get(
				"base_dictionary", "base_dictionaryList");
		if (null == base_dictionaryList) {
			base_dictionaryList = Base_dictionary.dao.find(sql);
			CacheKit.put("base_dictionary", "base_dictionaryList",
					base_dictionaryList);
		}
		if (null != base_dictionaryList) {
			System.out.println("系统初始化：写入缓存的基础字典配置信息共："
					+ base_dictionaryList.size() + "条。");
		} else {
			System.err.println("系统初始化：写入缓存的基础字典配置信息失败！");
		}
	}

}

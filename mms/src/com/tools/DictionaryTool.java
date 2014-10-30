package com.tools;

import java.util.List;

import com.tools.model.Base_dictionary;

public class DictionaryTool {
	public final static String BSSPROJECT = "bss_project";
	public final static String PROJECTSTATUS = "projectstatus";

	public static List<Base_dictionary> findByModuleAndType(String module,
			String type) {
		String sql = "select * from base_dictionary where status = 1 and appmodule = ? and apptype = ? ";
		List<Base_dictionary> base_dictionaryList = Base_dictionary.dao
				.findByCache("base_dictionary", "base_dictionaryList", sql,
						module, type);

		return base_dictionaryList;
	}

}

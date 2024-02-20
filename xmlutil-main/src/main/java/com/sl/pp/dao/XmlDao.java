package com.sl.pp.dao;

import java.util.List;

import com.sl.pp.pojo.Accounts;

public interface XmlDao {
	
	public int saveAccountsRecord(Accounts accounts);
	
	public int updateAccountsRecord(Accounts accounts);
	
	public int saveAccountListData(List<String> accounListString);
	
	public int updateAccountListData(List<String> accounListString);

}

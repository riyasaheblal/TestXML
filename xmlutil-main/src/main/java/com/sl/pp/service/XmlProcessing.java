package com.sl.pp.service;

import java.io.IOException;
import java.util.List;

import com.sl.pp.pojo.Accounts;

public interface XmlProcessing {

	 public List<String> getXMLFiles(String sourcePath, String failedPath);
	 public Accounts processAccountsXml(String fullPath);
	 public boolean insertXmlData(Accounts accounts);
	 public boolean updateXmlData(Accounts accounts);
	 public void moveXmlFileToSuccessFailedFolder(String fileName, String sourcePath, String destinationPath);
	 public void copymoveXmlFileToSuccessFailedFolder(String srcPath, String fileName, String destinationPath) throws IOException;

}

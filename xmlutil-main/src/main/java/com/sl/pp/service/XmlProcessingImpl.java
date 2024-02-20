package com.sl.pp.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.sl.pp.dao.XmlDao;
import com.sl.pp.dao.XmlDaoImpl;
import com.sl.pp.pojo.Account;
import com.sl.pp.pojo.Accounts;
import com.sl.pp.util.CommonConstants;
import com.sl.pp.util.PropertyReader;

public class XmlProcessingImpl implements XmlProcessing {
	private static final Logger logger = LogManager.getLogger(XmlProcessingImpl.class);

	@Override
	public List<String> getXMLFiles(String sourcePath, String failedPath) {

		List<String> xmlFiles = new ArrayList<>();
		try {
			File directory = new File(sourcePath);
			File[] files = directory.listFiles();

			if (files != null) {
				for (File file : files) {
					String checkString = file.getName().length() >= Integer
							.parseInt(PropertyReader.getProperty(CommonConstants.FILENAME_SUBSTRING_FROM))
							&& file.getName().length() >= Integer
							.parseInt(PropertyReader.getProperty(CommonConstants.FILENAME_SUBSTRING_TO))
							+ 1 ? file.getName().substring(
									Integer.parseInt(
											PropertyReader.getProperty(CommonConstants.FILENAME_SUBSTRING_FROM)),
									Integer.parseInt(
											PropertyReader.getProperty(CommonConstants.FILENAME_SUBSTRING_TO)) + 1)
									: null;
							if (file.isFile() && file.getName().toLowerCase().endsWith(".xml")) {
								if (checkString != null && (checkString.toUpperCase()
										.equals(PropertyReader.getProperty(CommonConstants.REQ_FILE_CHECK_STRING))
										|| checkString.toUpperCase()
										.equals(PropertyReader.getProperty(CommonConstants.RES_FILE_CHECK_STRING)))) {
									xmlFiles.add(file.getName());
								} else {
									moveXmlFileToSuccessFailedFolder(file.getName(), sourcePath, failedPath);
									logger.info("XmlProcessingImpl.getXMLFiles() ::"+file.getName()+" Files is XML but not request and response file so its moving to failed folder");
								}
							} else if (file.isFile() && !file.getName().toLowerCase().endsWith(".xml")) {
								moveXmlFileToSuccessFailedFolder(file.getName(), sourcePath, failedPath);
								logger.info("XmlProcessingImpl.getXMLFiles() ::"+file.getName()+" Files is not XML so its moved into failed folder");

							}
				}
				logger.info("XmlProcessingImpl.getXMLFiles() :: total xml files present " + xmlFiles.size());
			} else {
				logger.error("XmlProcessingImpl.getXMLFiles() :: No xml files present ");
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("XmlProcessingImpl.getXMLFiles() ::Error while getting XML files", e);
		}
		return xmlFiles;
	}

	@Override
	public Accounts processAccountsXml(String fullPath) {
		File file = new File(fullPath);
		XmlMapper xmlMapper = new XmlMapper();
		Accounts accountsData = null;
		try {
			accountsData = xmlMapper.readValue(file, Accounts.class);
		} catch (StreamReadException e) {
			e.printStackTrace();
			logger.error("XmlProcessingImpl.processAccountsXml() StreamReadException :: " + e);
		} catch (DatabindException e) {
			e.printStackTrace();
			logger.error("XmlProcessingImpl.processAccountsXml() DatabindException :: " + e);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("XmlProcessingImpl.processAccountsXml() IOException :: " + e);
		}
		return accountsData;
	}

	@Override
	public boolean insertXmlData(Accounts accounts) {
		XmlDao xmlDao = new XmlDaoImpl();
		int savedResult = 0;
		int savedAccountsRecord = 0;
		List<String> accountDataStringList = null;
		try {
			String checkString = accounts.getMessageId().length() >= 11 ? accounts.getMessageId().substring(
					Integer.parseInt(PropertyReader.getProperty(CommonConstants.FILENAME_SUBSTRING_FROM)),
					Integer.parseInt(PropertyReader.getProperty(CommonConstants.FILENAME_SUBSTRING_TO)) + 1) : null;

			if (checkString != null) {
				accountDataStringList = convertAccountToInsertString(accounts);
				savedResult = xmlDao.saveAccountListData(accountDataStringList);
				logger.info("XmlProcessingImpl.insertXmlData()  :: Data inserted sucesssfully in sp_inst");
				if (checkString.toUpperCase().equals(PropertyReader.getProperty(CommonConstants.REQ_FILE_CHECK_STRING)) && savedResult == accounts.getAccounts().size()) {
					savedAccountsRecord = xmlDao.saveAccountsRecord(accounts);
					logger.info("XmlProcessingImpl.insertXmlData()  :: Data inserted sucesssfully in AVSUMMARY");
				}}
			return savedResult == accounts.getAccounts().size() ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("XmlProcessingImpl.insertXmlData():: Error while inserting XML data", e);
			throw new RuntimeException("XmlProcessingImpl.insertXmlData():: Error while inserting XML data", e);
		}
	}



	@Override
	public boolean updateXmlData(Accounts accounts) {
		XmlDao xmlDao = new XmlDaoImpl();
		int updatedAccountsRecord = 0;
		int updatedResult = 0;
		List<String> accountUpdateDataStringList = null;
		try {
			String checkString = accounts.getMessageId().length() >= 11 ? accounts.getMessageId().substring(
					Integer.parseInt(PropertyReader.getProperty(CommonConstants.FILENAME_SUBSTRING_FROM)),
					Integer.parseInt(PropertyReader.getProperty(CommonConstants.FILENAME_SUBSTRING_TO)) + 1) : null;

			if (checkString != null) {
				accountUpdateDataStringList = convertAccountToUpdateString(accounts);
				updatedResult = xmlDao.updateAccountListData(accountUpdateDataStringList);
				logger.info("XmlProcessingImpl.updateXmlData()  :: Data Updated sucesssfully in sp_upd");


				if (checkString.toUpperCase().equals(PropertyReader.getProperty(CommonConstants.RES_FILE_CHECK_STRING)) && updatedResult == accountUpdateDataStringList.size()) {
					updatedAccountsRecord = xmlDao.updateAccountsRecord(accounts);
					logger.info("XmlProcessingImpl.updateXmlData()  :: Data Updated sucesssfully in AVSUMMARY");

				}
			}
			return updatedResult == accountUpdateDataStringList.size() ? true : false;
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("XmlProcessingImpl.insertXmlData():: Error while updating XML data", e);
			throw new RuntimeException("XmlProcessingImpl.insertXmlData():: Error while updating XML data", e);
		}

	}

	@Override
	public void copymoveXmlFileToSuccessFailedFolder(String srcPath, String fileName, String archivePath) throws IOException  {
		Path source = Paths.get(srcPath, fileName);
		Path destination = Paths.get(archivePath, fileName);
		try {
		Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
		}catch (IOException e) {
			e.printStackTrace();
			logger.error("XmlProcessingImpl.copymoveXmlFileToSuccessFailedFolder() IOException :: " + e);
		}
	}

	@Override
	public void moveXmlFileToSuccessFailedFolder(String fileName, String sourcePath, String destinationPath) {

		Path srcPath = Paths.get(sourcePath, fileName);
		Path dstPath = Paths.get(destinationPath, fileName);
		try {
			Files.move(srcPath, dstPath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("XmlProcessingImpl.moveXmlFileToSuccessFailedFolder() IOException :: " + e);
		}
	}

	private static List<String> convertAccountToInsertString(Accounts accounts) {

		List<Account> accountList = accounts.getAccounts();
		List<String> stringList = accountList.stream()
				.map(acc -> new StringJoiner("|").add(accounts.getMessageId()).add(acc.getAccountNumber())
						.add(accounts.getSource()).add(accounts.getDestination()).add(accounts.getBankCode())
						.add(accounts.getBankName()).add(String.valueOf(accounts.getRecordsCount()))
						.add(acc.getEntityCode()).add(acc.getDataRequired()).toString())
				.collect(Collectors.toList());
		return stringList;
	}

	private static List<String> convertAccountToUpdateString(Accounts accounts) {

		List<Account> accountList = accounts.getAccounts();
		List<String> stringList = accountList.stream().map(acc -> new StringJoiner("|").add(accounts.getMessageId())
				.add(acc.getReqMsgId()).add(acc.getAccountNumber()).add(acc.getAccountValidity())
				.add(acc.getAccountStatus()).add(acc.getAccountType()).add(acc.getBsrCode()).add(acc.getIfscCode())
				.add(acc.getAccountOpenDate())
				.add(acc.getAccountCloseDate().length() > 0 ? acc.getAccountCloseDate() : " ")
				.add(acc.getAhDetails().getAh().getEmailID()).add(acc.getAhDetails().getAh().getTan())
				.add(acc.getAhDetails().getAh().getStateLGDCode()).add(acc.getAhDetails().getAh().getState())
				.add(acc.getAhDetails().getAh().getPinCode()).add(acc.getAhDetails().getAh().getPan())
				.add(acc.getAhDetails().getAh().getNationality()).add(acc.getAhDetails().getAh().getName())
				.add(acc.getAhDetails().getAh().getMobile()).add(acc.getAhDetails().getAh().getGender())
				.add(acc.getAhDetails().getAh().getDistrictLGDCode()).add(acc.getAhDetails().getAh().getDistrict())
				.add(acc.getAhDetails().getAh().getDob()).add(acc.getAhDetails().getAh().getAddressLine2())
				.add(acc.getAhDetails().getAh().getAddressLine1()).add(acc.getAhDetails().getAh().getAhType())
				.toString()).collect(Collectors.toList())
				;
		return stringList;
	}

}

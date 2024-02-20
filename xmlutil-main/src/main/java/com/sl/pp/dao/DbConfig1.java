package com.sl.pp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sl.pp.AppMain;
import com.sl.pp.util.CommonConstants;
import com.sl.pp.util.PropertyReader;

public class DbConfig1 {

	private static final Logger logger = LogManager.getLogger(AppMain.class);

	public static Connection getDBConnection1(){

		String url = PropertyReader.getProperty(CommonConstants.DB_URL1);
		String username = PropertyReader.getProperty(CommonConstants.DB_USERNAME1);
		String password = PropertyReader.getProperty(CommonConstants.DB_PASSWORD1);

		try {
			Connection connection = DriverManager.getConnection(url, username, password);
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("DbConfig.getDBConnection() SqlException "+e);
			return null;
		}
	}



}

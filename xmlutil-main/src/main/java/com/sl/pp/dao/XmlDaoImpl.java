package com.sl.pp.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sl.pp.AppMain;
import com.sl.pp.pojo.Accounts;

public class XmlDaoImpl implements XmlDao{

	private static final Logger logger = LogManager.getLogger(AppMain.class);

	@Override
	public int saveAccountsRecord(Accounts accounts) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int rowsInserted = 0;
		try {
			connection = DbConfig1.getDBConnection1();
			String sql = "INSERT INTO AVSUMMARY (BANKCODE, REQMSGID, RECCNT, RESMSGID, ACKMSGID, VALSTAT, VALERROR, RESDATE, CRDATE) " +
					"VALUES (?, ?, ?, ' ', NULL, NULL, NULL, NULL, SYSDATE)";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, accounts.getBankCode());
			preparedStatement.setString(2, accounts.getMessageId());
			preparedStatement.setString(3, String.valueOf(accounts.getRecordsCount()));
			rowsInserted = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("XmlDaoImpl.saveAccountsRecord() SQLException :: "+e);
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("XmlDaoImpl.saveAccountsRecord() SQLException :: "+e);
			}
		}
		return rowsInserted;
	}

	@Override
	public int saveAccountListData(List<String> accounListString) {
		Connection connection = null;
		CallableStatement callableStatement = null;
		int accountsInserted = 0;
		try {
			connection = DbConfig.getDBConnection();
			callableStatement = connection.prepareCall("{CALL sp_inst(string_collection(?))}");
			for (String concatenatedAccount : accounListString) {
				logger.info("XmlDaoImpl.saveAccountListData() :: "+ concatenatedAccount);
				callableStatement.setString(1, concatenatedAccount);
				callableStatement.execute();
				accountsInserted++;
			}	
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("XmlDaoImpl.saveAccountListData() Exception :: "+e);
		}finally {
			try {
				if (callableStatement != null) {
					callableStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			}catch (SQLException e) {
				e.printStackTrace();
				logger.error("XmlDaoImpl.saveAccountListData() SQLException :: "+e);
			}
		}
		return accountsInserted;
	}

	@Override
	public int updateAccountsRecord(Accounts accounts) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int rowsUpdated = 0;

		try {
			connection = DbConfig1.getDBConnection1();
			String sql = "UPDATE AVSUMMARY SET RESMSGID = ? WHERE REQMSGID = ?";
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, accounts.getMessageId());
			preparedStatement.setString(2, accounts.getAccounts().get(0).getReqMsgId());
			rowsUpdated = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("XmlDaoImpl.updateAccountsRecord() SQLException :: "+e);
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("XmlDaoImpl.updateAccountsRecord() SQLException :: "+e);
			}
		}
		return rowsUpdated;
	}

	@Override
	public int updateAccountListData(List<String> accounListString) {
		Connection connection = null;
		CallableStatement callableStatement = null;
		int accountsUpdated = 0;
		try {
			connection = DbConfig.getDBConnection();
			callableStatement = connection.prepareCall("{CALL sp_upd(string_collection(?))}");
			for (String concatenatedAccount : accounListString) {
				logger.info("XmlDaoImpl.updateAccountListData() :: "+ concatenatedAccount);
				System.out.println(concatenatedAccount);
				callableStatement.setString(1, concatenatedAccount);
				callableStatement.execute();
				accountsUpdated++;
			}	
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("XmlDaoImpl.updateAccountListData() Exception :: "+e);

		}finally {
			try {
				if (callableStatement != null) {
					callableStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			}catch (SQLException e) {
				e.printStackTrace();
				logger.error("XmlDaoImpl.updateAccountListData() SQLException :: "+e);
			}
		}
		return accountsUpdated;
	}

}

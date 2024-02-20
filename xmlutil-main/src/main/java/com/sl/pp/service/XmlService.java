package com.sl.pp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sl.pp.pojo.Accounts;
import com.sl.pp.util.CommonConstants;
import com.sl.pp.util.PropertyReader;

public class XmlService {
	private static final Logger logger = LogManager.getLogger(XmlService.class);
	static XmlProcessing xmlProcessing = new XmlProcessingImpl();
	private static final int THREAD_POOL_SIZE = 5;

	public static void performXmlService() {
		try {
			logger.info("InsertProcess::XmlService Started");
			insertProcess();
			logger.info("InsertProcess::XmlService Ended");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("InsertProcess::XmlService ::Error in InsertProcess::XmlService", e);
			throw new RuntimeException("InsertProcess::XmlService:: Error in InsertProcess::XmlService", e);
		}
		try {
			logger.info("UpdateProcess::XmlService Started");
			updateProcess();
			logger.info("UpdateProcess::XmlService Ended");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("UpdateProcess::XmlService Error in UpdateProcess::XmlService", e);
			throw new RuntimeException("UpdateProcess::XmlService Error in UpdateProcess::XmlService", e);

		}
		System.out.println("Goes Under waiting process for 2 min");
	}

	private static void insertProcess() {
		try {
			List<String> fileListReq = xmlProcessing.getXMLFiles(
					PropertyReader.getProperty(CommonConstants.FILE_ROOT_PATH_REQUEST),
					PropertyReader.getProperty(CommonConstants.FILE_ROOT_PATH_REQUEST_FAILED));
			logger.info("XmlService.insertProcess() :: " + fileListReq);

			ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
			List<Callable<Boolean>> callableTasksList = new ArrayList<>();

			try {
				for (String fileName : fileListReq) {
					try {
						String checkString = fileName.substring(
								Integer.parseInt(PropertyReader.getProperty(CommonConstants.FILENAME_SUBSTRING_FROM)),
								Integer.parseInt(PropertyReader.getProperty(CommonConstants.FILENAME_SUBSTRING_TO)) + 1);

						if (checkString.toUpperCase().equals(PropertyReader.getProperty(CommonConstants.REQ_FILE_CHECK_STRING))) {
							Callable<Boolean> callableTask = new Callable<Boolean>() {
								@Override
								public Boolean call() throws Exception {
									try {
										return insertionThread(fileName);
									} catch (Exception e) {
										// Handle exceptions if needed
										e.printStackTrace();
										return false; // or throw a specific exception
									}
								}
							};

							callableTasksList.add(callableTask);
						} else {
							xmlProcessing.moveXmlFileToSuccessFailedFolder(fileName,
									PropertyReader.getProperty(CommonConstants.FILE_ROOT_PATH_REQUEST),
									PropertyReader.getProperty(CommonConstants.FILE_ROOT_PATH_REQUEST_FAILED));
							logger.info("XmlService.insertProcess() ::"+fileName+ " File is not a Request File so its moving to failed folder");
						}
					} catch (StringIndexOutOfBoundsException e) {
						e.printStackTrace();
						logger.error("XmlService.insertProcess() :: Error processing file: " + fileName, e);
					}
				}

				try {
					List<Future<Boolean>> futures = executorService.invokeAll(callableTasksList);
					for (Future<Boolean> future : futures) {
						try {
							logger.info("XmlService.insertProcess() :: file executed " + future.get());
						} catch (Exception e) {
							e.printStackTrace();
							logger.error("XmlService.insertProcess() :: Error getting result from callable", e);
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
					logger.error("XmlService.insertProcess() :: Exception while invoking callable tasks", e);
				} finally {
					executorService.shutdown();
				}

			} catch (Exception e) {
				e.printStackTrace();
				logger.error("XmlService.insertProcess() :: Exception in loop", e);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("XmlService.insertProcess() :: Exception getting XML files", e);
		}
	}

	private static boolean insertionThread(String fileName) {
		try {

			Accounts accounts = xmlProcessing.processAccountsXml(
					PropertyReader.getProperty(CommonConstants.FILE_ROOT_PATH_REQUEST).concat("/").concat(fileName));
			String srcPath = PropertyReader.getProperty(CommonConstants.FILE_ROOT_PATH_REQUEST);
			String dstSuccessPath = PropertyReader.getProperty(CommonConstants.FILE_ROOT_PATH_REQUEST_SUCCESS);
			String dstFailedPath = PropertyReader.getProperty(CommonConstants.FILE_ROOT_PATH_REQUEST_FAILED);
			String dstArchievepath= PropertyReader.getProperty(CommonConstants.FILE_ROOT_PATH_REQUEST_ARCHIEVE);
			boolean processed = xmlProcessing.insertXmlData(accounts);
			if (processed) {
				try {
				xmlProcessing.copymoveXmlFileToSuccessFailedFolder(srcPath,fileName,dstArchievepath);
				logger.info("XmlService.insertionThread() ::File Copied To Archive Folder");
				}
				catch (Exception e) {
					e.printStackTrace();
					logger.error("XmlService.insertionThread() :: Error While Coping The File To Archieve Folder");
				}
				try {
				xmlProcessing.moveXmlFileToSuccessFailedFolder(fileName, srcPath, dstSuccessPath);
				logger.info("XmlService.insertionThread() :: File Moved To Success Folder");
				}
				catch (Exception e) {
					e.printStackTrace();
					logger.error("XmlService.insertionThread() :: Error While Moving File To Success Folder");
				}
			} else {
				try {
				xmlProcessing.moveXmlFileToSuccessFailedFolder(fileName, srcPath, dstFailedPath);
				logger.info("XmlService.insertionThread() :: File Moved To Failed Folder");
				}
				catch (Exception e) {
					e.printStackTrace();
					logger.error("XmlService.insertionThread() :: Error While Moving File To Success Folder");				}
			}

			return processed;
		} catch (Exception e) {
			// Handle the exception or log it
			e.printStackTrace(); // This prints the exception trace to the console
			logger.error("XmlService.insertionThread() :: General exception occurred", e);
			return false;
		}
	}

	private static boolean updationThread(String fileName) {
		try {
			Accounts accounts = xmlProcessing.processAccountsXml(
					PropertyReader.getProperty(CommonConstants.FILE_ROOT_PATH_RESPONSE).concat("/").concat(fileName));
			String srcPath = PropertyReader.getProperty(CommonConstants.FILE_ROOT_PATH_RESPONSE);
			String dstSuccessPath = PropertyReader.getProperty(CommonConstants.FILE_ROOT_PATH_RESPONSE_SUCCESS);
			String dstFailedPath = PropertyReader.getProperty(CommonConstants.FILE_ROOT_PATH_RESPONSE_FAILED);
			String dstArchievepath= PropertyReader.getProperty(CommonConstants.FILE_ROOT_PATH_RESPONSE_ARCHIEVE);

			boolean processed = xmlProcessing.updateXmlData(accounts);
			if (processed) {
				try {
					xmlProcessing.copymoveXmlFileToSuccessFailedFolder(srcPath,fileName,dstArchievepath);
					logger.info("XmlService.updationThread() :: File Copied To Archive Folder");
				}catch (Exception e) {
					e.printStackTrace();
					logger.error("XmlService.updationThread() ::Error while Coping File To Archive Folder");
				}
				try {
					xmlProcessing.moveXmlFileToSuccessFailedFolder(fileName, srcPath, dstSuccessPath);
					logger.info("XmlService.updationThread() :: File Moved to success folder");
				}
				catch (Exception e) {
					e.printStackTrace();
					logger.error("XmlService.updationThread() ::Error While Moving File To Success Folder");
				}
			} else {
				try {
					xmlProcessing.moveXmlFileToSuccessFailedFolder(fileName, srcPath, dstFailedPath);
					logger.info("XmlService.updationThread() ::File Moved to Failed Folder");
				}
				catch (Exception e) {
					e.printStackTrace();
					logger.error("XmlService.updationThread() ::Error While Moving File To Failed Folder");
					}

			}

			return processed;
		} catch (Exception e) {
			e.printStackTrace(); // This prints the exception trace to the console
			logger.error("XmlService.updationThread() :: General exception occurred", e);
			return false;
		}
	}

	private static void updateProcess() {
		try {
			List<String> fileListRes = xmlProcessing
					.getXMLFiles(PropertyReader.getProperty(CommonConstants.FILE_ROOT_PATH_RESPONSE), PropertyReader.getProperty(CommonConstants.FILE_ROOT_PATH_RESPONSE_FAILED));
			logger.info("XmlService.updateProcess() :: "+fileListRes);
			ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
			List<Callable<Boolean>> callableTasksList = new ArrayList<>();

			try {
				for (String fileName : fileListRes) {
					try {
						String checkString = fileName.substring(
								Integer.parseInt(PropertyReader.getProperty(CommonConstants.FILENAME_SUBSTRING_FROM)),
								Integer.parseInt(PropertyReader.getProperty(CommonConstants.FILENAME_SUBSTRING_TO)) + 1);
						if (checkString.toUpperCase()
								.equals(PropertyReader.getProperty(CommonConstants.RES_FILE_CHECK_STRING))) {
							Callable<Boolean> callableTask = new Callable<Boolean>() {
								@Override
								public Boolean call() throws Exception {
									try {
										return updationThread(fileName);
									} catch (Exception e) {
										// Handle exceptions if needed
										e.printStackTrace();
										return false; // or throw a specific exception
									}
								}
							};

							callableTasksList.add(callableTask);
						} else {
							xmlProcessing.moveXmlFileToSuccessFailedFolder(fileName,
									PropertyReader.getProperty(CommonConstants.FILE_ROOT_PATH_RESPONSE), PropertyReader.getProperty(CommonConstants.FILE_ROOT_PATH_RESPONSE_FAILED));
						logger.info("XmlService.updateProcess() :: "+fileName+" File is not a Response File so it moving to failed folder");
						}
					}
					catch (StringIndexOutOfBoundsException e) {
						e.printStackTrace();
						logger.error("XmlService.updateProcess() :: Error processing file: " + fileName, e);
					}
				}

				try {
					List<Future<Boolean>> futures = executorService.invokeAll(callableTasksList);
					for (Future<Boolean> future : futures) {
						try {
							logger.info("XmlService.updateProcess() :: file executed " + future.get());
						} catch (Exception  e) {
							e.printStackTrace();
							logger.error("XmlService.insertProcess() :: Error getting result from callable", e);
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
					logger.error("XmlService.updateProcess()  ::: Exception while callable thread call " + e);
				} finally {
					executorService.shutdown();
				}
			}

			catch (Exception e) {
				e.printStackTrace();
				logger.error("XmlService.updateProcess() :: Exception in loop " + e);
			} finally {
				// Shutdown the ExecutorService outside the try-catch block
				executorService.shutdown();
			}}catch (Exception e) {
				e.printStackTrace();
				logger.error("XmlService.updateProcess() :: Exception getting XML files", e);
			}
	}
}

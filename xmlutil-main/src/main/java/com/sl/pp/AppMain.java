package com.sl.pp;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sl.pp.service.XmlService;

public class AppMain {

	private static final Logger logger = LogManager.getLogger(AppMain.class);

	public static void main(String[] args) {

		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		logger.info("AppMain.main() :: Execution Started ");
		try {
			scheduler.scheduleAtFixedRate(() -> {
				XmlService.performXmlService();
			}, 0, 2, TimeUnit.MINUTES);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AppMain.main() :: Exception while starting the scheduler "+e);
		}
	}

}

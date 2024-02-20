package com.sl.pp.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {

    private static final String CONFIG_FILE = CommonConstants.CONFIG_FILE;

    public static String getProperty(String key) {
        try (InputStream input = PropertyReader.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                System.out.println("Sorry, unable to find " + CONFIG_FILE);
                return null;
            }

            Properties properties = new Properties();
            properties.load(input);

            return properties.getProperty(key);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

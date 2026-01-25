package com.ns.frame.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {
    

    private static final Properties prop = new Properties();

static{

    try ( FileInputStream fis = new FileInputStream("src/test/resources/config.properties")){
       
        prop.load(fis);

    } catch (Exception e) {
        throw new RuntimeException("Failed to load config.properties file");
    }
}
  

public static String getProperty(String key) {
    return prop.getProperty(key);
}

public static int getIntProperty(String key) {
    String value = prop.getProperty(key);
    if (value != null) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Property value for key '" + key + "' is not a valid integer.");
        }
    } else {
        throw new RuntimeException("Property key '" + key + "' not found.");
    }
}


 public static boolean getBoolean(String key) {
        String value = prop.getProperty(key);
        if (value != null) {
            return Boolean.parseBoolean(value.trim());
        } else {
            throw new RuntimeException("Property key '" + key + "' not found.");
        }
    }

    




}

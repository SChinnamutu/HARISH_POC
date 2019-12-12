
package com.mufg.alert.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PropertiesCache {

	private final Properties configProp = new Properties();

	private PropertiesCache() {
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("app.properties");
		try {
			log.info("Property file loading started");
			configProp.load(in);
			log.info("Property file loading end");
		} catch (IOException e) {
			log.error("Exception occred while loading property file in memory  and the exception is " + e);
		}
	} 

	private static class LazyHolder {
		private static final PropertiesCache INSTANCE = new PropertiesCache();
	}

	public static PropertiesCache getInstance() {
		return LazyHolder.INSTANCE;
	}

	public String getProperty(String key) {
		return configProp.getProperty(key);
	}

	public Set<String> getAllPropertyNames() {
		return configProp.stringPropertyNames();
	}

	public boolean containsKey(String key) {
		return configProp.containsKey(key);
	}
}

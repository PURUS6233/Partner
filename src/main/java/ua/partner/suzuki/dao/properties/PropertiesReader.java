package ua.partner.suzuki.dao.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.dao.impl.AbstractFileDao;

public class PropertiesReader {

	public PropertiesReader() {
		this.suzuki_prop = propertyReader();
	}

	private Logger logger = LoggerFactory.getLogger(getClass());
	private String filename = "config/config.properties";
	private Properties suzuki_prop;

	// Getters for specified property values
	// Get database.location property
	public String getDatabaseLocation() {
		return suzuki_prop.getProperty("database.location");
	}

	public Properties propertyReader() {
		Properties prop = new Properties();

		try (InputStream input = AbstractFileDao.class.getClassLoader()
				.getResourceAsStream(filename);) {
			if (input == null) {
				logger.error("The proprerty file " + filename
						+ "can't be found");
				throw new DAOException("The file " + filename
						+ "can't be found");
			}
			// load a properties file from class path, inside static method
			prop.load(input);
		} catch (IOException e) {
			logger.error("Error occuared while property file loading", e);
		} catch (DAOException e) {
			logger.error("The proprerty file " + filename + "can't be found", e);
		}
		return prop;
	}
}

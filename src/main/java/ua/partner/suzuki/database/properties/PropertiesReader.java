package ua.partner.suzuki.database.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.dao.postgres.AbstractJDBCDao;

public class PropertiesReader {

	public PropertiesReader() {
		
	}

	private Logger logger = LoggerFactory.getLogger(getClass());
	private static final String FILE = "config/config.properties";

	// Getters for specified property values
	// Get database.location property


	public Properties propertyReader() {
		Properties prop = new Properties();

		try (InputStream input = AbstractJDBCDao.class.getClassLoader()
				.getResourceAsStream(FILE);) {
			if (input == null) {
				logger.error("The proprerty file " + FILE
						+ "can't be found");
				throw new DAOException("The file " + FILE
						+ "can't be found");
			}
			// load a properties file from class path, inside static method
			prop.load(input);
		} catch (IOException e) {
			logger.error("Error occuared while property file loading", e);
		} catch (DAOException e) {
			logger.error("The proprerty file " + FILE + "can't be found", e);
		}
		return prop;
	}
}

package ua.partner.suzuki.database.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.dao.postgres.AbstractJDBCDao;

public class PropertiesHelper {
	
	public static final String CONFIG_PROPERTIES_FILE = "config/config.properties";
	public static final String DATABASE_PROP_FILE = "db/database.properties";

	public PropertiesHelper() {
	}

	private Logger logger = LoggerFactory.getLogger(getClass());

	public Properties propertyReader(String propertyFile) {
		Properties prop = new Properties();
		try (InputStream input = AbstractJDBCDao.class.getClassLoader()
				.getResourceAsStream(propertyFile);) {
			if (input == null) {
				logger.error("The proprerty file " + propertyFile
						+ "can't be found");
				throw new DAOException("The file " + propertyFile
						+ "can't be found");
			}
			prop.load(input);
		} catch (IOException e) {
			logger.error("Error occuared while property file loading", e);
		} catch (DAOException e) {
			logger.error("The proprerty file " + propertyFile + "can't be found", e);
		}
		return prop;
	}
}

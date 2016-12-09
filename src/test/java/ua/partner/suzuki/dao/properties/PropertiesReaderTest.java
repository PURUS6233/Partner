package ua.partner.suzuki.dao.properties;

import static org.junit.Assert.assertNotNull;

import java.util.Properties;

import org.junit.Test;

import ua.partner.suzuki.database.properties.PropertiesHelper;

public class PropertiesReaderTest {
	
	private static final String propertyFile = "config/config.properties";
	
	@Test
	public void test_type() throws Exception {
		assertNotNull(PropertiesHelper.class);
	}
	
	@Test
	public void test_instantiation() throws Exception {
		PropertiesHelper valid = new PropertiesHelper();
		assertNotNull(valid);
	}

	@Test
	public void test_getDatabaseLocation() {
		PropertiesHelper prop = new PropertiesHelper();
		Properties suzuki_prop = prop.propertyReader(propertyFile);
		String actual = suzuki_prop.getProperty("loader_file.location");
		assertNotNull(actual);
	}

}

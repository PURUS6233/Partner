package ua.partner.suzuki.dao.properties;

import static org.junit.Assert.assertNotNull;

import java.util.Properties;

import org.junit.Test;

import ua.partner.suzuki.database.properties.PropertiesReader;

public class PropertiesReaderTest {
	
	@Test
	public void test_type() throws Exception {
		assertNotNull(PropertiesReader.class);
	}
	
	@Test
	public void test_instantiation() throws Exception {
		PropertiesReader valid = new PropertiesReader();
		assertNotNull(valid);
	}

	@Test
	public void test_getDatabaseLocation() {
		PropertiesReader prop = new PropertiesReader();
		Properties suzuki_prop = prop.propertyReader();
		String actual = suzuki_prop.getProperty("database.location");
		assertNotNull(actual);
	}

}

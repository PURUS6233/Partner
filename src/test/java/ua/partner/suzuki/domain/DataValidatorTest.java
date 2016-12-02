package ua.partner.suzuki.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;

public class DataValidatorTest {
	
	@Test
	public void test_type() throws Exception {
		assertNotNull(DataValidator.class);
	}
	
	@Test
	public void test_instantiation() throws Exception {
		DataValidator valid = new DataValidator();
		assertNotNull(valid);
	}
	
	DataValidator validator = new DataValidator();
	
	@Test
	public void test_checkWithRegExp(){
		boolean actual = validator.checkWithRegExp("14002F", Constants.PREFIX_4_STROKE_PATTERN);
		assertTrue(actual);
	}
	
	@Test
	public void test_divideEngineNumberToPrefixAndSerialNumber() {
		String[] actual = validator.divideEngineNumberToPrefixAndSerialNumber("02002F-310483");
		String[] expected = {"02002F", "310483"}; 
		Assert.assertArrayEquals(expected, actual);
	}
	
	@Test
	public void test_findModelYear() throws DomainException {
		String modelYear = validator.findModelYear("310483");
		assertEquals("13", modelYear);
	}
}

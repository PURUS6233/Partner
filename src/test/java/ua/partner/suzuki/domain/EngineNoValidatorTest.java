package ua.partner.suzuki.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;

public class EngineNoValidatorTest {
	
	@Test
	public void test_type() throws Exception {
		assertNotNull(EngineNoValidatorTest.class);
	}
	
	@Test
	public void test_instantiation() throws Exception {
		EngineNoValidatorTest valid = new EngineNoValidatorTest();
		assertNotNull(valid);
	}
	
	private static final String PATTERN = "^\\d\\d\\d\\d\\d(K|P|F)?$";
	
	EngineNoValidator validator = new EngineNoValidator();
	
	@Test
	public void test_checkWithRegExp(){
		validator.setPatternExpresion(PATTERN);
		boolean actual = validator.checkWithRegExp("14002F");
		assertTrue(actual);
	}
	
	private static final String PREFIX_PATTERN = "^\\d\\d\\d\\d\\d(K|P|F)?$";

	@Test
	public void test_checkPrefix() throws DomainException {
		validator.setPatternExpresion(PREFIX_PATTERN);
		String actual = validator.checkPrefix("02002F");
		assertEquals("02002F", actual);
	}
	
	@Test(expected = DomainException.class)
	public void test_checkPrefix_exception() throws DomainException{
		validator.setPatternExpresion(PREFIX_PATTERN);
		String actual = validator.checkPrefix("02002A");
		assertEquals("02002F", actual);
	}
	
	private static final String SERIAL_NUMBER_PATTERN = "^\\d\\d\\d\\d\\d\\d?$";

	@Test
	public void test_checkSerialNumber() throws DomainException {
		validator.setPatternExpresion(SERIAL_NUMBER_PATTERN);
		String actual = validator.checkSerialNumber("310483");
		assertEquals("310483", actual);
	}
	
	@Test(expected = DomainException.class)
	public void test_checkSerialNumber_exception() throws DomainException {
		validator.setPatternExpresion(SERIAL_NUMBER_PATTERN);
		String actual = validator.checkSerialNumber("310A83");
		assertEquals("310A83", actual);
	}
	
	@Test
	public void test_divideEngineNumberToPrefixAndSerialNumber() {
		String[] actuals = validator.divideEngineNumberToPrefixAndSerialNumber("02002F-310483");
		String[] expecteds = {"02002F", "310483"}; 
		Assert.assertArrayEquals(expecteds, actuals);
	}
	
	@Test
	public void test_findModelYear() throws DomainException {
		String modelYear = validator.findModelYear("310483");
		assertEquals("13", modelYear);
	}
}

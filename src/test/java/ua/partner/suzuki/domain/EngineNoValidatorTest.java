package ua.partner.suzuki.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;

public class EngineNoValidatorTest {
	
	@Test
	public void test_type() throws Exception {
		assertNotNull(EngineNoValidator.class);
	}
	
	@Test
	public void test_instantiation() throws Exception {
		EngineNoValidator valid = new EngineNoValidator();
		assertNotNull(valid);
	}
	
	private static final String PATTERN = "^\\d\\d\\d\\d\\d(K|P|F)?$";
	
	EngineNoValidator validator = new EngineNoValidator();
	
	@Test
	public void test_checkWithRegExp(){
		boolean actual = validator.checkWithRegExp("14002F", PATTERN);
		assertTrue(actual);
	}

	@Test
	public void test_checkPrefix() throws DomainException {
		String actual = validator.checkPrefix("02002F");
		assertEquals("02002F", actual);
	}
	
	@Test(expected = DomainException.class)
	public void test_checkPrefix_exception() throws DomainException{
		String actual = validator.checkPrefix("02002A");
		assertEquals("02002F", actual);
	}

	@Test
	public void test_checkSerialNumber() throws DomainException {
		String actual = validator.checkSerialNumber("310483");
		assertEquals("310483", actual);
	}
	
	@Test(expected = DomainException.class)
	public void test_checkSerialNumber_exception() throws DomainException {
		String actual = validator.checkSerialNumber("310A83");
		assertEquals("310A83", actual);
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

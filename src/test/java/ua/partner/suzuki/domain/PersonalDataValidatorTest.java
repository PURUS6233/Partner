package ua.partner.suzuki.domain;

import static org.junit.Assert.*;

import org.junit.Test;

import ua.partner.suzuki.domain.customer.CustomerType;
import ua.partner.suzuki.domain.customer.SexType;

public class PersonalDataValidatorTest {
	
	@Test
	public void test_type() throws Exception {
		assertNotNull(PersonalDataValidator.class);
	}
	
	private PersonalDataValidator valid = new PersonalDataValidator();
	
	@Test
	public void test_instantiation() throws Exception {
		assertNotNull(valid);
	}
	
	private static final String EMAIL_PATTERN = "^.+@\\w+\\.\\w+$";

	@Test
	public void testCheckWithRegExp() {
		assertTrue(valid.checkWithRegExp("alkomarenko@yandex.ru", EMAIL_PATTERN));
	}

	@Test
	public void testMaleValidator() {
		assertTrue(valid.buyerTypeValidator(CustomerType.GOVERMENT));
	}

	@Test
	public void testBuyerTypeValidator() {
		assertTrue(valid.maleValidator(SexType.MALE));
	}

}

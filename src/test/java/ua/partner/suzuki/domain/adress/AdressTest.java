package ua.partner.suzuki.domain.adress;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ua.partner.suzuki.domain.DomainException;
import ua.partner.suzuki.domain.adress.Address;

public class AdressTest {

	@Test
	public void test_type() throws Exception {
		assertNotNull(Address.class);
	}

	private static final String STREET = "24, Лазаряна";
	private static final String CITY = "Днепр";
	private static final String DISTRICT = "ДИИТ";
	private static final String COUNTRY = "Украина";
	private static final String POST_CODE = "29100";
	private static final String PHONE = "+380385247898";
	private static final String EMAIL = "blabla@mail.ru";

	private static final String expected = "Address {" + " Street=" + STREET
			+ ", City=" + CITY + ", District=" + DISTRICT + ", Country="
			+ COUNTRY + ", Post Code=" + POST_CODE + ", Phone=" + PHONE
			+ ", Email=" + EMAIL + '}';
	
	private Address adress = new Address(STREET, CITY, DISTRICT, COUNTRY, POST_CODE,
			PHONE, EMAIL);
	
	@Test
	public void test_validate() throws DomainException {
		assertTrue(adress.validate());
	}

	@Test
	public void test_instantiation() {
		assertNotNull(adress);
	}

	@Test
	public void test_adress() {
		assertEquals(expected, adress.toString());

	}
}

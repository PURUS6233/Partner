package ua.partner.suzuki.domain.dealer;

import static org.junit.Assert.*;

import org.junit.Test;

import ua.partner.suzuki.domain.DomainException;
import ua.partner.suzuki.domain.adress.Adress;

public class DealerTest {

	@Test
	public void test_type() throws Exception {
		assertNotNull(Dealer.class);
	}

	private static final String STREET = "24, Лазаряна";
	private static final String CITY = "Днепр";
	private static final String DISTRICT = "ДИИТ";
	private static final String COUNTRY = "Украина";
	private static final String POST_CODE = "29100";
	private static final String PHONE = "+380385247898";
	private static final String EMAIL = "blabla@mail.ru";

	private static Adress adress = new Adress(STREET, CITY, DISTRICT, COUNTRY,
			POST_CODE, PHONE, EMAIL);

	private static final String NAME = "GGI";
	private static final String LOGIN = "GGI";
	private static final String PASSWORD = "123123";

	private Dealer dealer = new Dealer(NAME, adress, LOGIN, PASSWORD);
	
	private static final String expected = "Dealer{" + "Name='" + NAME + ", Adress=" + adress.toString()
			+ ", Login=" + LOGIN + ", Password=" + PASSWORD + '}';

	@Test
	public void test_instantiation() throws Exception {
		assertNotNull(dealer);
	}

	@Test
	public void test_dealer() throws Exception {
		System.out.println(dealer);
		assertEquals(expected, dealer.toString());
	}
	
	@Test
	public void test_validate() throws DomainException {
		assertTrue(dealer.validate());
	}

}

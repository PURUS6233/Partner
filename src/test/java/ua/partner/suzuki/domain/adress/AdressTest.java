package ua.partner.suzuki.domain.adress;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import ua.partner.suzuki.domain.DomainException;
import ua.partner.suzuki.domain.adress.Adress;

public class AdressTest {

	@Test
	public void test_type() throws Exception {
		assertNotNull(Adress.class);
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

	public static Adress getCommonAdressData() {
		Adress adress = null;
		try {
			adress = new Adress(STREET, CITY, DISTRICT, COUNTRY, POST_CODE,
					PHONE, EMAIL);
		} catch (DomainException e) {
			e.printStackTrace();
		}
		return adress;
	}

	@Test
	public void test_instantiation() throws Exception {
		Adress adress = getCommonAdressData();
		assertNotNull(adress);
	}

	@Test
	public void test_adress() throws Exception {
		Adress adress = getCommonAdressData();
		assertEquals(expected, adress.toString());

	}
}

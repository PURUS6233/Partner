package ua.partner.suzuki.domain.customer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import ua.partner.suzuki.domain.adress.Address;
import ua.partner.suzuki.domain.customer.CustomerType;
import ua.partner.suzuki.domain.customer.Customer;
import ua.partner.suzuki.domain.customer.SexType;

public class CustomerTest {
	
	private static final String STREET = "24, Лазаряна";
	private static final String CITY = "Днепр";
	private static final String DISTRICT = "ДИИТ";
	private static final String COUNTRY = "Украина";
	private static final String POST_CODE = "29100";
	private static final String PHONE = "+380385247898";
	private static final String EMAIL = "blabla@mail.ru";
	
	private static Address adress = new Address(STREET, CITY, DISTRICT, COUNTRY,
			POST_CODE, PHONE, EMAIL);

	@Test
	public void test_type() throws Exception {
		assertNotNull(Customer.class);
	}

	private static final String ENGINE_NUMBER = "02002F-000000";
	private static final String NAME = "Павел";
	private static final String SURNAME = "Лесев";
	private static final SexType SEX = SexType.MALE;
	private static final CustomerType CUSTOMER_TYPE = CustomerType.PRIVATE_PERSON;

	private Customer customer = new Customer(ENGINE_NUMBER, NAME, SURNAME, SEX,
				adress, CUSTOMER_TYPE);
	
	private static final String expected = "Customer{" + "Engine Number="
			+ ENGINE_NUMBER + ", Name='" + NAME + ", Surname=" + SURNAME
			+ ", Male=" + SEX + ", Adress=" + adress.toString()
			+ ", Customer Type=" + CUSTOMER_TYPE + '}';

	@Test
	public void test_instantiation() throws Exception {
		assertNotNull(customer);
	}

	@Test
	public void test_customer() throws Exception {
		assertEquals(expected, customer.toString());

	}

}

package ua.partner.suzuki.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import ua.partner.suzuki.domain.adress.Adress;
import ua.partner.suzuki.domain.adress.AdressTest;
import ua.partner.suzuki.domain.customer.BuyerType;
import ua.partner.suzuki.domain.customer.Customer;
import ua.partner.suzuki.domain.customer.SexType;

public class CustomerTest {

	@Test
	public void test_type() throws Exception {
		assertNotNull(Customer.class);
	}

	private static final String ENGINE_NUMBER = "02002F-000000";
	private static final String NAME = "Павел";
	private static final String SURNAME = "Лесев";
	private static final SexType SEX = SexType.MALE;
	private static final Adress ADRESS = AdressTest.getCommonAdressData();
	private static final BuyerType BUYER_TYPE = BuyerType.PRIVATE_PERSON;

	private static final String expected = "Customer{" + "Engine Number="
			+ ENGINE_NUMBER + ", Name='" + NAME + ", Surname=" + SURNAME
			+ ", Male=" + SEX + ", Adress=" + ADRESS.toString()
			+ ", Buyer Type=" + BUYER_TYPE + '}';

	protected static Customer getCommonCustomerData() throws DomainException {
		Customer customer = new Customer(ENGINE_NUMBER, NAME, SURNAME, SEX,
				ADRESS, BUYER_TYPE);
		return customer;
	}

	@Test
	public void test_instantiation() throws Exception {
		Customer actual = getCommonCustomerData();
		assertNotNull(actual);
	}

	@Test
	public void test_customer() throws Exception {
		Customer actual = getCommonCustomerData();
		assertEquals(expected, actual.toString());

	}

}

package ua.partner.suzuki.domain.obm;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import ua.partner.suzuki.domain.adress.Address;
import ua.partner.suzuki.domain.customer.CustomerType;
import ua.partner.suzuki.domain.customer.Customer;
import ua.partner.suzuki.domain.customer.SexType;

public class RegistrationTest {

	@Test
	public void test_type() throws Exception {
		assertNotNull(RegistrationBuilder.class);
	}

	private static final String engineNumber_prefix = "02002F";
	private static final String engineNumber_serialNumber = "111111";
	private static final WarrantyType warrantyType = WarrantyType.COMMERCE;
	private static final String dealerName = "GGI";

	private static Address adress = new Address("24, Лазаряна", "Днепр", "ДИИТ",
			"Украина", "29100", "+380385247898", "blabla@mail.ru");

	private static final Customer customer = new Customer("02002F-111111",
			"Павел", "Лесев", SexType.MALE, adress, CustomerType.PRIVATE_PERSON);

	private static final Date dateSold = new Date(new Date().getTime() - 17400000000L);
	private static final Date dateDelivered = new Date(new Date().getTime() - 17400000000L);

	final Registration registration = new RegistrationBuilder(engineNumber_prefix, engineNumber_serialNumber,
			warrantyType, dealerName, customer, dateSold, dateDelivered).engineNumber().warrantyExpiration().penalty().createRegistration();
	
	@Test
	public void test_instantiation() throws Exception {
		assertNotNull(registration);
	}
	
	@Test
	public void test_warrantyExpiration() throws Exception {
		Calendar c = Calendar.getInstance();
		c.setTime(dateSold);
		c.add(Calendar.MONTH, 12);
		assertEquals(c.getTime(), registration.getWarrantyExpiration());
	}
	
	@Test
	public void test_penalty() throws Exception {
		assertEquals(191, registration.getPenalty());
	}
}

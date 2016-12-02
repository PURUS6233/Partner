package ua.partner.suzuki.domain.obm.registration;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import ua.partner.suzuki.domain.adress.Address;
import ua.partner.suzuki.domain.customer.CustomerType;
import ua.partner.suzuki.domain.customer.Customer;
import ua.partner.suzuki.domain.customer.GenderType;
import ua.partner.suzuki.domain.obm.registration.Registration;
import ua.partner.suzuki.domain.obm.registration.RegistrationFiller;
import ua.partner.suzuki.domain.obm.registration.WarrantyType;

public class RegistrationTest {

	@Test
	public void test_type() throws Exception {
		assertNotNull(RegistrationFiller.class);
	}

	private static final String engineNumber = "00252F-002255";
	private static final WarrantyType warrantyType = WarrantyType.COMMERCE;
	private static final String dealerLogin = "GGI";;
	private static final UnitSurvey unitSurvey = UnitSurvey.NONE_OF_THE_FOLLOWING;
	private static final boolean unitOperation = true;
	private static final boolean unitMaintenance = true;
	private static final boolean safetyFeatures = true;
	private static final boolean warrantyPolicy = true;
	private static final boolean ownersSignature = true;

	private static Address adress = new Address("24, Лазаряна", "Днепр",
			"ДИИТ", "Украина", "29100", "+380385247898", "blabla@mail.ru");

	private static final Customer customer = new Customer("02002F-111111",
			"Павел", "Лесев", GenderType.MALE, adress,
			CustomerType.PRIVATE_PERSON);

	private static final Date dateSold = new Date(
			new Date().getTime() - 17400000000L);
	private static final Date dateDelivered = new Date(
			new Date().getTime() - 17400000000L);

	final Registration registration = new RegistrationFiller(new Registration(
			engineNumber, warrantyType, dealerLogin, customer, dateSold,
			dateDelivered, unitSurvey, unitOperation,
			unitMaintenance, safetyFeatures, warrantyPolicy, ownersSignature))
			.fill();

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

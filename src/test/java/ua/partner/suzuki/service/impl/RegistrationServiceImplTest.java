package ua.partner.suzuki.service.impl;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.dao.DaoFactory;
import ua.partner.suzuki.dao.postgres.PostgreRegistrationDao;
import ua.partner.suzuki.domain.adress.Address;
import ua.partner.suzuki.domain.customer.CustomerType;
import ua.partner.suzuki.domain.customer.Customer;
import ua.partner.suzuki.domain.customer.GenderType;
import ua.partner.suzuki.domain.obm.registration.Registration;
import ua.partner.suzuki.domain.obm.registration.RegistrationFiller;
import ua.partner.suzuki.domain.obm.registration.UnitSurvey;
import ua.partner.suzuki.domain.obm.registration.WarrantyType;
import ua.partner.suzuki.service.GenericService;
import ua.partner.suzuki.service.ServiceException;

public class RegistrationServiceImplTest {

	@Test
	public void test_type() throws Exception {
		assertNotNull(RegistrationServiceImpl.class);
	}

	private static final String engineNumber = "00252F-111111";
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

	private static final Customer customer = new Customer("00252F-111111",
			"Павел", "Лесев", GenderType.MALE, adress,
			CustomerType.PRIVATE_PERSON);

	private static final Date dateSold = new Date(
			new Date().getTime() - 17400000000L);
	private static final Date dateDelivered = new Date(
			new Date().getTime() - 17400000000L);

	final Registration registration_A = new RegistrationFiller(
			new Registration(engineNumber, warrantyType, dealerLogin, customer,
					dateSold, dateDelivered, unitSurvey, unitOperation,
					unitMaintenance, safetyFeatures, warrantyPolicy,
					ownersSignature)).fill();

	private static final String engineNumber_B = "14003F-111111";
	private static final WarrantyType warrantyType_B = WarrantyType.PRIVATE;
	private static final String dealerLogin_B = "Logos Sport";

	private static final Customer customer_B = new Customer("14003F-111111",
			"Павел", "Лесев", GenderType.MALE, adress,
			CustomerType.PRIVATE_PERSON);

	private static final Date dateSold_B = new Date();
	private static final Date dateDelivered_B = new Date();

	final Registration registration_B = new RegistrationFiller(
			new Registration(engineNumber_B, warrantyType_B, dealerLogin_B,
					customer_B, dateSold_B, dateDelivered_B, unitSurvey,
					unitOperation, unitMaintenance, safetyFeatures,
					warrantyPolicy, ownersSignature)).fill();

	private final List<Registration> listRegistrations = new LinkedList<Registration>();

	@Mock
	private DaoFactory<Connection> daoFactory;

	@Mock
	private PostgreRegistrationDao dao;

	@Mock
	private GenericService<Customer, String> customerDao;

	@InjectMocks
	private GenericService<Registration, String> service = new RegistrationServiceImpl();

	@Before
	public void setUp() throws DAOException, ServiceException {
		MockitoAnnotations.initMocks(this);
		when(daoFactory.getConnection()).thenReturn(null);
		when(daoFactory.getDao(null, Registration.class)).thenReturn(dao);
		when(dao.add(registration_A)).thenReturn(registration_A);
		when(customerDao.add(customer)).thenReturn(customer);
		when(dao.getByPK("00252F-111111")).thenReturn(registration_A);
		when(dao.getAll()).thenReturn(listRegistrations);
		when(customerDao.get(any())).thenReturn(customer);
		when(dao.update(registration_A)).thenReturn(registration_B);
		when(customerDao.update(customer)).thenReturn(customer);
		when(dao.delete("00252F-111111")).thenReturn(true);
	}

	@Test
	public void test_add() throws ServiceException, DAOException {
		assertEquals(registration_A, service.add(registration_A));
		verify(dao).add(any(Registration.class));
		verify(customerDao).add(any(Customer.class));
	}

	@Test
	public void test_getByPK() throws DAOException, ServiceException {
		assertEquals(registration_A, service.get("00252F-111111"));
		verify(dao).getByPK("00252F-111111");
	}

	@Test
	public void test_getAll() throws DAOException, ServiceException {
		listRegistrations.add(registration_A);
		listRegistrations.add(registration_B);
		assertEquals(listRegistrations, service.getAll());
		verify(dao).getAll();
	}

	 @Test
	 public void test_update() throws DAOException, ServiceException {
		 registration_A.setDealerLogin("Login");
	 assertEquals(registration_B, service.update(registration_A));
	 verify(dao).update(registration_A);
	 verify(customerDao).update(any(Customer.class));
	 }
	
	 @Test
	 public void test_remove() throws DAOException, ServiceException {
	 assertEquals(true, service.remove("00252F-111111"));
	 verify(dao).delete("00252F-111111");
	 }
}

package ua.partner.suzuki.service.impl;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.dao.RegistrationDao;
import ua.partner.suzuki.domain.adress.Address;
import ua.partner.suzuki.domain.customer.CustomerType;
import ua.partner.suzuki.domain.customer.Customer;
import ua.partner.suzuki.domain.customer.SexType;
import ua.partner.suzuki.domain.obm.Registration;
import ua.partner.suzuki.domain.obm.RegistrationBuilder;
import ua.partner.suzuki.domain.obm.WarrantyType;
import ua.partner.suzuki.service.OBMWarehouseException;
import ua.partner.suzuki.service.ServiceException;
import ua.partner.suzuki.service.WarehouseService;

public class RegistrationServiceImplTest {

	private static final String engineNumber_prefix_A = "02002F";
	private static final String engineNumber_serialNumber_A = "111111";
	private static final WarrantyType warrantyType_A = WarrantyType.COMMERCE;
	private static final String dealerName_A = "GGI";

	private static Address adress_A = new Address("24, Лазаряна", "Днепр",
			"ДИИТ", "Украина", "29100", "+380385247898", "blabla@mail.ru");

	private static final Customer customer_A = new Customer("02002F-111111",
			"Павел", "Лесев", SexType.MALE, adress_A, CustomerType.PRIVATE_PERSON);

	private static final Date dateSold_A = new Date(
			new Date().getTime() - 17400000000L);
	private static final Date dateDelivered_A = new Date(
			new Date().getTime() - 17400000000L);

	final Registration registration_A = new RegistrationBuilder(
			engineNumber_prefix_A, engineNumber_serialNumber_A, warrantyType_A, dealerName_A, customer_A,
			dateSold_A, dateDelivered_A).engineNumber().warrantyExpiration().penalty()
			.createRegistration();

	private static final String engineNumber_prefix_B = "14003F";
	private static final String engineNumber_serialNumber_B = "111111";
	private static final WarrantyType warrantyType_B = WarrantyType.PRIVATE;
	private static final String dealerName_B = "Logos Sport";

	private static final Customer customer_B = new Customer("14003F-111111",
			"Павел", "Лесев", SexType.MALE, adress_A, CustomerType.PRIVATE_PERSON);

	private static final Date dateSold_B = new Date();
	private static final Date dateDelivered_B = new Date();

	final Registration registration_B = new RegistrationBuilder(
			engineNumber_prefix_B, engineNumber_serialNumber_B, warrantyType_B, dealerName_B, customer_B,
			dateSold_B, dateDelivered_B).engineNumber().warrantyExpiration().penalty()
			.createRegistration();
	
	private final List<Registration> listRegistration_A = Arrays.asList(registration_A);
	
	@Mock
	private RegistrationDao registrationDao;
	
	@Mock
	private WarehouseService warehouseService;

	@InjectMocks
	private RegistrationServiceImpl service = new RegistrationServiceImpl();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(registrationDao.add(registration_B)).thenReturn(registration_A);
		when(registrationDao.delete("02002F-111111")).thenReturn(true);
		when(registrationDao.isExist("02002F-111111")).thenReturn(true);
		when(registrationDao.get("02002F-111111")).thenReturn(registration_A);
		when(registrationDao.getAll()).thenReturn(listRegistration_A);
		when(registrationDao.init()).thenReturn(true);
		when(registrationDao.update("14003F-111111", registration_B)).thenReturn(registration_A);
		when(registrationDao.writeMapToFile()).thenReturn(true);
	}

	@Test
	public void test_add() throws ServiceException, DAOException, OBMWarehouseException {
		when(registrationDao.isExist("14003F-111111")).thenReturn(false);
		service.add(registration_B);
		verify(registrationDao).init();
		verify(registrationDao).isExist("14003F-111111");
		verify(warehouseService).isExist("14003F-111111");
		verify(registrationDao).add(any());
	}

	@Test
	public void test_get() throws DAOException, ServiceException {
		assertEquals(registration_A, service.get("02002F-111111"));
		verify(registrationDao).init();
		verify(registrationDao).isExist("02002F-111111");
		verify(registrationDao).get("02002F-111111");
	}

	@Test
	public void test_getAll() throws DAOException, ServiceException {
		assertEquals(listRegistration_A, service.getAll());
		verify(registrationDao).init();
		verify(registrationDao).getAll();
	}

	@Test
	public void test_update() throws DAOException, ServiceException {
		assertEquals(registration_A, service.update(registration_B));
		verify(registrationDao).init();
		verify(registrationDao).update("14003F-111111", registration_B);
		verify(registrationDao).writeMapToFile();
	}

	@Test
	public void test_remove() throws DAOException, ServiceException {
		assertEquals(registration_A, service.remove("02002F-111111"));
		verify(registrationDao).init();
		verify(registrationDao).isExist("02002F-111111");
		verify(registrationDao).get("02002F-111111");
		verify(registrationDao).delete("02002F-111111");
		verify(registrationDao).writeMapToFile();
	}

}

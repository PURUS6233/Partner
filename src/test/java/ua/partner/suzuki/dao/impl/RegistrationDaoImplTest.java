package ua.partner.suzuki.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.dao.postgres.PostgreRegistrationDao;
import ua.partner.suzuki.database.properties.PropertiesReader;
import ua.partner.suzuki.domain.adress.Address;
import ua.partner.suzuki.domain.customer.CustomerType;
import ua.partner.suzuki.domain.customer.Customer;
import ua.partner.suzuki.domain.customer.GenderType;
import ua.partner.suzuki.domain.obm.registration.Registration;
import ua.partner.suzuki.domain.obm.registration.RegistrationFiller;
import ua.partner.suzuki.domain.obm.registration.WarrantyType;

import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import com.google.gson.Gson;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Gson.class, Maps.class, Resources.class })
public class RegistrationDaoImplTest {

	private static final String engineNumber_prefix = "02002F";
	private static final String engineNumber_serialNumber = "111111";
	private static final WarrantyType warrantyType = WarrantyType.COMMERCE;
	private static final String dealerName = "GGI";

	private static Address adress = new Address("24, Лазаряна", "Днепр", "ДИИТ",
			"Украина", "29100", "+380385247898", "blabla@mail.ru");

	private static final Customer customer = new Customer("02002F-111111",
			"Павел", "Лесев", GenderType.MALE, adress, CustomerType.PRIVATE_PERSON);

	private static final Date dateSold = new Date(new Date().getTime() - 17400000000L);
	private static final Date dateDelivered = new Date(new Date().getTime() - 17400000000L);

	final Registration registration = new RegistrationFiller(engineNumber_prefix, engineNumber_serialNumber,
			warrantyType, dealerName, customer, dateSold, dateDelivered).warrantyExpiration().penalty().createRegistration();
	
	@Mock
	private final Gson gson = PowerMockito.mock(Gson.class);

	@Mock
	private Map<String, Registration> map;

	@Mock
	private ClassLoader classLoader;

	@Mock
	private PropertiesReader prop;

	@Mock
	private Properties prop_suzuki;

	@Mock
	private BufferedReader reader;

	@Mock
	private FileWriter writer;

	@InjectMocks
	private PostgreRegistrationDao registrationDao = new PostgreRegistrationDao();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void test_init() throws DAOException {
		PowerMockito.mockStatic(Maps.class);
		PowerMockito.mockStatic(Resources.class);
		assertEquals(true, registrationDao.init());
		//verify(map).putAll(any());
	}

	@Test
	public void test_find() throws DAOException {
		when(registrationDao.isExist("02002F-414778")).thenReturn(true);
		assertEquals(true, registrationDao.isExist("02002F-414778"));
		verify(map).containsKey("02002F-414778");
	}

	@Test
	public void test_add() throws DAOException {
		assertEquals(registration, registrationDao.add(registration));
		verify(map).put(registration.getEngineNumber(), registration);
	}

	@Test
	public void test_get() throws DAOException {
		registrationDao.get("02002F-414778");
		verify(map).get("02002F-414778");
	}

	@Test
	public void test_getAll() throws DAOException {
		registrationDao.getAll();
		verify(map).values();
	}

	@Test
	public void test_update() throws DAOException {
		assertEquals(registration, registrationDao.update("02002F-414778", registration));
		verify(map).put("02002F-414778", registration);
	}

	@Test
	public void test_delete() throws DAOException {
		assertEquals(true, registrationDao.delete("02002F-414778"));
		verify(map).remove("02002F-414778");
	}

	@Ignore
	@Test
	// TODO change test
	public void test_writeMapToJson() throws DAOException {
		when(prop_suzuki.getProperty("database.location")).thenReturn(
				"E:/Alex/Projects/eclipse_workspaces/suzuki_database");
		assertEquals(true, registrationDao.writeMapToFile());
		verify(map).values();
	}
}

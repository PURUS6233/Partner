package ua.partner.suzuki.dao.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.FileWriter;
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
import ua.partner.suzuki.dao.postgres.PostgreCustomerDao;
import ua.partner.suzuki.database.properties.PropertiesReader;
import ua.partner.suzuki.domain.adress.Address;
import ua.partner.suzuki.domain.customer.CustomerType;
import ua.partner.suzuki.domain.customer.Customer;
import ua.partner.suzuki.domain.customer.SexType;

import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import com.google.gson.Gson;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Gson.class, Maps.class, Resources.class })
public class CustomerDaoImplTest {

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
	private static final CustomerType BUYER_TYPE = CustomerType.PRIVATE_PERSON;

	private Customer customer = new Customer(ENGINE_NUMBER, NAME, SURNAME, SEX,
				adress, BUYER_TYPE);
	
	@Mock
	private final Gson gson = PowerMockito.mock(Gson.class);

	@Mock
	private Map<String, Customer> map;

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
	private PostgreCustomerDao customerDao = new PostgreCustomerDao();
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void test_init() throws DAOException {
		PowerMockito.mockStatic(Maps.class);
		PowerMockito.mockStatic(Resources.class);
		assertEquals(true, customerDao.init());
	}
	
	@Test
	public void test_find() throws DAOException {
		when(customerDao.isExist("02002F-414778")).thenReturn(true);
		assertEquals(true, customerDao.isExist("02002F-414778"));
		verify(map).containsKey("02002F-414778");
	}

	@Test
	public void test_add() throws DAOException {
		assertEquals(customer, customerDao.add(customer));
		verify(map).put(customer.getEngineNumber(), customer);
	}
	
	@Test
	public void test_get() throws DAOException {
		customerDao.get("02002F-414778");
		verify(map).get("02002F-414778");
	}
	
	@Test
	public void test_getAll() throws DAOException {
		customerDao.getAll();
		verify(map).values();
	}
	
	@Test
	public void test_update() throws DAOException {
		assertEquals(customer, customerDao.update("02002F-414778", customer));
		verify(map).put("02002F-414778", customer);
	}
	
	@Test
	public void test_delete() throws DAOException {
		assertEquals(true, customerDao.delete("02002F-414778"));
		verify(map).remove("02002F-414778");
	}
	
	@Ignore
	@Test
	// TODO change test
	public void test_writeMapToJson() throws DAOException {
		when(prop_suzuki.getProperty("database.location")).thenReturn("E:/Alex/Projects/eclipse_workspaces/suzuki_database");
		assertEquals(true, customerDao.writeMapToFile());
		verify(map).values();
	}

}

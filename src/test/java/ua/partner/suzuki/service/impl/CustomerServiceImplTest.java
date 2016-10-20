package ua.partner.suzuki.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ua.partner.suzuki.dao.CustomerDao;
import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.domain.adress.Adress;
import ua.partner.suzuki.domain.customer.BuyerType;
import ua.partner.suzuki.domain.customer.Customer;
import ua.partner.suzuki.domain.customer.SexType;
import ua.partner.suzuki.service.ServiceException;

public class CustomerServiceImplTest {

	private static final String STREET = "24, Лазаряна";
	private static final String CITY = "Днепр";
	private static final String DISTRICT = "ДИИТ";
	private static final String COUNTRY = "Украина";
	private static final String POST_CODE = "29100";
	private static final String PHONE = "+380385247898";
	private static final String EMAIL = "blabla@mail.ru";
	
	private static Adress adress = new Adress(STREET, CITY, DISTRICT, COUNTRY,
			POST_CODE, PHONE, EMAIL);

	@Test
	public void test_type() throws Exception {
		assertNotNull(Customer.class);
	}

	private static final String ENGINE_NUMBER = "02002F-000000";
	private static final String NAME = "Павел";
	private static final String SURNAME = "Лесев";
	private static final SexType SEX = SexType.MALE;
	private static final BuyerType BUYER_TYPE = BuyerType.PRIVATE_PERSON;

	private Customer customer_A = new Customer(ENGINE_NUMBER, NAME, SURNAME, SEX,
				adress, BUYER_TYPE);
	
	private static final String ENGINE_NUMBER_B = "14003A-000000";
	private static final String NAME_B = "Александр";
	private static final String SURNAME_B = "Комаренко";
	private static final SexType SEX_B = SexType.MALE;
	private static final BuyerType BUYER_TYPE_B = BuyerType.PRIVATE_PERSON;

	private Customer customer_B = new Customer(ENGINE_NUMBER_B, NAME_B, SURNAME_B, SEX_B,
				adress, BUYER_TYPE_B);
	
	private final List<Customer> listcustomer_A = Arrays.asList(customer_A);
	
	@Mock
	private CustomerDao customerDao;

	@InjectMocks
	private CustomerServiceImpl service = new CustomerServiceImpl();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(customerDao.add(customer_B)).thenReturn(customer_A);
		when(customerDao.delete("02002F-000000")).thenReturn(true);
		when(customerDao.isExist("02002F-000000")).thenReturn(true);
		when(customerDao.get("02002F-000000")).thenReturn(customer_A);
		when(customerDao.getAll()).thenReturn(listcustomer_A);
		when(customerDao.init()).thenReturn(true);
		when(customerDao.update("14003A-000000", customer_B)).thenReturn(customer_A);
		when(customerDao.writeMapToFile()).thenReturn(true);
	}

	@Test
	public void test_add() throws ServiceException, DAOException {
		when(customerDao.isExist("14003A-000000")).thenReturn(false);
		service.add(customer_B);
		verify(customerDao).init();
		verify(customerDao).isExist("14003A-000000");
		verify(customerDao).add(any());
	}

	@Test
	public void test_get() throws DAOException, ServiceException {
		assertEquals(customer_A, service.get("02002F-000000"));
		verify(customerDao).init();
		verify(customerDao).isExist("02002F-000000");
		verify(customerDao).get("02002F-000000");
	}

	@Test
	public void test_getAll() throws DAOException, ServiceException {
		assertEquals(listcustomer_A, service.getAll());
		verify(customerDao).init();
		verify(customerDao).getAll();
	}

	@Test
	public void test_update() throws DAOException, ServiceException {
		assertEquals(customer_A, service.update(customer_B));
		verify(customerDao).init();
		verify(customerDao).update("14003A-000000", customer_B);
		verify(customerDao).writeMapToFile();
	}

	@Test
	public void test_remove() throws DAOException, ServiceException {
		assertEquals(customer_A, service.remove("02002F-000000"));
		verify(customerDao).init();
		verify(customerDao).isExist("02002F-000000");
		verify(customerDao).get("02002F-000000");
		verify(customerDao).delete("02002F-000000");
		verify(customerDao).writeMapToFile();
	}
}

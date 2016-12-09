package ua.partner.suzuki.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.dao.DaoFactory;
import ua.partner.suzuki.dao.postgres.PostgreCustomerDao;
import ua.partner.suzuki.domain.adress.Address;
import ua.partner.suzuki.domain.customer.CustomerType;
import ua.partner.suzuki.domain.customer.Customer;
import ua.partner.suzuki.domain.customer.GenderType;
import ua.partner.suzuki.service.GenericService;
import ua.partner.suzuki.service.ServiceException;

public class CustomerServiceImplTest {

	@Test
	public void test_type() throws Exception {
		assertNotNull(Customer.class);
	}

	private static final String STREET = "24, Лазаряна";
	private static final String CITY = "Днепр";
	private static final String DISTRICT = "ДИИТ";
	private static final String COUNTRY = "Украина";
	private static final String POST_CODE = "29100";
	private static final String PHONE = "+380385247898";
	private static final String EMAIL = "blabla@mail.ru";

	private static Address adress = new Address(STREET, CITY, DISTRICT,
			COUNTRY, POST_CODE, PHONE, EMAIL);

	private static final String ENGINE_NUMBER = "02002F-000000";
	private static final String NAME = "Павел";
	private static final String SURNAME = "Лесев";
	private static final GenderType SEX = GenderType.MALE;
	private static final CustomerType BUYER_TYPE = CustomerType.PRIVATE_PERSON;

	private Customer customer_A = new Customer(ENGINE_NUMBER, NAME,
			SURNAME, SEX, adress, BUYER_TYPE);

	private static final String ENGINE_NUMBER_B = "14003A-000000";
	private static final String NAME_B = "Александр";
	private static final String SURNAME_B = "Комаренко";
	private static final GenderType SEX_B = GenderType.MALE;
	private static final CustomerType BUYER_TYPE_B = CustomerType.PRIVATE_PERSON;

	private Customer customer_B = new Customer(ENGINE_NUMBER_B, NAME_B,
			SURNAME_B, SEX_B, adress, BUYER_TYPE_B);

	private final List<Customer> listCustomers = new LinkedList<Customer>();

	@Mock
	private DaoFactory<Connection> daoFactory;

	@Mock
	private PostgreCustomerDao dao;

	@InjectMocks
	private GenericService<Customer, String> service = new CustomerServiceImpl();

	@Before
	public void setUp() throws DAOException {
		MockitoAnnotations.initMocks(this);
		when(daoFactory.getConnection()).thenReturn(null);
		when(daoFactory.getDao(null, Customer.class)).thenReturn(dao);
		when(dao.add(customer_A)).thenReturn(customer_A);
		when(dao.getByPK("14003A-000000")).thenReturn(customer_B);
		when(dao.getAll()).thenReturn(listCustomers);
		when(dao.update(customer_B)).thenReturn(customer_A);
		when(dao.delete("2")).thenReturn(true);
	}

	@Test
	public void test_add() throws ServiceException, DAOException {
		assertEquals(customer_A, service.add(customer_A));
		verify(dao).add(any(Customer.class));
	}

	@Test
	public void test_getByPK() throws DAOException, ServiceException {
		assertEquals(customer_B, service.get("14003A-000000")
				);
		verify(dao).getByPK("14003A-000000");
	}

	@Test
	public void test_getAll() throws DAOException, ServiceException {
		listCustomers.add(customer_B);
		listCustomers.add(customer_A);
		assertEquals(listCustomers, service.getAll());
		verify(dao).getAll();
	}

	@Test
	public void test_update() throws DAOException, ServiceException {
		customer_B.setName("Artem");
		assertEquals(customer_A, service.update(customer_B));
		verify(dao).update(customer_B);
	}

	@Test
	public void test_remove() throws DAOException, ServiceException {
		assertEquals(true, service.remove("2"));
		verify(dao).delete("2");
	}
}

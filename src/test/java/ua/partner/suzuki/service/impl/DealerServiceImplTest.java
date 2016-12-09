package ua.partner.suzuki.service.impl;

import static org.junit.Assert.assertEquals;
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
import ua.partner.suzuki.dao.postgres.PostgreDealerDao;
import ua.partner.suzuki.domain.adress.Address;
import ua.partner.suzuki.domain.dealer.Dealer;
import ua.partner.suzuki.service.GenericService;
import ua.partner.suzuki.service.ServiceException;

public class DealerServiceImplTest {

	private static final String STREET = "24, Лазаряна";
	private static final String CITY = "Днепр";
	private static final String DISTRICT = "ДИИТ";
	private static final String COUNTRY = "Украина";
	private static final String POST_CODE = "29100";
	private static final String PHONE = "+380385247898";
	private static final String EMAIL = "blabla@mail.ru";

	private static Address adress = new Address(STREET, CITY, DISTRICT,
			COUNTRY, POST_CODE, PHONE, EMAIL);

	private static final String NAME = "GGI";
	private static final String LOGIN = "GGI";
	private static final String PASSWORD = "123123";

	private Dealer dealer_A = new Dealer(NAME, adress, LOGIN, PASSWORD);

	private static final String NAME_B = "LOGOS";
	private static final String LOGIN_B = "LSM";
	private static final String PASSWORD_B = "999666";

	private Dealer dealer_B = new Dealer(NAME_B, adress, LOGIN_B, PASSWORD_B);

	private final List<Dealer> listDealers = new LinkedList<Dealer>();

	@Mock
	private DaoFactory<Connection> daoFactory;

	@Mock
	private PostgreDealerDao dao;

	@InjectMocks
	private GenericService<Dealer, String> service = new DealerServiceImpl();

	@Before
	public void setUp() throws DAOException {
		MockitoAnnotations.initMocks(this);
		when(daoFactory.getConnection()).thenReturn(null);
		when(daoFactory.getDao(null, Dealer.class)).thenReturn(dao);
		when(dao.add(dealer_A)).thenReturn(dealer_A);
		when(dao.getByPK("GGI")).thenReturn(dealer_A);
		when(dao.getAll()).thenReturn(listDealers);
		when(dao.update(dealer_B)).thenReturn(dealer_A);
		when(dao.delete("GGI")).thenReturn(true);
	}

	@Test
	public void test_add() throws ServiceException, DAOException {
		assertEquals(dealer_A, service.add(dealer_A));
		verify(dao).add(any(Dealer.class));
	}

	@Test
	public void test_getByPK() throws DAOException, ServiceException {
		assertEquals(dealer_A, service.get("GGI"));
		verify(dao).getByPK("GGI");
	}

	@Test
	public void test_getAll() throws DAOException, ServiceException {
		listDealers.add(dealer_B);
		listDealers.add(dealer_A);
		assertEquals(listDealers, service.getAll());
		verify(dao).getAll();
	}

	@Test
	public void test_update() throws DAOException, ServiceException {
		dealer_A.setName("Name");
		assertEquals(dealer_A, service.update(dealer_B));
		verify(dao).update(dealer_B);
	}

	 @Test
	 public void test_remove() throws DAOException, ServiceException {
	 assertEquals(true, service.remove("GGI"));
	 verify(dao).delete("GGI");
	 }
}

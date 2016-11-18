package ua.partner.suzuki.service.impl;

import static org.junit.Assert.assertEquals;
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

import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.dao.DealerDao;
import ua.partner.suzuki.domain.adress.Address;
import ua.partner.suzuki.domain.dealer.Dealer;
import ua.partner.suzuki.service.ServiceException;

public class DealerServiceImplTest {
	
	private static final String STREET = "24, Лазаряна";
	private static final String CITY = "Днепр";
	private static final String DISTRICT = "ДИИТ";
	private static final String COUNTRY = "Украина";
	private static final String POST_CODE = "29100";
	private static final String PHONE = "+380385247898";
	private static final String EMAIL = "blabla@mail.ru";

	private static Address adress = new Address(STREET, CITY, DISTRICT, COUNTRY,
			POST_CODE, PHONE, EMAIL);

	private static final String NAME = "GGI";
	private static final String LOGIN = "GGI";
	private static final String PASSWORD = "123123";

	private Dealer dealer_A = new Dealer(NAME, adress, LOGIN, PASSWORD);
	
	private static final String NAME_B = "LOGOS";
	private static final String LOGIN_B = "LSM";
	private static final String PASSWORD_B = "999666";
	
	private Dealer dealer_B = new Dealer(NAME_B, adress, LOGIN_B, PASSWORD_B);
	
	private final List<Dealer> listOBM_A = Arrays.asList(dealer_A, dealer_B);

	@Mock
	private DealerDao dealerDao;

	@InjectMocks
	private DealerServiceImpl service = new DealerServiceImpl();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(dealerDao.add(dealer_A)).thenReturn(dealer_B);
		when(dealerDao.delete("GGI")).thenReturn(true);
		when(dealerDao.isExist("GGI")).thenReturn(true);
		when(dealerDao.get("GGI")).thenReturn(dealer_A);
		when(dealerDao.getAll()).thenReturn(listOBM_A);
		when(dealerDao.init()).thenReturn(true);
		when(dealerDao.update("LSM", dealer_B)).thenReturn(dealer_A);
		when(dealerDao.writeMapToFile()).thenReturn(true);
	}

	@Test
	public void test_add() throws ServiceException, DAOException {
		when(dealerDao.isExist("GGI")).thenReturn(false);
		service.add(dealer_A);
		verify(dealerDao).init();
		verify(dealerDao).isExist("GGI");
		verify(dealerDao).add(any());
	}

	@Test
	public void test_get() throws DAOException, ServiceException {
		assertEquals(dealer_A, service.get("GGI"));
		verify(dealerDao).init();
		verify(dealerDao).isExist("GGI");
		verify(dealerDao).get("GGI");
	}

	@Test
	public void test_getAll() throws DAOException, ServiceException {
		assertEquals(listOBM_A, service.getAll());
		verify(dealerDao).init();
		verify(dealerDao).getAll();
	}

	@Test
	public void test_update() throws DAOException, ServiceException {
		assertEquals(dealer_A, service.update(dealer_B));
		verify(dealerDao).init();
		verify(dealerDao).update("LSM", dealer_B);
		verify(dealerDao).writeMapToFile();
	}

	@Test
	public void test_remove() throws DAOException, ServiceException {
		assertEquals(dealer_A, service.remove("GGI"));
		verify(dealerDao).init();
		verify(dealerDao).isExist("GGI");
		verify(dealerDao).get("GGI");
		verify(dealerDao).delete("GGI");
		verify(dealerDao).writeMapToFile();
	}

}

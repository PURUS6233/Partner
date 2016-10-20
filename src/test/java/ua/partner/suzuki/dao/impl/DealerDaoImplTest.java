package ua.partner.suzuki.dao.impl;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
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
import ua.partner.suzuki.database.properties.PropertiesReader;
import ua.partner.suzuki.domain.adress.Adress;
import ua.partner.suzuki.domain.dealer.Dealer;

import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import com.google.gson.Gson;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Gson.class, Maps.class, Resources.class })
public class DealerDaoImplTest {

	@Test
	public void test_type() throws Exception {
		assertNotNull(DealerDaoImpl.class);
	}

	private static final String STREET = "24, Лазаряна";
	private static final String CITY = "Днепр";
	private static final String DISTRICT = "ДИИТ";
	private static final String COUNTRY = "Украина";
	private static final String POST_CODE = "29100";
	private static final String PHONE = "+380385247898";
	private static final String EMAIL = "blabla@mail.ru";

	private static Adress adress = new Adress(STREET, CITY, DISTRICT, COUNTRY,
			POST_CODE, PHONE, EMAIL);

	private static final String NAME = "GGI";
	private static final String LOGIN = "GGI";
	private static final String PASSWORD = "123123";

	private Dealer dealer = new Dealer(NAME, adress, LOGIN, PASSWORD);

	@Mock
	private final Gson gson = PowerMockito.mock(Gson.class);

	@Mock
	private Map<String, Dealer> map;

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
	private DealerDaoImpl dealerDao = new DealerDaoImpl();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void test_init() throws DAOException {
		PowerMockito.mockStatic(Maps.class);
		PowerMockito.mockStatic(Resources.class);
		assertEquals(true, dealerDao.init());
		verify(map).putAll(any());
	}

	@Test
	public void test_find() throws DAOException {
		when(dealerDao.isExist("GGI")).thenReturn(true);
		assertEquals(true, dealerDao.isExist("GGI"));
		verify(map).containsKey("GGI");
	}

	@Test
	public void test_add() throws DAOException {
		assertEquals(dealer, dealerDao.add(dealer));
		verify(map).put(dealer.getLogin(), dealer);
	}

	@Test
	public void test_get() throws DAOException {
		when(map.get("GGI")).thenReturn(dealer);
		dealerDao.get("GGI");
		verify(map).get("GGI");
	}

	@Test
	public void test_getAll() throws DAOException {
		dealerDao.getAll();
		verify(map).values();
	}

	@Test
	public void test_update() throws DAOException {
		assertEquals(dealer, dealerDao.update("GGI", dealer));
		verify(map).put("GGI", dealer);
	}

	@Test
	public void test_delete() throws DAOException {
		assertEquals(true, dealerDao.delete("GGI"));
		verify(map).remove("GGI");
	}

	@Ignore
	@Test
	// TODO change test
	public void test_writeMapToJson() throws DAOException {
		when(prop_suzuki.getProperty("database.location")).thenReturn(
				"E:/Alex/Projects/eclipse_workspaces/suzuki_database");
		assertEquals(true, dealerDao.writeMapToFile());
		verify(map).values();
		verify(prop_suzuki).getProperty("database.location");
		//verify(gson).toJson(map.values(), writer);
	}
}

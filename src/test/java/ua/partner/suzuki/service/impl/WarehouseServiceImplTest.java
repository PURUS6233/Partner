package ua.partner.suzuki.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.dao.DaoFactory;
import ua.partner.suzuki.dao.postgres.PostgreOBMDao;
import ua.partner.suzuki.domain.obm.Model;
import ua.partner.suzuki.domain.obm.OBM;
import ua.partner.suzuki.domain.obm.Status;
import ua.partner.suzuki.service.ServiceException;
import ua.partner.suzuki.service.WarehouseService;

public class WarehouseServiceImplTest { 

	private static final String ENGINE_NUMBER = "02002F-414778";
	private static final String MODEL_YEAR = "14";
	private static final Model MODEL = Model.DF20A;
	private static final Status STATUS = Status.STOLEN;
	private static final OBM obm_A = new OBM(ENGINE_NUMBER, MODEL_YEAR, MODEL,
			STATUS);
	private static final OBM obm_B = new OBM(ENGINE_NUMBER, MODEL_YEAR, MODEL);
	private static final List<OBM> listOBMs = Arrays.asList(obm_A, obm_B);
	
	private static final String INPUT = "02002F-414778, 000000-000000";
	private static final Collection<String> list_wrong_EngineNumbers = Arrays.asList("000000-000000");
	
	private static final InputStream STREAM = new ByteArrayInputStream(
			INPUT.getBytes(StandardCharsets.UTF_8));
	
	@Mock
	private DaoFactory<Connection> daoFactory;
	
	@Mock
	private PostgreOBMDao dao;
	
	@InjectMocks
	private WarehouseService service = new WarehouseServiceImpl();

	@Before
	public void setUp() throws DAOException   {
		MockitoAnnotations.initMocks(this);
		when(daoFactory.getConnection()).thenReturn(null);
		when(daoFactory.getDao(null, OBM.class)).thenReturn(dao);
		when(dao.getByPK("02002F-414778")).thenReturn(obm_B);
		when(dao.getAll()).thenReturn(listOBMs);
		when(dao.update(obm_B)).thenReturn(obm_A);
		when(dao.delete("02002F-414778")).thenReturn(true);
	}

	@Test
	public void test_add() throws Exception {
		assertEquals(list_wrong_EngineNumbers, service.add(STREAM));
		verify(dao).add(any(OBM.class));
	}
	
	@Test
	public void test_get() throws ServiceException, DAOException{
		assertEquals(obm_B.toString(), service.get("02002F-414778").toString());
		verify(dao).getByPK("02002F-414778");
	}

	@Test
	public void test_getAll() throws DAOException, ServiceException {
		assertEquals(listOBMs, service.getAll());
		verify(dao).getAll();
	}

	@Test
	public void test_update() throws DAOException, ServiceException {
		assertEquals(obm_A, service.update(obm_B));
		verify(dao).update(obm_B);
	}

	@Test
	public void test_remove() throws DAOException, ServiceException {
		assertEquals(true, service.remove("02002F-414778"));
		verify(dao).delete("02002F-414778");
	}
}

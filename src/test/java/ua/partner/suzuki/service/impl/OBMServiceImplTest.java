package ua.partner.suzuki.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.dao.OBMDao;
import ua.partner.suzuki.domain.obm.Model;
import ua.partner.suzuki.domain.obm.OBM;
import ua.partner.suzuki.domain.obm.Status;
import ua.partner.suzuki.service.ServiceException;

public class OBMServiceImplTest {

	private static final String ENGINE_NUMBER = "02002F-414778";
	private static final String MODEL_YEAR = "14";
	private static final Model MODEL = Model.DF20A;
	private static final Status STATUS = Status.STOLEN;
	private static final OBM obm_A = new OBM(ENGINE_NUMBER, MODEL_YEAR, MODEL,
			STATUS);
	private static final OBM obm_B = new OBM(ENGINE_NUMBER, MODEL_YEAR, MODEL);
	private static final List<OBM> listOBM_A = Arrays.asList(obm_A);

	@Mock
	private OBMDao engineNumberDao;

	@InjectMocks
	private OBMServiceImpl service = new OBMServiceImpl();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(engineNumberDao.add(obm_B)).thenReturn(obm_A);
		when(engineNumberDao.delete("02002F-414778")).thenReturn(true);
		when(engineNumberDao.find("02002F-414778")).thenReturn(false);
		when(engineNumberDao.get("02002F-414778")).thenReturn(obm_A);
		when(engineNumberDao.getAll()).thenReturn(listOBM_A);
		when(engineNumberDao.init()).thenReturn(true);
		when(engineNumberDao.update("02002F-414778", obm_B)).thenReturn(obm_A);
		when(engineNumberDao.writeMapToFile()).thenReturn(true);
	}

	@Test
	public void test_add() throws ServiceException, DAOException {
		assertEquals(obm_A, service.add(obm_B));
		verify(engineNumberDao).init();
		verify(engineNumberDao).find("02002F-414778");
		verify(engineNumberDao).add(obm_B);
	}

	@Test
	public void test_get() throws DAOException, ServiceException {
		assertEquals(obm_A, service.get("02002F-414778"));
		verify(engineNumberDao).init();
		verify(engineNumberDao).find("02002F-414778");
		verify(engineNumberDao).get("02002F-414778");
	}

	@Test
	public void test_getAll() throws DAOException, ServiceException {
		assertEquals(listOBM_A, service.getAll());
		verify(engineNumberDao).init();
		verify(engineNumberDao).getAll();
	}

	@Test
	public void test_update() throws DAOException, ServiceException {
		assertEquals(obm_A, service.update("02002F-414778", obm_B));
		verify(engineNumberDao).init();
		verify(engineNumberDao).update("02002F-414778", obm_B);
		verify(engineNumberDao).writeMapToFile();
	}

	@Test
	public void test_remove() throws DAOException, ServiceException {
		assertEquals(obm_A, service.remove("02002F-414778"));
		verify(engineNumberDao).init();
		verify(engineNumberDao).find("02002F-414778");
		verify(engineNumberDao).get("02002F-414778");
		verify(engineNumberDao).delete("02002F-414778");
		verify(engineNumberDao).writeMapToFile();
	}
}

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
import ua.partner.suzuki.service.OBMWarehouseException;
import ua.partner.suzuki.service.ServiceException;

public class WarehouseServiceImplTest {

	private static final String ENGINE_NUMBER = "02002F-414778";
	private static final String MODEL_YEAR = "14";
	private static final Model MODEL = Model.DF20A;
	private static final Status STATUS = Status.STOLEN;
	private static final OBM obm_A = new OBM(ENGINE_NUMBER, MODEL_YEAR, MODEL,
			STATUS);
	private static final OBM obm_B = new OBM(ENGINE_NUMBER, MODEL_YEAR, MODEL);
	private static final List<OBM> listOBM_A = Arrays.asList(obm_A);

	@Mock
	private OBMDao warehouseDao;

	@InjectMocks
	private WarehouseServiceImpl service = new WarehouseServiceImpl();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(warehouseDao.add(obm_B)).thenReturn(obm_A);
		when(warehouseDao.delete("02002F-414778")).thenReturn(true);
		when(warehouseDao.isExist("02002F-414778")).thenReturn(true);
		when(warehouseDao.get("02002F-414778")).thenReturn(obm_A);
		when(warehouseDao.getAll()).thenReturn(listOBM_A);
		when(warehouseDao.init()).thenReturn(true);
		when(warehouseDao.update("02002F-414778", obm_B)).thenReturn(obm_A);
		when(warehouseDao.writeMapToFile()).thenReturn(true);
	}

	@Test
	public void test_add() throws ServiceException, DAOException {
		when(warehouseDao.isExist("02002F-414778")).thenReturn(false);
		service.add(obm_B.getEngineNumber());
		verify(warehouseDao).init();
		verify(warehouseDao).isExist("02002F-414778");
		verify(warehouseDao).add(any());
	}

	@Test
	public void test_get() throws DAOException, ServiceException {
		assertEquals(obm_A, service.get("02002F-414778"));
		verify(warehouseDao).init();
		verify(warehouseDao).isExist("02002F-414778");
		verify(warehouseDao).get("02002F-414778");
	}

	@Test
	public void test_getAll() throws DAOException, ServiceException {
		assertEquals(listOBM_A, service.getAll());
		verify(warehouseDao).init();
		verify(warehouseDao).getAll();
	}

	@Test
	public void test_update() throws DAOException, ServiceException {
		assertEquals(obm_A, service.update(obm_B));
		verify(warehouseDao).init();
		verify(warehouseDao).update("02002F-414778", obm_B);
		verify(warehouseDao).writeMapToFile();
	}

	@Test
	public void test_remove() throws DAOException, ServiceException {
		assertEquals(obm_A, service.remove("02002F-414778"));
		verify(warehouseDao).init();
		verify(warehouseDao).isExist("02002F-414778");
		verify(warehouseDao).get("02002F-414778");
		verify(warehouseDao).delete("02002F-414778");
		verify(warehouseDao).writeMapToFile();
	}
	
	@Test
	public void test_isExist() throws DAOException, ServiceException, OBMWarehouseException {
		assertEquals(true, service.isExist("02002F-414778"));
		verify(warehouseDao).init();
		verify(warehouseDao).isExist("02002F-414778");
	}
}

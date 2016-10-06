package ua.partner.suzuki.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.io.FileWriter;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.dao.properties.PropertiesReader;
import ua.partner.suzuki.domain.obm.Model;
import ua.partner.suzuki.domain.obm.OBM;
import ua.partner.suzuki.domain.obm.Status;

import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import com.google.gson.Gson;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Gson.class, Maps.class, Resources.class })
public class WarehouseDaoImplTest {

	private static final String ENGINE_NUMBER = "02002F-414778";
	private static final String MODEL_YEAR = "14";
	private static final Model MODEL = Model.DF20A;
	private static final Status STATUS = Status.STOLEN;
	private static final OBM obm = new OBM(ENGINE_NUMBER, MODEL_YEAR, MODEL,
			STATUS);
	@Mock
	private final Gson gson = PowerMockito.mock(Gson.class);

	@SuppressWarnings("unchecked")
	@Mock
	private final Map<String, OBM> map = PowerMockito.mock(Map.class);

	@Mock
	private final ClassLoader classLoader = mock(ClassLoader.class);

	@Mock
	private final PropertiesReader prop = mock(PropertiesReader.class);

	@Mock
	private final FileWriter writer = mock(FileWriter.class);

	@InjectMocks
	private WarehouseDaoImpl warehauseDao = new WarehouseDaoImpl();

	@Test
	public void test_init() throws DAOException {
		PowerMockito.mockStatic(Maps.class);
		PowerMockito.mockStatic(Resources.class);
		assertEquals(true, warehauseDao.init());
		verify(map).putAll(any());
	}

	@Test
	public void test_find() throws DAOException {
		when(warehauseDao.find("02002F-414778")).thenReturn(true);
		assertEquals(true, warehauseDao.find("02002F-414778"));
		verify(map).containsKey("02002F-414778");
	}

	@Test
	public void test_add() throws DAOException {
		assertEquals(obm, warehauseDao.add(obm));
		verify(map).put("02002F-414778", obm);
	}

	@Test
	public void test_get() throws DAOException {
		warehauseDao.get("02002F-414778");
		verify(map).get("02002F-414778");
	}

	@Test
	public void test_getAll() throws DAOException {
		warehauseDao.getAll();
		verify(map).values();
	}

	@Test
	public void test_update() throws DAOException {
		assertEquals(obm, warehauseDao.update("02002F-414778", obm));
		verify(map).put("02002F-414778", obm);
	}

	@Test
	public void test_delete() throws DAOException {
		assertEquals(true, warehauseDao.delete("02002F-414778"));
		verify(map).remove("02002F-414778");
	}

	@Test(expected = DAOException.class)
	// TODO change test
	public void test_writeMapToJson() throws DAOException {
		assertEquals(true, warehauseDao.writeMapToFile());
		verify(map).values();
		verify(gson).toJson(anyCollection());
		verify(prop).getDatabaseLocation();
		//verify(gson).toJson(anyString(), any());
	}
}

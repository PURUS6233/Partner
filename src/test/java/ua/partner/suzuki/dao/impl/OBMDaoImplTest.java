package ua.partner.suzuki.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.domain.obm.Model;
import ua.partner.suzuki.domain.obm.OBM;
import ua.partner.suzuki.domain.obm.Status;

import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import com.google.gson.Gson;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Gson.class,Maps.class, Resources.class})
public class OBMDaoImplTest {

	private static final String ENGINE_NUMBER = "02002F-414778";
	private static final String MODEL_YEAR = "14";
	private static final Model MODEL = Model.DF20A;
	private static final Status STATUS = Status.STOLEN;
	private static final OBM obm_A = new OBM(ENGINE_NUMBER, MODEL_YEAR, MODEL,
			STATUS);
	private static final OBM obm_B = new OBM(ENGINE_NUMBER, MODEL_YEAR, MODEL);
	private static final List<OBM> listOBM_A = Arrays.asList(obm_A);

	@Mock
	private final Gson gson = PowerMockito.mock(Gson.class);
	
	@SuppressWarnings("unchecked")
	@Mock
	private Map<String, OBM> map = PowerMockito.mock(Map.class);

	@InjectMocks
	private OBMDaoImpl obmDao = new OBMDaoImpl();
	
	@Test
	public void test_init() throws DAOException {
		PowerMockito.mockStatic(Maps.class);
		PowerMockito.mockStatic(Resources.class);
		assertEquals(true, obmDao.init());
		verify(map).putAll(any());
	}

	@Test
	public void test_find() throws DAOException {
		when(obmDao.find("02002F-414778")).thenReturn(true);
		assertEquals(true, obmDao.find("02002F-414778"));
		verify(map).containsKey("02002F-414778");
	}
	
	@Test
	public void test_add() throws DAOException {
		assertEquals(obm_B, obmDao.add(obm_B));
		verify(map).put("02002F-414778", obm_B);
	}
}

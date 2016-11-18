package ua.partner.suzuki.dao.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import com.google.gson.Gson;

import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.dao.postgres.PostgreMaintenanceDao;
import ua.partner.suzuki.database.properties.PropertiesReader;
import ua.partner.suzuki.domain.obm.Maintenance;
import ua.partner.suzuki.domain.obm.MaintenanceType;
import ua.partner.suzuki.domain.obm.OBMMaintenance;
import ua.partner.suzuki.domain.obm.ServiceType;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Gson.class, Maps.class, Resources.class })
public class OBMMaintenanceDaoImplTest {
	
	private static final String ENGINE_NUMBER = "02002F-414778";

	private static final Date executionDate = new Date();
	private static final MaintenanceType maintenanceType = MaintenanceType.FIRST_20_HOURS;
	private static final ServiceType serviceType = ServiceType.PERIODIC_MAINTENANCE;
	private static final String hours = "100";
	private static final String note = "test";
	private static final String diagnosticFileUrlPath = "http//:suzuki.com";

	private static Maintenance maintenance = new Maintenance(executionDate,
			maintenanceType, serviceType, hours, note, diagnosticFileUrlPath);
	
	private OBMMaintenance obmMaintenance = new OBMMaintenance(ENGINE_NUMBER);

	@Mock
	private final Gson gson = PowerMockito.mock(Gson.class);

	@Mock
	private Map<String, OBMMaintenance> map;

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
	private PostgreMaintenanceDao maintenanceDao = new PostgreMaintenanceDao();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void test_init() throws DAOException {
		PowerMockito.mockStatic(Maps.class);
		PowerMockito.mockStatic(Resources.class);
		assertEquals(true, maintenanceDao.init());
		//verify(map).putAll(any());
	}

	@Test
	public void test_add() throws DAOException {
		Map<Date, Maintenance> resource = new HashMap<Date, Maintenance>();
		resource.put(maintenance.getExecutionDate(), maintenance);
		obmMaintenance.setObmMaintenance(resource);
		
		assertEquals(true, maintenanceDao.add(ENGINE_NUMBER, maintenance));
		//verify(map).put(ENGINE_NUMBER, obmMaintenance);
	}

	@Test
	public void test_get() throws DAOException {
		maintenanceDao.get("02002F-414778", maintenance.getExecutionDate());
		verify(map).get("02002F-414778");
	}
//
//	@Test
//	public void test_getAll() throws DAOException {
//		maintenanceDao.getAll();
//		verify(map).values();
//	}
//
//	@Test
//	public void test_update() throws DAOException {
//		assertEquals(registration,
//				maintenanceDao.update("02002F-414778", registration));
//		verify(map).put("02002F-414778", registration);
//	}
//
//	@Test
//	public void test_delete() throws DAOException {
//		assertEquals(true, maintenanceDao.delete("02002F-414778"));
//		verify(map).remove("02002F-414778");
//	}
//
//	@Ignore
//	@Test
//	// TODO change test
//	public void test_writeMapToJson() throws DAOException {
//		when(prop_suzuki.getProperty("database.location")).thenReturn(
//				"E:/Alex/Projects/eclipse_workspaces/suzuki_database");
//		assertEquals(true, maintenanceDao.writeMapToFile());
//		verify(map).values();
//	}

}

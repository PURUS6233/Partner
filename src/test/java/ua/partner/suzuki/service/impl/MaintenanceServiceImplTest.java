package ua.partner.suzuki.service.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.dao.DaoFactory;
import ua.partner.suzuki.dao.postgres.PostgreMaintenanceDao;
import ua.partner.suzuki.domain.obm.maintenance.Maintenance;
import ua.partner.suzuki.domain.obm.maintenance.MaintenanceType;
import ua.partner.suzuki.domain.obm.maintenance.ServiceType;
import ua.partner.suzuki.service.GenericService;
import ua.partner.suzuki.service.ServiceException;

public class MaintenanceServiceImplTest {

	@Test
	public void test_type() throws Exception {
		assertNotNull(MaintenanceServiceImpl.class);
	}

	private static final String engineNumber = "00252F-001122";
	private static final Date executionDate = new Date();
	private static final MaintenanceType maintenanceType = MaintenanceType.FIRST_20_HOURS;
	private static final ServiceType serviceType = ServiceType.PERIODIC_MAINTENANCE;
	private static final String hours = "100";
	private static final String note = "test";
	private static final String SDSFile = "http//:suzuki.com";

	private static final Maintenance maintenance = new Maintenance(
			engineNumber, executionDate, maintenanceType, serviceType, hours,
			note, SDSFile);
	
	List<Maintenance> listMaintenances = new LinkedList<Maintenance>();
	
	@Mock
	private DaoFactory<Connection> daoFactory;
	
	@Mock
	private PostgreMaintenanceDao dao;
	
	@InjectMocks
	private GenericService<Maintenance, Integer> service = new MaintenanceServiceImpl();
	
	@InjectMocks
	private MaintenanceServiceImpl serviceMaintenance = new MaintenanceServiceImpl();

	@Before
	public void setUp() throws DAOException   {
		MockitoAnnotations.initMocks(this);
		when(daoFactory.getConnection()).thenReturn(null);
		when(daoFactory.getDao(null, Maintenance.class)).thenReturn(dao);
		when(dao.add(maintenance)).thenReturn(maintenance);
		when(dao.getByPK(1)).thenReturn(maintenance);
		when(dao.getAll("00252F-001122")).thenReturn(listMaintenances);
		when(dao.update(maintenance)).thenReturn(maintenance);
		when(dao.delete(1)).thenReturn(true);
	}

	@Test
	public void test_add() throws Exception {
		assertEquals(maintenance, service.add(maintenance));
		verify(dao).add(maintenance);
	}
	
	@Test
	public void test_get() throws ServiceException, DAOException{
		assertEquals(maintenance.toString(), service.get(1).toString());
		verify(dao).getByPK(1);
	}

	@Test
	public void test_getAll() throws DAOException, ServiceException {
		listMaintenances.add(maintenance);
		assertEquals(listMaintenances, serviceMaintenance.getAll("00252F-001122"));
		verify(dao).getAll("00252F-001122");
	}

	@Test
	public void test_update() throws DAOException, ServiceException {
		maintenance.setId(1);
		maintenance.setMaintenanceType(MaintenanceType.EVERY_100_HOURS);
		assertEquals(maintenance, service.update(maintenance));
		verify(dao).update(maintenance);
	}

	@Test
	public void test_remove() throws DAOException, ServiceException {
		assertEquals(true, service.remove(1));
		verify(dao).delete(1);
	}
}

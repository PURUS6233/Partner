package ua.partner.suzuki.domain.obm.maintenance;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import ua.partner.suzuki.domain.obm.maintenance.Maintenance;
import ua.partner.suzuki.domain.obm.maintenance.MaintenanceType;
import ua.partner.suzuki.domain.obm.maintenance.ServiceType;

public class MaintenanceTest {

	@Test
	public void test_type() throws Exception {
		assertNotNull(Maintenance.class);
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

	@Test
	public void test_instantiation() throws Exception {
		assertNotNull(maintenance);
	}
	
	@Test
	public void test_maintenanceValidate() throws Exception {
		assertTrue(maintenance.validate());
	}
}

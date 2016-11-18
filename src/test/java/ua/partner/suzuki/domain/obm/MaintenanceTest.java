package ua.partner.suzuki.domain.obm;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class MaintenanceTest {

	@Test
	public void test_type() throws Exception {
		assertNotNull(Maintenance.class);
	}

	private static final Date executionDate = new Date();
	private static final MaintenanceType maintenanceType = MaintenanceType.FIRST_20_HOURS;
	private static final ServiceType serviceType = ServiceType.PERIODIC_MAINTENANCE;
	private static final Integer hours = 100;
	private static final String note = "test";
	private static final String diagnosticFileUrlPath = "http//:suzuki.com";

	private static Maintenance maintenance = new Maintenance(executionDate,
			maintenanceType, serviceType, hours, note, diagnosticFileUrlPath);

	@Test
	public void test_instantiation() throws Exception {
		assertNotNull(maintenance);
	}

	@Test
	public void test_maintenance() throws Exception {
		assertEquals(executionDate, maintenance.getExecutionDate());
	}
}

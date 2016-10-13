package ua.partner.suzuki.domain.obm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;

import ua.partner.suzuki.domain.DomainException;

public class OBMBuilderTest {

	@Test
	public void test_type() throws Exception {
		assertNotNull(OBMBuilder.class);
	}

	private static final String INPUT = "14003F-512134, 00252F-324069, 00602F-313027, 14003F-411425, 14003F-512131, 14003F-411903, 14003A-411902, 00602F-315594, 00602F-31568";

	private static final int size = 7;

	@Test
	public void test_buildOBMFromEngineNumberList() throws DomainException, EngineNoLoaderException {
		OBMBuilder builder = new OBMBuilder(INPUT);
		Collection<OBM> actual = builder.getObms();		
		assertTrue(actual.size() == size);
	}

	private static final String ENGINE_NUMBER = "14003F-512134";
	private static final String MODEL_YEAR = "15";
	private static final Model MODEL = Model.DF140AT;
	private static final Status STATUS = Status.IN_STOCK;

	@Test
	public void test_createOBMFromEngineNumber() throws DomainException {
		OBMBuilder builder = new OBMBuilder();
		OBM actual = builder.createOBMFromEngineNumber("14003F-512134");
		assertTrue(actual.getEngineNumber() == ENGINE_NUMBER);
		assertEquals(actual.getModelYear(), MODEL_YEAR);
		assertTrue(actual.getModel() == MODEL);
		assertTrue(actual.getStatus() == STATUS);
	}
}

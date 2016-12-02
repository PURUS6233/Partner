package ua.partner.suzuki.domain.obm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;

import ua.partner.suzuki.domain.DomainException;
import ua.partner.suzuki.domain.EngineNoLoaderException;

public class OBMConverterTest {

	@Test
	public void test_type() throws Exception {
		assertNotNull(OBMConverter.class);
	}

	private static final Collection<String> INPUT = Arrays.asList(
			"14003F-512134", "00252F-324069", "00602F-313027", "14003F-411425",
			"14003F-512131", "14003F-411903", "00602F-315594");

	private static final int size = 7;

	@Test
	public void test_buildOBMFromEngineNumberList() throws DomainException,
			EngineNoLoaderException {
		OBMConverter converter = new OBMConverter(INPUT);
		Collection<OBM> actual = converter.getObms();
		assertTrue(actual.size() == size);
	}

	private static final String ENGINE_NUMBER = "14003F-512134";
	private static final String MODEL_YEAR = "15";
	private static final Model MODEL = Model.DF140AT;
	private static final Status STATUS = Status.IN_STOCK;

	@Test
	public void test_createOBMFromEngineNumber() throws DomainException {
		OBMConverter converter = new OBMConverter();
		OBM actual = converter.createOBMFromEngineNumber("14003F-512134");
		assertTrue(actual.getEngineNumber() == ENGINE_NUMBER);
		assertEquals(actual.getModelYear(), MODEL_YEAR);
		assertTrue(actual.getModel() == MODEL);
		assertTrue(actual.getStatus() == STATUS);
	}
}

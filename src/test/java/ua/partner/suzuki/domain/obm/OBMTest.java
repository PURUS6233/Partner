package ua.partner.suzuki.domain.obm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class OBMTest {

	@Test
	public void test_type() throws Exception {
		assertNotNull(OBM.class);
	}

	private static final String ENGINE_NUMBER = "02002F-414778";
	private static final String MODEL_YEAR = "14";
	private static final Model MODEL = Model.DF20A;
	private static final Status STATUS = Status.STOLEN;

	@Test
	public void test_obm_$A() throws Exception {
		OBM obm = new OBM(ENGINE_NUMBER, MODEL_YEAR, MODEL);
		assertTrue(obm.validate());
	}

	@Test
	public void test_obm_$B() throws Exception {
		OBM obm = new OBM(ENGINE_NUMBER, MODEL_YEAR, MODEL, STATUS);
		assertTrue(obm.validate());
		assertEquals(STATUS, obm.getStatus());
	}
}

package ua.partner.suzuki.domain.obm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

	private static final String expected = "OBM [engineNumber=" + ENGINE_NUMBER
			+ ", modelYear=" + MODEL_YEAR + ", model=" + MODEL + ", status="
			+ Status.IN_STOCK + ']';

	private static final String expected_2 = "OBM [engineNumber=" + ENGINE_NUMBER
			+ ", modelYear=" + MODEL_YEAR + ", model=" + MODEL + ", status="
			+ STATUS + ']';

	@Test
	public void test_obm_$A() throws Exception {
		OBM obm_A = new OBM(ENGINE_NUMBER, MODEL_YEAR, MODEL);
		assertEquals(expected, obm_A.toString());
	}

	@Test
	public void test_obm_$B() throws Exception {
		OBM obm_B = new OBM(ENGINE_NUMBER, MODEL_YEAR, MODEL, STATUS);
		assertEquals(expected_2, obm_B.toString());
	}
}

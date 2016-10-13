package ua.partner.suzuki.domain.obm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;

public class EngineNumbersLoaderTest {

	private static final String INPUT = "14003F-512134, 0900F-412288, "
			+ "00252F-324069, 00602F-313027, 0, 14003F-411425, 14003F-512131, "
			+ "14003F-411903, 14003F-411902, 00602F-315594, 00602F-31568";

	private static final Collection<String> expected = Arrays.asList(
			"14003F-512134", "00252F-324069", "00602F-313027",
			"14003F-411425", "14003F-512131", "14003F-411903", "14003F-411902",
			"00602F-315594");

	@Test
	public void test_type() throws Exception {
		assertNotNull(EngineNumbersLoader.class);
	}

	private static final InputStream STREAM = new ByteArrayInputStream(
			INPUT.getBytes(StandardCharsets.UTF_8));
	
	@Test
	public void test_engineNoLoader() throws EngineNoLoaderException {
		EngineNumbersLoader loader = new EngineNumbersLoader(STREAM);
		Collection<String> actual = loader.getEngineNumbers();
		assertTrue(actual.size() == expected.size());
		assertEquals(expected.toString(), actual.toString());
	}
	

}

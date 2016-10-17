package ua.partner.suzuki.dao.impl;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.dao.EngineNumbersLoaderDao;

public class EngineNumbersLoaderDaoImplTest {

	private static final String INPUT = "14003F-512134, 0900F-412288, "
			+ "00252F-324069, 00602F-313027, 0, 14003F-411425, 14003F-512131, "
			+ "14003F-411903, 14003F-411902, 00602F-315594, 00602F-31568";

	private static final Collection<String> expected = Arrays.asList(
			"14003F-512134", "0900F-412288", "00252F-324069", "00602F-313027",
			"0", "14003F-411425", "14003F-512131", "14003F-411903",
			"14003F-411902", "00602F-315594", "00602F-31568");

	private final EngineNumbersLoaderDao loader = new EngineNumbersLoaderDaoImpl();

	@Before
	@Test
	public void testWriteToFile() throws DAOException {
		assertEquals(true, loader.writeToFile(INPUT));
	}

	@Test
	public void testReadFromFile() throws DAOException {
		assertEquals(expected, loader.readFromFile());
	}

}
package ua.partner.suzuki.service.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.dao.EngineNumbersLoaderDao;
import ua.partner.suzuki.service.EngineNumbersLoaderService;

public class EngineNumbersLoaderServiceImplTest {
	
	@Mock
	private EngineNumbersLoaderDao loaderDao;

	@InjectMocks
	private EngineNumbersLoaderService service = new EngineNumbersLoaderServiceImpl();
	
	private static final Collection<String> INPUT = Arrays.asList(
			"14003F-512134", "00252F-324069", "00602F-313027",
			"14003F-411425", "14003F-512131", "14003F-411903", "14003F-411902",
			"00602F-315594");
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(loaderDao.writeToFile(INPUT.toString())).thenReturn(true);
		when(loaderDao.readFromFile()).thenReturn(INPUT);
	}

	@Test
	public void test_saveToFile() throws DAOException {
		assertTrue(service.saveToFile(INPUT.toString()));
	}

	@Test
	public void test_readFromFile() throws DAOException {
		service.readFromFile();
		verify(loaderDao).readFromFile();
	}

}

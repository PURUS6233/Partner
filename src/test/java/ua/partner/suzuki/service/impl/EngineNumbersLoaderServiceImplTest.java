package ua.partner.suzuki.service.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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
	
	final String INPUT = "14003F-512134, 0900F-412288, "
			+ "00252F-324069, 00602F-313027, 0, 14003F-411425, 14003F-512131, "
			+ "14003F-411903, 14003F-411902, 00602F-315594, 00602F-3156";
	final InputStream STREAM = new ByteArrayInputStream(
			INPUT.getBytes(StandardCharsets.UTF_8));
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(loaderDao.writeToFile(STREAM)).thenReturn(true);
	}

	@Test
	public void test_saveToFile() throws DAOException {
		assertTrue(service.saveToFile(STREAM));
		verify(loaderDao).writeToFile(STREAM);
	}

	@Test
	public void test_readFromFile() throws DAOException {
		service.readFromFile();
		verify(loaderDao).readFromFile();
	}

}

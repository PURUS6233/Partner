package ua.partner.suzuki.service.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.dao.OBMDao;
import ua.partner.suzuki.service.ServiceException;

public class OBMServiceImplTest {
	
	@Mock
	private OBMDao engineNumberDao;
	
	@InjectMocks
	private OBMServiceImpl service = new OBMServiceImpl(null);
	
	@Test
	public void test_add() throws DAOException, ServiceException{
		OBMDao obmDao = mock(OBMDao.class);
		OBMServiceImpl service = new OBMServiceImpl(obmDao);
		service.add(null);
		verify(service).add(null);
	}
}

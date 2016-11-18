package ua.partner.suzuki.dao.postgres;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.util.List;

import org.junit.Test;

import ua.partner.suzuki.dao.DaoFactory;
import ua.partner.suzuki.dao.GenericDao;
import ua.partner.suzuki.domain.obm.OBM;

public class PostgreOBMDaoTest {

	@Test
	public void testGetAll() throws Exception {
	    DaoFactory<Connection> daoFactory = new PostgreDaoFactory();
	    List<OBM> list;
	    try (Connection con = daoFactory.getConnection()) {
	        GenericDao<OBM, String> dao = daoFactory.getDao(con, OBM.class);
	        list = dao.getAll();
	    }
	    assertNotNull(list);
	    assertTrue(list.size() > 0);
	}

}

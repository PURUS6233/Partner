package ua.partner.suzuki.dao.impl.file;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.reflect.TypeToken;

import ua.partner.suzuki.dao.OBMDao;
import ua.partner.suzuki.domain.customer.Customer;
import ua.partner.suzuki.domain.obm.OBM;

public class OBMDaoImpl extends AbstractFileDao<OBM> implements OBMDao {

	@Override
	protected Class<OBM> getEntityClass() {
		return OBM.class;
	}

	@Override
	protected Type getListType() {
		return new TypeToken<List<Customer>>(){}.getType();
	}

	@Override
	protected String getFileName() {
		return "OBMs.json";
	}
}
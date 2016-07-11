package ua.partner.suzuki.dao.impl.lile;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.reflect.TypeToken;

import ua.partner.suzuki.dao.OBMDao;
import ua.partner.suzuki.domain.Customer;
import ua.partner.suzuki.domain.OBM;

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

	@Override
	public String create(OBM obm) {
		// TODO Auto-generated method stub
		return null;
	}
}

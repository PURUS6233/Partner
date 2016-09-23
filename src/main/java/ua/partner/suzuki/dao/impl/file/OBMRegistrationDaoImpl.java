package ua.partner.suzuki.dao.impl.file;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.reflect.TypeToken;

import ua.partner.suzuki.dao.OBMRegistrationDao;
import ua.partner.suzuki.domain.obm.OBMRegistration;

public class OBMRegistrationDaoImpl extends AbstractFileDao<OBMRegistration> implements
		OBMRegistrationDao {

	@Override
	protected Class<OBMRegistration> getEntityClass() {
		return OBMRegistration.class;
	}

	@Override
	protected Type getListType() {
		return new TypeToken<List<OBMRegistration>>(){}.getType();
	}

	@Override
	protected String getFileName() {
		return "registeredOBMs";
	}
}

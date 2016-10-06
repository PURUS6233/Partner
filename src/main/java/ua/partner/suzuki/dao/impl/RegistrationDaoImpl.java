package ua.partner.suzuki.dao.impl;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.reflect.TypeToken;

import ua.partner.suzuki.dao.RegistrationDao;
import ua.partner.suzuki.domain.obm.Registration;

public class RegistrationDaoImpl extends AbstractFileDao<Registration> implements
		RegistrationDao {

	@Override
	protected Class<Registration> getEntityClass() {
		return Registration.class;
	}

	@Override
	protected Type getListType() {
		return new TypeToken<List<Registration>>(){}.getType();
	}

	@Override
	protected String getFileName() {
		return "registeredOBMs";
	}
}

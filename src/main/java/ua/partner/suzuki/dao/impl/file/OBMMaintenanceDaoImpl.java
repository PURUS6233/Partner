package ua.partner.suzuki.dao.impl.file;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.reflect.TypeToken;

import ua.partner.suzuki.dao.OBMMaintenanceDao;
import ua.partner.suzuki.domain.OBMMaintenance;

public class OBMMaintenanceDaoImpl extends AbstractFileDao<OBMMaintenance> implements
		OBMMaintenanceDao {

	@Override
	protected Class<OBMMaintenance> getEntityClass() {
		return OBMMaintenance.class;
	}

	@Override
	protected Type getListType() {
		return new TypeToken<List<OBMMaintenance>>(){}.getType();
	}

	@Override
	protected String getFileName() {
		return "maintainedOBMs.json";
	}

}

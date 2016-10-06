package ua.partner.suzuki.dao.impl;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.reflect.TypeToken;

import ua.partner.suzuki.dao.WarehouseDao;
import ua.partner.suzuki.domain.obm.OBM;

public class WarehouseDaoImpl extends AbstractFileDao<OBM> implements WarehouseDao {

	@Override
	protected Class<OBM> getEntityClass() {
		return OBM.class;
	}

	@Override
	protected Type getListType() {
		return new TypeToken<List<OBM>>(){}.getType();
	}

	@Override
	protected String getFileName() {
		return "warehouse.json";
	}
}
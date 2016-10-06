package ua.partner.suzuki.service.impl;

import ua.partner.suzuki.dao.WarehouseDao;
import ua.partner.suzuki.domain.obm.OBM;
import ua.partner.suzuki.service.WarehouseService;

public class WarehouseServiceImpl extends AbstractService<OBM> implements WarehouseService {

	private WarehouseDao obmDao;
	
	@Override
	protected Class<WarehouseServiceImpl> getEntityClass() {
		return WarehouseServiceImpl.class;
	}

	@Override
	protected WarehouseDao getDaoEntity() {
		return obmDao;
	}
}

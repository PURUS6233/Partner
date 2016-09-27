package ua.partner.suzuki.services.impl;

import ua.partner.suzuki.dao.EngineNumberDao;
import ua.partner.suzuki.dao.impl.OBMDaoImpl;
import ua.partner.suzuki.domain.obm.OBM;
import ua.partner.suzuki.service.OBMService;

public class OBMServiceImpl extends AbstractService<OBM> implements OBMService {
	
	@SuppressWarnings("rawtypes")
	private EngineNumberDao obmDao = new OBMDaoImpl();

	@Override
	protected Class<OBMServiceImpl> getEntityClass() {
		return OBMServiceImpl.class;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected EngineNumberDao getEntity() {
		return obmDao;
	}
}

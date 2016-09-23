package ua.partner.suzuki.services.impl;

import ua.partner.suzuki.dao.OBMDao;
import ua.partner.suzuki.dao.impl.file.OBMDaoImpl;
import ua.partner.suzuki.domain.obm.OBM;
import ua.partner.suzuki.services.OBMServices;

public class OBMServicesImpl extends AbstractServices<OBM> implements OBMServices {
	
	private OBMDao obmDao = new OBMDaoImpl();

	@Override
	protected Class<OBMServicesImpl> getEntityClass() {
		return OBMServicesImpl.class;
	}

	@Override
	protected OBMDao getEntity() {
		return obmDao;
	}
}

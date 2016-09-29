package ua.partner.suzuki.service.impl;

import ua.partner.suzuki.dao.OBMDao;
import ua.partner.suzuki.domain.obm.OBM;
import ua.partner.suzuki.service.OBMService;

public class OBMServiceImpl extends AbstractService<OBM> implements OBMService {

	private OBMDao obmDao;
	
	public OBMServiceImpl(OBMDao obmDao){
		this.obmDao = obmDao;
	}
	
	@Override
	protected Class<OBMServiceImpl> getEntityClass() {
		return OBMServiceImpl.class;
	}

	@Override
	protected OBMDao getDaoEntity() {
		return obmDao;
	}
}

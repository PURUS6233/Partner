package ua.partner.suzuki.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.dao.RegistrationDao;
import ua.partner.suzuki.dao.postgres.PostgreRegistrationDao;
import ua.partner.suzuki.domain.obm.Registration;
import ua.partner.suzuki.service.OBMWarehouseException;
import ua.partner.suzuki.service.RegistrationService;
import ua.partner.suzuki.service.ServiceException;
import ua.partner.suzuki.service.WarehouseService;

public class RegistrationServiceImpl extends AbstractService<Registration>
		implements RegistrationService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	private RegistrationDao obmDao = new PostgreRegistrationDao();
	private WarehouseService warehouseService = new WarehouseServiceImpl();

	@Override
	protected Class<RegistrationServiceImpl> getEntityClass() {
		return RegistrationServiceImpl.class;
	}

	@Override
	protected RegistrationDao getDaoEntity() {
		return obmDao;
	}
	
	@Override
	public Registration add(Registration registration) throws ServiceException {
		try {
			// Read Data from Json file to map
			getDaoEntity().init();
			// Check if the entity already exists in database
			logger.info("Check if entity is already exist", getEntityClass()
					.getSimpleName());
			Preconditions
					.checkState(!getDaoEntity().isExist(registration.getEngineNumber()));
			warehouseService.isExist(registration.getEngineNumber());
			getDaoEntity().add(registration);
			getDaoEntity().writeMapToFile();
		} catch (IllegalStateException e) {
			logger.error("Entity with this engine number already exists!", e);
			throw new ServiceException("Can not add entity to map.", e);
		} catch (DAOException e) {
			logger.error("Problems occured while writing entity to json!", e);
			throw new ServiceException("Can not add entity to file.", e);
		} catch (OBMWarehouseException e) {
			logger.error("You can not add registration as the OBM is not add to Warehouse!", e);
			throw new ServiceException("You can not add registration as the OBM is not add to Warehouse!", e);
		}
		return registration;
	}
}

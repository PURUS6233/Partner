package ua.partner.suzuki.service.impl;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.dao.OBMDao;
import ua.partner.suzuki.dao.postgres.PostgreOBMDao;
import ua.partner.suzuki.domain.DomainException;
import ua.partner.suzuki.domain.obm.EngineNoLoaderException;
import ua.partner.suzuki.domain.obm.OBM;
import ua.partner.suzuki.domain.obm.OBMBuilder;
import ua.partner.suzuki.service.OBMWarehouseException;
import ua.partner.suzuki.service.ServiceException;
import ua.partner.suzuki.service.WarehouseService;

public class WarehouseServiceImpl extends AbstractService<OBM> implements
		WarehouseService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private OBMDao obmDao = new PostgreOBMDao();

	@Override
	protected Class<WarehouseServiceImpl> getEntityClass() {
		return WarehouseServiceImpl.class;
	}

	@Override
	protected OBMDao getDaoEntity() {
		return obmDao;
	}

	public Collection<OBM> add(String engineNumbers) throws ServiceException {
		Collection<OBM> listOBM;
		try {
			OBMBuilder builder = new OBMBuilder(engineNumbers);
			listOBM = builder.getObms();
			for (OBM obm : listOBM) {
				// Read Data from Json file to map
				getDaoEntity().init();
				// Check if the entity already exists in database
				logger.info("Check if entity is already exist",
						getEntityClass().getSimpleName());
				Preconditions.checkState(!getDaoEntity().isExist(
						obm.getEngineNumber()));
				getDaoEntity().add(obm);
				getDaoEntity().writeMapToFile();
			}
		} catch (IllegalStateException e) {
			logger.error("Entity with this engine number already exists!", e);
			throw new ServiceException("Can not add entity to map.", e);
		} catch (DAOException e) {
			logger.error("Problems occured while writing entity to json!", e);
			throw new ServiceException("Can not add entity to file.", e);
		} catch (EngineNoLoaderException e) {
			logger.error("Error occured while loading engine numbers!", e);
			throw new ServiceException(
					"Can not load engine numbers from stream.", e);
		} catch (DomainException e) {
			logger.error("Problem occured during OBM building!", e);
			throw new ServiceException("Can not create OBM entity.", e);
		}
		return listOBM;
	}

	public boolean isExist(String engineNumber) throws OBMWarehouseException,
			ServiceException {
		try {
			getDaoEntity().init();
			Preconditions.checkState(getDaoEntity().isExist(engineNumber));
		} catch (IllegalStateException e) {
			logger.error("You are trying to register OBM that is not loaded to warehouse.", e);
			throw new OBMWarehouseException(
					"You are trying to register OBM that is not loaded to warehouse.",
					e);
		} catch (DAOException e) {
			logger.error("Can not read json file", e);
			throw new ServiceException("Can not retrieve entity", e);
		}
		return true;
	}
}

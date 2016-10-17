package ua.partner.suzuki.service.impl;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.dao.WarehouseDao;
import ua.partner.suzuki.dao.impl.WarehouseDaoImpl;
import ua.partner.suzuki.domain.DomainException;
import ua.partner.suzuki.domain.obm.EngineNoLoaderException;
import ua.partner.suzuki.domain.obm.OBM;
import ua.partner.suzuki.domain.obm.OBMBuilder;
import ua.partner.suzuki.service.EngineNumbersLoaderService;
import ua.partner.suzuki.service.ServiceException;
import ua.partner.suzuki.service.WarehouseService;

public class WarehouseServiceImpl extends AbstractService<OBM> implements
		WarehouseService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private WarehouseDao obmDao = new WarehouseDaoImpl();

	@Override
	protected Class<WarehouseServiceImpl> getEntityClass() {
		return WarehouseServiceImpl.class;
	}

	@Override
	protected WarehouseDao getDaoEntity() {
		return obmDao;
	}
//TODO
//	public OBM add(String engineNumber) throws ServiceException {
//
//		OBMBuilder builder = new OBMBuilder();
//
//		OBM response;
//		try {
//			// Read Data from Json file to map
//			getDaoEntity().init();
//			// Check if the entity already exists in database
//			logger.info("Check if entity is already exist", getEntityClass()
//					.getSimpleName());
//			Preconditions.checkState(!getDaoEntity().find(engineNumber));
//			OBM obm = builder.createOBMFromEngineNumber(engineNumber);
//			response = getDaoEntity().add(obm);
//			getDaoEntity().writeMapToFile();
//		} catch (IllegalStateException e) {
//			logger.error("Entity with this engine number already exists!", e);
//			throw new ServiceException("Can not add entity to map.", e);
//		} catch (DAOException e) {
//			logger.error("Problems occured while writing entity to json!", e);
//			throw new ServiceException("Can not add entity to file.", e);
//		} catch (DomainException e) {
//			logger.error("Problem occured during OBM building!", e);
//			throw new ServiceException("Can not create OBM entity.", e);
//		}
//		return response;
//	}

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
}

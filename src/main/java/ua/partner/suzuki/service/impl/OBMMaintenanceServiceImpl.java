package ua.partner.suzuki.service.impl;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.dao.MaintenanceDao;
import ua.partner.suzuki.dao.postgres.PostgreMaintenanceDao;
import ua.partner.suzuki.domain.obm.Maintenance;
import ua.partner.suzuki.service.OBMMaintenanceService;
import ua.partner.suzuki.service.ServiceException;

public class OBMMaintenanceServiceImpl implements OBMMaintenanceService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private MaintenanceDao maintenanceDao = new PostgreMaintenanceDao();

	protected Class<OBMMaintenanceServiceImpl> getEntityClass() {
		return OBMMaintenanceServiceImpl.class;
	}

	protected MaintenanceDao getDaoEntity() {
		return maintenanceDao;
	}

	@Override
	public Maintenance add(String engineNumber, Maintenance entity)
			throws ServiceException {
		try {
			// Read Data from Json file to map
			getDaoEntity().init();
			getDaoEntity().add(engineNumber, entity);
			getDaoEntity().writeMapToFile();
		} catch (DAOException e) {
			logger.error("Problems occured while adding Maintenance to json!",
					e);
			throw new ServiceException("Can not add maintenance to file.", e);
		}
		return entity;
	}

	@Override
	public Maintenance get(String engineNumber, Date maintenanceDate)
			throws ServiceException {
		Maintenance response;
		try {
			getDaoEntity().init();
			logger.info("Check if maintenance exist", getEntityClass()
					.getSimpleName());
			Preconditions.checkState(getDaoEntity().isExist(engineNumber));
			response = getDaoEntity().get(engineNumber, maintenanceDate);
		} catch (IllegalStateException e) {
			logger.error("Can not find maintenance for this OBM!"
					+ engineNumber + maintenanceDate, e);
			throw new ServiceException("Can not retrieve maintenance.", e);
		} catch (DAOException e) {
			logger.error("Can not read json file", e);
			throw new ServiceException("Can not retrieve maintenance", e);
		}
		return response;
	}

	@Override
	public Map<Date, Maintenance> getAll(String engineNumber)
			throws ServiceException {
		try {
			getDaoEntity().init();
			logger.info("Check if maintenance history exist", getEntityClass()
					.getSimpleName());
			Preconditions.checkState(getDaoEntity().isExist(engineNumber));
			return getDaoEntity().getAll(engineNumber);
		} catch (IllegalStateException e) {
			logger.error("Can not find maintenance for this OBM!"
					+ engineNumber, e);
			throw new ServiceException(
					"Can not retrieve maintenance history for OBM. "
							+ engineNumber, e);
		} catch (DAOException e) {
			logger.error("Can not read maintenance history from database", e);
			throw new ServiceException("Can not read maintenance history from database", e);
		}
	}

	@Override
	public Maintenance update(String engineNumber, Maintenance entity)
			throws ServiceException {
		Maintenance response;
		try {
			getDaoEntity().init();
			logger.info("Entity of class '{}' updating to Map",
					getEntityClass().getSimpleName());
			response = getDaoEntity().update(engineNumber, entity);
			logger.info("Writing data to json '{}'", getEntityClass()
					.getSimpleName());
			getDaoEntity().writeMapToFile();
		} catch (DAOException e) {
			logger.error("Problem occured during updating maintenance"
					+ getEntityClass().getSimpleName(), e);
			throw new ServiceException(
					"Problem occured during updating maintenance", e);
		}
		return response;
	}

	@Override
	public Maintenance remove(String engineNumber, Date maintenanceDate)
			throws ServiceException {
		Maintenance response;
		try {
			getDaoEntity().init();
			logger.info("Entity of class '{}' updating to Map",
					getEntityClass().getSimpleName());
			response = getDaoEntity().get(engineNumber, maintenanceDate);
			getDaoEntity().delete(engineNumber, maintenanceDate);
			getDaoEntity().writeMapToFile();
		} catch (DAOException e) {
			logger.error("Problem occured during maintenance deleating"
					+ getEntityClass().getSimpleName(), e);
			throw new ServiceException(
					"Problem occured during maintenance deleating", e);
		}
		return response;
	}

}

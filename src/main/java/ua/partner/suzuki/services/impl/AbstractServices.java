package ua.partner.suzuki.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.dao.EngineNumberDao;
import ua.partner.suzuki.domain.AbstractIntEngineNumberEntity;
import ua.partner.suzuki.services.ServiceException;

public abstract class AbstractServices<T extends AbstractIntEngineNumberEntity> {

	private Logger logger = LoggerFactory.getLogger(getEntityClass());

	@SuppressWarnings("unchecked")
	public void add(T entity) throws ServiceException {
		try {
			// Read Data from Json file to map
			getEntity().init();
			// Check if the entity is already exists in database
			logger.info("Check if entity is already exist", getEntityClass()
					.getSimpleName());
			Preconditions.checkState(!getEntity()
					.find(entity.getEngineNumber()));
			getEntity().add(entity);
		} catch (IllegalStateException e) {
			logger.error("Entity with this engine number already exists!", e);
			throw new ServiceException("Can not add entity to map.", e);
		} catch (DAOException e) {
			logger.error("Problems occured while writing entity to json!", e);
			throw new ServiceException("Can not add entity to file.", e);
		}
	}

	@SuppressWarnings("unchecked")
	public T retrieve(String engineNumber) throws ServiceException {
		try {
			getEntity().init();
			Preconditions.checkState(!getEntity().find(engineNumber));
		} catch (IllegalStateException e) {
			logger.error("Entity with this engine number already exists!", e);
			throw new ServiceException("Can not retrieve entity.", e);
		} catch (DAOException e) {
			logger.error("Can not read json file", e);
			throw new ServiceException("Can not retrieve entity", e);
		}
		return (T) getEntity().get(engineNumber);
	}

	@SuppressWarnings("unchecked")
	public List<T> retrieveAll() throws ServiceException {
		try {
			getEntity().init();
			return (List<T>) getEntity().getAll();
		} catch (DAOException e) {
			logger.error("Can not read entities from database", e);
			throw new ServiceException("Can not read entities from database", e);
		}

	}

	@SuppressWarnings("unchecked")
	public void update(String engineNumber, T entity) throws ServiceException {
		try {
			getEntity().init();
			logger.info("Entity of class '{}' updating to Map",
					getEntityClass().getSimpleName());
			getEntity().update(engineNumber, entity);
			logger.info("Writing data to json '{}'", getEntityClass()
					.getSimpleName());
			getEntity().writeMapToJson();
		} catch (DAOException e) {
			logger.error("Problem occured during updating entitye"
					+ getEntityClass().getSimpleName(), e);
			throw new ServiceException(
					"Problem occured during updating entity", e);
		}
	}

	public void delete(String engineNumber) throws ServiceException {
		try {
			getEntity().init();
			logger.info("Entity of class '{}' updating to Map",
					getEntityClass().getSimpleName());
			getEntity().delete(engineNumber);
			getEntity().writeMapToJson();
		} catch (DAOException e) {
			logger.error("Problem occured during entity deleating"
					+ getEntityClass().getSimpleName(), e);
			throw new ServiceException(
					"Problem occured during entity deleating", e);
		}
	}

	@SuppressWarnings("rawtypes")
	protected abstract Class<? extends AbstractServices> getEntityClass();

	@SuppressWarnings("rawtypes")
	protected abstract EngineNumberDao getEntity();
}

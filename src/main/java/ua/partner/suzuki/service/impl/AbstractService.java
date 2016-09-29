package ua.partner.suzuki.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.dao.EngineNumberDao;
import ua.partner.suzuki.domain.AbstractIntEngineNumberEntity;
import ua.partner.suzuki.service.ServiceException;

public abstract class AbstractService<T extends AbstractIntEngineNumberEntity> {

	private Logger logger = LoggerFactory.getLogger(getEntityClass());
	
	@SuppressWarnings("unchecked")
	public T add(T entity) throws ServiceException {
		try {
			// Read Data from Json file to map
			getDaoEntity().init();
			// Check if the entity already exists in database
			logger.info("Check if entity is already exist", getEntityClass()
					.getSimpleName());
			Preconditions.checkState(!getDaoEntity()
					.find(entity.getEngineNumber()));
			getDaoEntity().add(entity);
		} catch (IllegalStateException e) {
			logger.error("Entity with this engine number already exists!", e);
			throw new ServiceException("Can not add entity to map.", e);
		} catch (DAOException e) {
			logger.error("Problems occured while writing entity to json!", e);
			throw new ServiceException("Can not add entity to file.", e);
		}
		return entity;
	}

	@SuppressWarnings("unchecked")
	public T get(String engineNumber) throws ServiceException {
		try {
			getDaoEntity().init();
			Preconditions.checkState(!getDaoEntity().find(engineNumber));
		} catch (IllegalStateException e) {
			logger.error("Entity with this engine number already exists!", e);
			throw new ServiceException("Can not retrieve entity.", e);
		} catch (DAOException e) {
			logger.error("Can not read json file", e);
			throw new ServiceException("Can not retrieve entity", e);
		}
		return (T) getDaoEntity().get(engineNumber);
	}

	@SuppressWarnings("unchecked")
	public List<T> getAll() throws ServiceException {
		try {
			getDaoEntity().init();
			return (List<T>) getDaoEntity().getAll();
		} catch (DAOException e) {
			logger.error("Can not read entities from database", e);
			throw new ServiceException("Can not read entities from database", e);
		}

	}

	@SuppressWarnings("unchecked")
	public T update(String engineNumber, T entity) throws ServiceException {
		try {
			getDaoEntity().init();
			logger.info("Entity of class '{}' updating to Map",
					getEntityClass().getSimpleName());
			getDaoEntity().update(engineNumber, entity);
			logger.info("Writing data to json '{}'", getEntityClass()
					.getSimpleName());
			getDaoEntity().writeMapToJson();
		} catch (DAOException e) {
			logger.error("Problem occured during updating entitye"
					+ getEntityClass().getSimpleName(), e);
			throw new ServiceException(
					"Problem occured during updating entity", e);
		}
		return entity;
	}

	@SuppressWarnings("unchecked")
	public T remove(String engineNumber) throws ServiceException {
		T entity;
		try {
			getDaoEntity().init();
			logger.info("Entity of class '{}' updating to Map",
					getEntityClass().getSimpleName());
			Preconditions.checkState(!getDaoEntity().find(engineNumber));
			entity = (T) getDaoEntity().get(engineNumber);
			getDaoEntity().delete(engineNumber);
			getDaoEntity().writeMapToJson();
		} catch (DAOException e) {
			logger.error("Problem occured during entity deleating"
					+ getEntityClass().getSimpleName(), e);
			throw new ServiceException(
					"Problem occured during entity deleating", e);
		}
		return entity;
	}

	@SuppressWarnings("rawtypes")
	protected abstract Class<? extends AbstractService> getEntityClass();
	
	@SuppressWarnings("rawtypes")
	protected abstract EngineNumberDao getDaoEntity();
}

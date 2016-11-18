package ua.partner.suzuki.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.dao.EngineNumberDao;
import ua.partner.suzuki.domain.EngineNumberIdentified;
import ua.partner.suzuki.service.ServiceException;

public abstract class AbstractService<T extends EngineNumberIdentified> {

	private Logger logger = LoggerFactory.getLogger(getEntityClass());

	@SuppressWarnings("unchecked")
	public T get(String engineNumber) throws ServiceException {
		T response;
		try {
			getDaoEntity().init();
			Preconditions.checkState(getDaoEntity().isExist(engineNumber));
			response = (T) getDaoEntity().get(engineNumber);
			;
		} catch (IllegalStateException e) {
			logger.error("Entity with this engine number already exists!", e);
			throw new ServiceException("Can not retrieve entity.", e);
		} catch (DAOException e) {
			logger.error("Can not read json file", e);
			throw new ServiceException("Can not retrieve entity", e);
		}
		return response;
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
	public T update(T entity) throws ServiceException {
		T response;
		try {
			getDaoEntity().init();
			logger.info("Entity of class '{}' updating to Map",
					getEntityClass().getSimpleName());
			response = (T) getDaoEntity().update(entity.getEngineNumber(), entity);
			logger.info("Writing data to json '{}'", getEntityClass()
					.getSimpleName());
			getDaoEntity().writeMapToFile();
		} catch (DAOException e) {
			logger.error("Problem occured during updating entitye"
					+ getEntityClass().getSimpleName(), e);
			throw new ServiceException(
					"Problem occured during updating entity", e);
		}
		return response;
	}

	@SuppressWarnings("unchecked")
	public T remove(String engineNumber) throws ServiceException {
		T response;
		try {
			getDaoEntity().init();
			logger.info("Entity of class '{}' updating to Map",
					getEntityClass().getSimpleName());
			Preconditions.checkState(getDaoEntity().isExist(engineNumber));
			response = (T) getDaoEntity().get(engineNumber);
			getDaoEntity().delete(engineNumber);
			getDaoEntity().writeMapToFile();
		} catch (DAOException e) {
			logger.error("Problem occured during entity deleating"
					+ getEntityClass().getSimpleName(), e);
			throw new ServiceException(
					"Problem occured during entity deleating", e);
		}
		return response;
	}

	@SuppressWarnings("rawtypes")
	protected abstract Class<? extends AbstractService> getEntityClass();

	
	@SuppressWarnings("rawtypes")
	protected abstract EngineNumberDao getDaoEntity();
}

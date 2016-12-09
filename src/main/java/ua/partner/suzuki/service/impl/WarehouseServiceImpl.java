package ua.partner.suzuki.service.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.dao.DaoFactory;
import ua.partner.suzuki.dao.GenericDao;
import ua.partner.suzuki.dao.postgres.PostgreDaoFactory;
import ua.partner.suzuki.dao.postgres.PostgreOBMDao;
import ua.partner.suzuki.domain.DomainException;
import ua.partner.suzuki.domain.EngineNoLoaderException;
import ua.partner.suzuki.domain.EngineNumbersLoader;
import ua.partner.suzuki.domain.obm.OBM;
import ua.partner.suzuki.domain.obm.OBMConverter;
import ua.partner.suzuki.service.EngineNumbersLoaderService;
import ua.partner.suzuki.service.ServiceException;
import ua.partner.suzuki.service.WarehouseService;

public class WarehouseServiceImpl implements WarehouseService {

	private DaoFactory<Connection> daoFactory = new PostgreDaoFactory();
	private Logger logger = LoggerFactory.getLogger(WarehouseServiceImpl.class);

	public Collection<String> add(InputStream inputStream)
			throws ServiceException {
		Collection<String> listWrongEngineNumbers = null;
		Collection<OBM> listOBM;
		try (Connection connection = daoFactory.getConnection()) {

			EngineNumbersLoader loader = new EngineNumbersLoader(inputStream);
			listWrongEngineNumbers = loader.getEngineNumbers_wrong();

			OBMConverter converter = new OBMConverter(loader.getEngineNumbers());
			listOBM = converter.getObms();
			
			EngineNumbersLoaderService loaderService = new EngineNumbersLoaderServiceImpl();
			Preconditions.checkState(loaderService.saveToFile(inputStream)); //TODO

			GenericDao<OBM, String> dao = (PostgreOBMDao) daoFactory.getDao(
					connection, OBM.class);

			for (OBM obm : listOBM) {
				dao.add(obm);
			}
		} catch (IllegalStateException e) {
			logger.error(
					"Problem occured while saving data to engineNumbersLoader.txt!",
					e);
			throw new ServiceException(
					"Problem occured while saving data to engineNumbersLoader.txt!",
					e);
		} catch (SQLException e) {
			logger.error("Error occured while closing connection", e);
			throw new ServiceException(
					"Error occured while closing connection", e);
		} catch (DAOException e) {
			logger.error("Error while adding OBM entities!", e);
			throw new ServiceException("Error while adding OBM entities!", e);
		} catch (EngineNoLoaderException e) {
			logger.error(
					"Error occured while converting OBM from engineNumber!", e);
			throw new ServiceException(
					"Error occured while converting OBM from engineNumber!", e);
		} catch (DomainException e) {
			logger.error("Problem occured during OBM converting!", e);
			throw new ServiceException("Can not convert OBM entity.", e);
		}
		return listWrongEngineNumbers;
	}

	@Override
	public OBM get(String engineNumber) throws ServiceException {
		OBM entity = null;
		try (Connection connection = daoFactory.getConnection()) {
			GenericDao<OBM, String> dao = (PostgreOBMDao) daoFactory.getDao(
					connection, OBM.class);
			entity = dao.getByPK(engineNumber);
			Preconditions.checkState(!entity.equals(null));
		} catch (IllegalStateException e) {
			logger.error("OBM with engine number - " + engineNumber
					+ ", does not exist in Warehouse!", e);
			throw new ServiceException("OBM with engine number - "
					+ engineNumber + ", does not exist in Warehouse!", e);
		} catch (SQLException e) {
			logger.error("Error occured while closing connection", e);
			throw new ServiceException(
					"Error occured while closing connection", e);
		} catch (DAOException e) {
			logger.error(
					"Error occured while loading entity with engine number:"
							+ engineNumber, e);
			throw new ServiceException(
					"Error occured while loading entity with engine number:"
							+ engineNumber, e);
		}
		return entity;
	}

	@Override
	public List<OBM> getAll() throws ServiceException {
		try (Connection connection = daoFactory.getConnection()) {
			GenericDao<OBM, String> dao = (PostgreOBMDao) daoFactory.getDao(
					connection, OBM.class);
			return dao.getAll();
		} catch (SQLException e) {
			logger.error("Error occured while closing connection", e);
			throw new ServiceException(
					"Error occured while closing connection", e);
		} catch (DAOException e) {
			logger.error("Error occured while loading OBM entities!", e);
			throw new ServiceException(
					"Error occured while loading OBM entities!", e);
		}
	}

	@Override
	public OBM update(OBM obm) throws ServiceException {
		OBM response;
		try (Connection connection = daoFactory.getConnection()) {
			GenericDao<OBM, String> dao = (PostgreOBMDao) daoFactory.getDao(
					connection, OBM.class);
			response = dao.update(obm);
		} catch (SQLException e) {
			logger.error("Error occured while closing connection", e);
			throw new ServiceException(
					"Error occured while closing connection", e);
		} catch (DAOException e) {
			logger.error(
					"Problem occured during updating OBM entity with engine number: "
							+ obm.getEngineNumber(), e);
			throw new ServiceException(
					"Problem occured during updating OBM entity with engine number: "
							+ obm.getEngineNumber(), e);
		}
		return response;
	}

	@Override
	public boolean remove(String engineNumber) throws ServiceException {
		try (Connection connection = daoFactory.getConnection()) {
			GenericDao<OBM, String> dao = (PostgreOBMDao) daoFactory.getDao(
					connection, OBM.class);
			return dao.delete(engineNumber);
		} catch (SQLException e) {
			logger.error("Error occured while closing connection", e);
			throw new ServiceException(
					"Error occured while closing connection", e);
		} catch (DAOException e) {
			logger.error(
					"Problem occured during deleting OBM entity with engine number: "
							+ engineNumber, e);
			throw new ServiceException(
					"Problem occured during deleting OBM entity with engine number: "
							+ engineNumber, e);
		}
	}
}

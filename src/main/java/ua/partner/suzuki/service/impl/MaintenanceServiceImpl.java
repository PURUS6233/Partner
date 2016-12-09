package ua.partner.suzuki.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.dao.DaoFactory;
import ua.partner.suzuki.dao.GenericDao;
import ua.partner.suzuki.dao.postgres.PostgreDaoFactory;
import ua.partner.suzuki.dao.postgres.PostgreMaintenanceDao;
import ua.partner.suzuki.domain.obm.maintenance.Maintenance;
import ua.partner.suzuki.service.GenericService;
import ua.partner.suzuki.service.ServiceException;

public class MaintenanceServiceImpl implements GenericService<Maintenance, Integer> {

	private DaoFactory<Connection> daoFactory = new PostgreDaoFactory();
	private Logger logger = LoggerFactory.getLogger(MaintenanceServiceImpl.class);

	public Maintenance add(Maintenance maintenance) throws ServiceException {
		Maintenance entity = null;
		try (Connection connection = daoFactory.getConnection()) {
			GenericDao<Maintenance, Integer> dao = (PostgreMaintenanceDao) daoFactory
					.getDao(connection, Maintenance.class);
			entity = dao.add(maintenance);
			Preconditions.checkState(!entity.equals(null));
		} catch (IllegalStateException e) {
			logger.error("Problem occured while saving Maintenance to Database!",
					e);
			throw new ServiceException(
					"Problem occured while saving Maintenance to Database!", e);
		} catch (SQLException e) {
			logger.error("Error occured while closing connection", e);
			throw new ServiceException(
					"Error occured while closing connection", e);
		} catch (DAOException e) {
			logger.error("Error while adding Maintenance entities!", e);
			throw new ServiceException("Error while adding Maintenance entities!",
					e);
		}
		return entity;
	}

	@Override
	public Maintenance get(Integer id) throws ServiceException {
		Maintenance entity = null;
		try (Connection connection = daoFactory.getConnection()) {
			GenericDao<Maintenance, Integer> dao = (PostgreMaintenanceDao) daoFactory
					.getDao(connection, Maintenance.class);
			entity = dao.getByPK(id);
			Preconditions.checkState(!entity.equals(null));
		} catch (IllegalStateException e) {
			logger.error("Maintenance with id - " + id
					+ ", does not exist!", e);
			throw new ServiceException("Maintenance with id - "
					+ id + ", does not exist!", e);
		} catch (SQLException e) {
			logger.error("Error occured while closing connection", e);
			throw new ServiceException(
					"Error occured while closing connection", e);
		} catch (DAOException e) {
			logger.error(
					"Error occured while loading Maintenance with id:"
							+ id, e);
			throw new ServiceException(
					"Error occured while loading Maintenance with id:"
							+ id, e);
		}
		return entity;
	}

	public List<Maintenance> getAll(String engineNumber) throws ServiceException {
		try (Connection connection = daoFactory.getConnection()) {
			PostgreMaintenanceDao dao = (PostgreMaintenanceDao) daoFactory
					.getDao(connection, Maintenance.class);
			return dao.getAll(engineNumber);
		} catch (SQLException e) {
			logger.error("Error occured while closing connection", e);
			throw new ServiceException(
					"Error occured while closing connection", e);
		} catch (DAOException e) {
			logger.error("Error occured while loading Maintenance entities!", e);
			throw new ServiceException(
					"Error occured while loading Maintenance entities!", e);
		}
	}

	@Override
	public Maintenance update(Maintenance maintenance) throws ServiceException {
		Maintenance entity;
		try (Connection connection = daoFactory.getConnection()) {
			GenericDao<Maintenance, Integer> dao = (PostgreMaintenanceDao) daoFactory
					.getDao(connection, Maintenance.class);
			entity = dao.update(maintenance);
		} catch (SQLException e) {
			logger.error("Error occured while closing connection", e);
			throw new ServiceException(
					"Error occured while closing connection", e);
		} catch (DAOException e) {
			logger.error(
					"Problem occured during updating Maintenance entity with engine number: "
							+ maintenance.getEngineNumber(), e);
			throw new ServiceException(
					"Problem occured during updating Maintenance entity with engine number: "
							+ maintenance.getEngineNumber(), e);
		}
		return entity;
	}

	@Override
	public boolean remove(Integer id) throws ServiceException {
		try (Connection connection = daoFactory.getConnection()) {
			GenericDao<Maintenance, Integer> dao = (PostgreMaintenanceDao) daoFactory
					.getDao(connection, Maintenance.class);
			return dao.delete(id);
		} catch (SQLException e) {
			logger.error("Error occured while closing connection", e);
			throw new ServiceException(
					"Error occured while closing connection", e);
		} catch (DAOException e) {
			logger.error(
					"Problem occured during deleting Maintenance entity with id: "
							+ id, e);
			throw new ServiceException(
					"Problem occured during deleting Maintenance entity with id: "
							+ id, e);
		}
	}

	@Override
	public List<Maintenance> getAll() throws ServiceException {
		throw new ServiceException("This method is not supported!");
	}
}

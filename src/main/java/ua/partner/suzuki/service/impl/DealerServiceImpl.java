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
import ua.partner.suzuki.dao.postgres.PostgreDealerDao;
import ua.partner.suzuki.domain.dealer.Dealer;
import ua.partner.suzuki.service.GenericService;
import ua.partner.suzuki.service.ServiceException;

public class DealerServiceImpl implements GenericService<Dealer, String> {

	private DaoFactory<Connection> daoFactory = new PostgreDaoFactory();
	private Logger logger = LoggerFactory.getLogger(DealerServiceImpl.class);

	public Dealer add(Dealer dealer) throws ServiceException {
		Dealer entity = null;
		try (Connection connection = daoFactory.getConnection()) {
			GenericDao<Dealer, String> dao = (PostgreDealerDao) daoFactory
					.getDao(connection, Dealer.class);
			entity = dao.add(dealer);
			Preconditions.checkState(!entity.equals(null));
		} catch (IllegalStateException e) {
			logger.error("Problem occured while saving Dealer to Database!",
					e);
			throw new ServiceException(
					"Problem occured while saving Dealer to Database!", e);
		} catch (SQLException e) {
			logger.error("Error occured while closing connection", e);
			throw new ServiceException(
					"Error occured while closing connection", e);
		} catch (DAOException e) {
			logger.error("Error while adding Dealer entities!", e);
			throw new ServiceException("Error while adding Dealer entities!",
					e);
		}
		return entity;
	}

	@Override
	public Dealer get(String login) throws ServiceException {
		Dealer entity = null;
		try (Connection connection = daoFactory.getConnection()) {
			GenericDao<Dealer, String> dao = (PostgreDealerDao) daoFactory
					.getDao(connection, Dealer.class);
			entity = dao.getByPK(login);
			Preconditions.checkState(!entity.equals(null));
		} catch (IllegalStateException e) {
			logger.error("Customer with login - " + login
					+ ", does not exist!", e);
			throw new ServiceException("Customer with login - "
					+ login + ", does not exist!", e);
		} catch (SQLException e) {
			logger.error("Error occured while closing connection", e);
			throw new ServiceException(
					"Error occured while closing connection", e);
		} catch (DAOException e) {
			logger.error(
					"Error occured while loading entity with login:"
							+ login, e);
			throw new ServiceException(
					"Error occured while loading entity with login:"
							+ login, e);
		}
		return entity;
	}

	@Override
	public List<Dealer> getAll() throws ServiceException {
		try (Connection connection = daoFactory.getConnection()) {
			GenericDao<Dealer, String> dao = (PostgreDealerDao) daoFactory
					.getDao(connection, Dealer.class);
			return dao.getAll();
		} catch (SQLException e) {
			logger.error("Error occured while closing connection", e);
			throw new ServiceException(
					"Error occured while closing connection", e);
		} catch (DAOException e) {
			logger.error("Error occured while loading Dealer entities!", e);
			throw new ServiceException(
					"Error occured while loading Dealer entities!", e);
		}
	}

	@Override
	public Dealer update(Dealer dealer) throws ServiceException {
		Dealer entity;
		try (Connection connection = daoFactory.getConnection()) {
			GenericDao<Dealer, String> dao = (PostgreDealerDao) daoFactory
					.getDao(connection, Dealer.class);
			entity = dao.update(dealer);
		} catch (SQLException e) {
			logger.error("Error occured while closing connection", e);
			throw new ServiceException(
					"Error occured while closing connection", e);
		} catch (DAOException e) {
			logger.error(
					"Problem occured during updating Customer entity with login: "
							+ dealer.getLogin(), e);
			throw new ServiceException(
					"Problem occured during updating Customer entity with login: "
							+ dealer.getLogin(), e);
		}
		return entity;
	}

	@Override
	public boolean remove(String login) throws ServiceException {
		try (Connection connection = daoFactory.getConnection()) {
			GenericDao<Dealer, String> dao = (PostgreDealerDao) daoFactory
					.getDao(connection, Dealer.class);
			return dao.delete(login);
		} catch (SQLException e) {
			logger.error("Error occured while closing connection", e);
			throw new ServiceException(
					"Error occured while closing connection", e);
		} catch (DAOException e) {
			logger.error(
					"Problem occured during deleting Dealer entity with login: "
							+ login, e);
			throw new ServiceException(
					"Problem occured during deleting Dealer entity with login: "
							+ login, e);
		}
	}
}

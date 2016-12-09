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
import ua.partner.suzuki.dao.postgres.PostgreCustomerDao;
import ua.partner.suzuki.dao.postgres.PostgreDaoFactory;
import ua.partner.suzuki.domain.customer.Customer;
import ua.partner.suzuki.service.GenericService;
import ua.partner.suzuki.service.ServiceException;

public class CustomerServiceImpl implements GenericService<Customer, String> {

	private DaoFactory<Connection> daoFactory = new PostgreDaoFactory();
	private Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

	public Customer add(Customer customer) throws ServiceException {
		Customer entity = null;
		try (Connection connection = daoFactory.getConnection()) {
			GenericDao<Customer, String> dao = (PostgreCustomerDao) daoFactory
					.getDao(connection, Customer.class);
			entity = dao.add(customer);
			Preconditions.checkState(!entity.equals(null));
		} catch (IllegalStateException e) {
			logger.error("Problem occured while saving Customer to Database!",
					e);
			throw new ServiceException(
					"Problem occured while saving Customer to Database!", e);
		} catch (SQLException e) {
			logger.error("Error occured while closing connection", e);
			throw new ServiceException(
					"Error occured while closing connection", e);
		} catch (DAOException e) {
			logger.error("Error while adding Customer entities!", e);
			throw new ServiceException("Error while adding Customer entities!",
					e);
		}
		return entity;
	}

	@Override
	public Customer get(String engineNumber) throws ServiceException {
		Customer entity = null;
		try (Connection connection = daoFactory.getConnection()) {
			GenericDao<Customer, String> dao = (PostgreCustomerDao) daoFactory
					.getDao(connection, Customer.class);
			entity = dao.getByPK(engineNumber);
			Preconditions.checkState(!entity.equals(null));
		} catch (IllegalStateException e) {
			logger.error("Customer with engine number - " + engineNumber
					+ ", does not exist!", e);
			throw new ServiceException("Customer with engine number - "
					+ engineNumber + ", does not exist!", e);
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
	public List<Customer> getAll() throws ServiceException {
		try (Connection connection = daoFactory.getConnection()) {
			GenericDao<Customer, String> dao = (PostgreCustomerDao) daoFactory
					.getDao(connection, Customer.class);
			return dao.getAll();
		} catch (SQLException e) {
			logger.error("Error occured while closing connection", e);
			throw new ServiceException(
					"Error occured while closing connection", e);
		} catch (DAOException e) {
			logger.error("Error occured while loading Customer entities!", e);
			throw new ServiceException(
					"Error occured while loading Customer entities!", e);
		}
	}

	@Override
	public Customer update(Customer customer) throws ServiceException {
		Customer entity;
		try (Connection connection = daoFactory.getConnection()) {
			GenericDao<Customer, String> dao = (PostgreCustomerDao) daoFactory
					.getDao(connection, Customer.class);
			entity = dao.update(customer);
		} catch (SQLException e) {
			logger.error("Error occured while closing connection", e);
			throw new ServiceException(
					"Error occured while closing connection", e);
		} catch (DAOException e) {
			logger.error(
					"Problem occured during updating Customer entity with engine number: "
							+ customer.getEngineNumber(), e);
			throw new ServiceException(
					"Problem occured during updating Customer entity with engine number: "
							+ customer.getEngineNumber(), e);
		}
		return entity;
	}

	@Override
	public boolean remove(String id) throws ServiceException {
		try (Connection connection = daoFactory.getConnection()) {
			GenericDao<Customer, String> dao = (PostgreCustomerDao) daoFactory
					.getDao(connection, Customer.class);
			return dao.delete(id);
		} catch (SQLException e) {
			logger.error("Error occured while closing connection", e);
			throw new ServiceException(
					"Error occured while closing connection", e);
		} catch (DAOException e) {
			logger.error(
					"Problem occured during deleting Customer entity with id: "
							+ id, e);
			throw new ServiceException(
					"Problem occured during deleting Customer entity with id: "
							+ id, e);
		}
	}
}

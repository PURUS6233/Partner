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
import ua.partner.suzuki.dao.postgres.PostgreRegistrationDao;
import ua.partner.suzuki.domain.customer.Customer;
import ua.partner.suzuki.domain.obm.registration.Registration;
import ua.partner.suzuki.service.GenericService;
import ua.partner.suzuki.service.ServiceException;

public class RegistrationServiceImpl implements
		GenericService<Registration, String> {

	private DaoFactory<Connection> daoFactory = new PostgreDaoFactory();
	private Logger logger = LoggerFactory.getLogger(WarehouseServiceImpl.class);
	private GenericService<Customer,String>  customerDao = new CustomerServiceImpl();

	public Registration add(Registration registration) throws ServiceException {
		Registration entity;
		Customer customer;
		try (Connection connection = daoFactory.getConnection()) {

			GenericDao<Registration, String> dao = (PostgreRegistrationDao) daoFactory
					.getDao(connection, Registration.class);
			entity = dao.add(registration);
			customer = customerDao.add(registration.getCustomer());
			entity.setCustomer(customer);
			Preconditions.checkState(!entity.equals(null));
		} catch (IllegalStateException e) {
			logger.error(
					"Problem occured while adding registration for engine number: "
							+ registration.getEngineNumber(), e);
			throw new ServiceException(
					"Problem occured while adding registration for engine number: "
							+ registration.getEngineNumber(), e);
		} catch (SQLException e) {
			logger.error("Error occured while closing connection", e);
			throw new ServiceException(
					"Error occured while closing connection", e);
		} catch (DAOException e) {
			logger.error("Error while adding registration for engine number: "
							+ registration.getEngineNumber(), e);
			throw new ServiceException("Error while adding registration for engine number: "
							+ registration.getEngineNumber(), e);
		}
		return entity;
	}

	@Override
	public Registration get(String engineNumber) throws ServiceException {
		Registration entity = null;
		try (Connection connection = daoFactory.getConnection()) {
			GenericDao<Registration, String> dao = (PostgreRegistrationDao) daoFactory
					.getDao(connection, Registration.class);
			entity = dao.getByPK(engineNumber);
			Preconditions.checkState(!entity.equals(null));
		} catch (IllegalStateException e) {
			logger.error("Registration with engine number - " + engineNumber
					+ ", does not exist!", e);
			throw new ServiceException("Registration with engine number - "
					+ engineNumber + ", does not exist!", e);
		} catch (SQLException e) {
			logger.error("Error occured while closing connection", e);
			throw new ServiceException(
					"Error occured while closing connection", e);
		} catch (DAOException e) {
			logger.error(
					"Error occured while loading registration for engine number: "
							+ engineNumber, e);
			throw new ServiceException(
					"Error occured while loading registration for engine number: "
							+ engineNumber, e);
		}
		return entity;
	}

	@Override
	public List<Registration> getAll() throws ServiceException {
		try (Connection connection = daoFactory.getConnection()) {
			GenericDao<Registration, String> dao = (PostgreRegistrationDao) daoFactory
					.getDao(connection, Registration.class);
			return dao.getAll();
		} catch (SQLException e) {
			logger.error("Error occured while closing connection", e);
			throw new ServiceException(
					"Error occured while closing connection", e);
		} catch (DAOException e) {
			logger.error("Error occured while loading Registrations!",
					e);
			throw new ServiceException(
					"Error occured while loading Registrations!", e);
		}
	}

	@Override
	public Registration update(Registration registration) throws ServiceException {
		Registration entity;
		Customer customer;
		try (Connection connection = daoFactory.getConnection()) {
			GenericDao<Registration, String> dao = (PostgreRegistrationDao) daoFactory
					.getDao(connection, Registration.class);
			entity = dao.update(registration);
			customer = customerDao.update(registration.getCustomer());
			entity.setCustomer(customer);
		} catch (SQLException e) {
			logger.error("Error occured while closing connection", e);
			throw new ServiceException(
					"Error occured while closing connection", e);
		} catch (DAOException e) {
			logger.error(
					"Problem occured during updating Registration with engine number: "
							+ registration.getEngineNumber(), e);
			throw new ServiceException(
					"Problem occured during updating Registration with engine number: "
							+ registration.getEngineNumber(), e);
		}
		return entity;
	}

	@Override
	public boolean remove(String engineNumber) throws ServiceException {
		try (Connection connection = daoFactory.getConnection()) {
			GenericDao<Registration, String> dao = (PostgreRegistrationDao) daoFactory
					.getDao(connection, Registration.class);
			return dao.delete(engineNumber);
		} catch (SQLException e) {
			logger.error("Error occured while closing connection", e);
			throw new ServiceException(
					"Error occured while closing connection", e);
		} catch (DAOException e) {
			logger.error(
					"Problem occured during deleting Registration for engine number: "
							+ engineNumber, e);
			throw new ServiceException(
					"Problem occured during deleting Registration for engine number: "
							+ engineNumber, e);
		}
	}
}

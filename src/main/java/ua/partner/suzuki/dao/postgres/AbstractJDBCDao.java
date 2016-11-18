package ua.partner.suzuki.dao.postgres;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.dao.GenericDao;

public abstract class AbstractJDBCDao<T, PK extends Serializable> implements
		GenericDao<T, PK> {

	private Connection connection;
	private Logger log = LoggerFactory.getLogger(getEntityClass().getName());

	protected abstract Class<T> getEntityClass();

	public abstract String getCreateQuery();

	public abstract String getSelectQuery();

	public abstract String getSelectAllQuery();

	public abstract String getUpdateQuery();

	public abstract String getDeleteQuery();

	public abstract void prepareStatementForInsert(PreparedStatement statement,
			T entity) throws DAOException;

	public abstract void prepareStatementForUpdate(PreparedStatement statement,
			T entity) throws DAOException;

	public abstract void prepareStatementForDelete(PreparedStatement statement,
			T entity) throws DAOException;

	protected abstract List<T> parseResultSet(ResultSet rs) throws DAOException;

	@Override
	public T add(T entity) throws DAOException {
		log.info("Adding new entity of " + entity.getClass());
		T addInstance;
		String sql = getCreateQuery();
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			log.trace("Create PreparedStatement");
			prepareStatementForInsert(statement, entity);
			int count = statement.executeUpdate();
			if (count != 1) {
				log.debug("On add modify more then 1 record: " + count);
				throw new DAOException("On add modify more then 1 record: "
						+ count);
			}
			log.info("New entity of " + entity.getClass() + " added!");
			try (ResultSet rs = statement.getGeneratedKeys()) {
				log.trace("Create result set");
				log.info("Read instance of " + entity.getClass());
				List<T> list = parseResultSet(rs);
				if ((list == null) || (list.size() != 1)) {
					log.debug("Receive zero result from DB for"
							+ entity.getClass());
					throw new DAOException(
							"Exception on findByPK new persist data.");
				}
				log.info("Instance of " + entity.getClass() + " created!");
				addInstance = list.iterator().next();
			}
		} catch (SQLException e) {
			log.error(
					"Problems occured while adding Object" + entity.toString()
							+ " to DB. Class: " + getEntityClass(), e);
			throw new DAOException(
					"Problems occured while adding Object to DB. Class: "
							+ getEntityClass(), e);
		}
		log.info("Instance of" + entity.getClass() + " created!");
		return addInstance;
	}

	@Override
	public T getByPK(PK key) throws DAOException {
		log.info("Looking for entity " + getEntityClass().getName()
				+ " with key: " + key);
		List<T> list;
		String sql = getSelectQuery();
		sql += " = ?";
		log.trace("Create PrepareStatement");
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, (String) key);
			log.trace("Create ResultSet");
			ResultSet rs = statement.executeQuery();
			log.trace("Create entity of " + getEntityClass().getName()
					+ " to return");
			list = parseResultSet(rs);
		} catch (SQLException e) {
			log.error("Problems occured while Object with key:" + key
					+ " fetch from DB. Class: " + getEntityClass().getName(), e);
			throw new DAOException(
					"Problems occured while Object fetch from DB. Class: "
							+ getEntityClass().getName(), e);
		}
		if (list == null || list.size() == 0) {
			log.debug("Entity with key " + key + "not found. Class: "
					+ getEntityClass().getName());
			throw new DAOException("Entity with key " + key
					+ "not found. Class: " + getEntityClass().getName());
		}
		if (list.size() > 1) {
			log.debug("Received more than one record from DB with the following key"
					+ key + ". Class: " + getEntityClass().getName());
			throw new DAOException(
					"Received more than one record from DB with the following key"
							+ key + ". Class: " + getEntityClass().getName());
		}
		log.info("Returning Entity of Class: " + getEntityClass().getName()
				+ ". With key: " + key);
		return list.iterator().next();
	}

	@Override
	public List<T> getAll() throws DAOException {
		List<T> list;
		String sql = getSelectAllQuery();
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			ResultSet rs = statement.executeQuery();
			list = parseResultSet(rs);
		} catch (SQLException e) {
			log.error("Problems occured while loading objects from DB. "
					+ getEntityClass().getName(), e);
			throw new DAOException(
					"Problems occured while loading objects from DB. "
							+ getEntityClass().getName(), e);
		}
		log.info("Entitys loaded from DB");
		return list;
	}

	@Override
	public T update(T entity) throws DAOException {
		T updateInstance;
		log.info("Updating instance " + entity.getClass().getName());
		String sql = getUpdateQuery();
		log.trace("Create result set");
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			log.trace("Create prepared statement");
			prepareStatementForUpdate(statement, entity);
			int count = statement.executeUpdate();
			if (count != 1) {
				log.debug("On update modify more then 1 record: " + count);
				throw new DAOException("On update modify more then 1 record: "
						+ count);
			}
			try (ResultSet rs = statement.getGeneratedKeys()) {
				log.trace("Create result set");
				log.info("Read instance of " + entity.getClass());
				List<T> list = parseResultSet(rs);
				if ((list == null) || (list.size() != 1)) {
					log.debug("Receive zero result from DB for"
							+ entity.getClass());
					throw new DAOException(
							"Exception on findByPK new persist data.");
				}
				log.info("Instance of " + entity.getClass() + " created!");
				updateInstance = list.iterator().next();
			}
		} catch (SQLException e) {
			log.error(
					"Problems occured during updating object "
							+ entity.toString(), e);
			throw new DAOException("Problems occured during updating object "
					+ entity.toString(), e);
		}
		log.info("Entity : " + entity.toString() + getEntityClass()
				+ " updated in DB");
		return updateInstance;
	}

	@Override
	public boolean delete(T entity) throws DAOException {
		log.info("Deleting instance " + entity.getClass().getName());
		String sql = getDeleteQuery();
		log.trace("Create prepare statement");
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			prepareStatementForDelete(statement, entity);
			int count = statement.executeUpdate();
			if (count != 1) {
				log.debug("On delete modify more then 1 record: " + count);
				throw new DAOException("On delete modify more then 1 record: "
						+ count);
			}
		} catch (SQLException e) {
			log.error(
					"Problems occured during deleting object "
							+ entity.toString() + " in DB. " + getEntityClass(),
					e);
			throw new DAOException(
					"Problems occured during deleting object in DB. "
							+ getEntityClass(), e);
		}
		log.info("Entity : " + entity.toString() + getEntityClass()
				+ " deleted from DB");
		return true;
	}

	public AbstractJDBCDao(Connection connection) {
		this.connection = connection;
	}
}

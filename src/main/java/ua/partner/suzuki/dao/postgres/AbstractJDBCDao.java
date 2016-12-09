package ua.partner.suzuki.dao.postgres;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.dao.GenericDao;
import ua.partner.suzuki.database.properties.PropertiesHelper;

public abstract class AbstractJDBCDao<T, PK extends Serializable> implements
		GenericDao<T, PK> {

	private Connection connection;
	private Logger log = LoggerFactory.getLogger(getEntityClass().getName());

	private PropertiesHelper propertiesHelper = new PropertiesHelper();
	private Properties suzuki_prop = propertiesHelper
			.propertyReader(PropertiesHelper.DATABASE_PROP_FILE);
	

	public AbstractJDBCDao(Connection connection) {
		this.connection = connection;
	}
	
	public String getCreateQuery() {
		return suzuki_prop.getProperty(getQueryPropertyName() + ".create.query");
	}

	public String getSelectQuery() {
		return suzuki_prop.getProperty(getQueryPropertyName() + ".select.query");
	}

	public String getSelectAllQuery() {
		return suzuki_prop.getProperty(getQueryPropertyName() + ".select_all.query");
	}

	public String getUpdateQuery() {
		return suzuki_prop.getProperty(getQueryPropertyName() + ".update.query");
	}

	public String getDeleteQuery() {
		return suzuki_prop.getProperty(getQueryPropertyName() + ".delete.query");
	}
	
	protected abstract Class<T> getEntityClass();

	protected abstract String getQueryPropertyName();

	public abstract void prepareStatementForInsert(PreparedStatement statement,
			T entity) throws DAOException;

	public abstract void prepareStatementForUpdate(PreparedStatement statement,
			T entity) throws DAOException;

	public abstract void prepareStatementForDelete(PreparedStatement statement,
			PK key) throws DAOException;

	protected abstract List<T> parseResultSet(ResultSet rs) throws DAOException;
	
	protected abstract List<T> parseResultSetGet(ResultSet rs) throws DAOException;

	@Override
	public T add(T entity) throws DAOException {
		log.info("Adding new entity of " + entity.getClass());
		T addInstance;
		String sql = getCreateQuery();
		try (PreparedStatement statement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
			log.trace("Create PreparedStatement");
			prepareStatementForInsert(statement, entity);
			int count = statement.executeUpdate();
			if (count != 1) {
				log.debug("On add modify more then 1 record: " + count);
				throw new DAOException("On add modify more then 1 record: "
						+ count);
			}
			log.info("New entity of " + entity.getClass() + " added!");
			try (ResultSet resultSet = statement.getGeneratedKeys()) {
				log.trace("Create result set");
				log.info("Read instance of " + entity.getClass());
				List<T> list = parseResultSet(resultSet);
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
		log.trace("Create PrepareStatement");
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setObject(1, (PK) key);
			log.trace("Create ResultSet");
			ResultSet rs = statement.executeQuery();
			log.trace("Create entity of " + getEntityClass().getName()
					+ " to return");
			list = parseResultSetGet(rs);
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
			list = parseResultSetGet(rs);
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
		try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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
	public boolean delete(PK key) throws DAOException {
		log.info("Deleting instance " + getEntityClass().getName());
		String sql = getDeleteQuery();
		log.trace("Create prepare statement");
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			prepareStatementForDelete(statement, key);
			int count = statement.executeUpdate();
			if (count != 1) {
				log.debug("On delete modify more then 1 record: " + count);
				throw new DAOException("On delete modify more then 1 record: "
						+ count);
			}
		} catch (SQLException e) {
			log.error(
					"Problems occured during deleting object "
							+ getEntityClass().getName() + " in DB. ",
					e);
			throw new DAOException(
					"Problems occured during deleting object in DB. "
							+ getEntityClass(), e);
		}
		log.info("Entity : " + getEntityClass().toString() + getEntityClass()
				+ " deleted from DB");
		return true;
	}
}

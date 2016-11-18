package ua.partner.suzuki.dao.postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.dao.DaoFactory;
import ua.partner.suzuki.dao.GenericDao;
import ua.partner.suzuki.domain.obm.OBM;

public class PostgreDaoFactory implements DaoFactory<Connection> {

	private Logger log = LoggerFactory.getLogger(PostgreDaoFactory.class.getName());
	private static final String USER = "postgres";
	private static final String PASSWORD = "root";
	private static final String URL = "jdbc:postgresql://localhost:5432/suzukidb";
	private static final String DRIVER = "org.postgresql.Driver";
	private Map<Class, DaoCreator<Connection>> creators;

	public Connection getConnection() throws DAOException {
		Connection connection = null;
		try {
			log.trace("Open connection");
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			log.error(
					"Error during connecting to DB", e);
			throw new DAOException(
					"Error during connecting to DB", e);
		}
		return connection;
	}

	@Override
	public GenericDao getDao(Connection connection, Class dtoClass)
			throws DAOException {
		log.info("Getting DAO entity " + dtoClass.getName());
		DaoCreator<Connection> creator = creators.get(dtoClass);
		if (creator == null) {
			log.error("Dao object for " + dtoClass.getName() + " not found.");
			throw new DAOException("Dao object for " + dtoClass.getName() + " not found.");
		}
		log.info("DAO for entity" + dtoClass.getName() + " get from factory!");
		return creator.create(connection);
	}

	public PostgreDaoFactory() {
		log.info("Filling DaoFactory Map with DAO entities.");
		try {
			log.trace("Driver registration");
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			log.error("Driver can not be found found.", e);
		}
		creators = new HashMap<Class, DaoCreator<Connection>>();
		log.trace("Creating DAO Entities!");
		creators.put(OBM.class, new DaoCreator<Connection>() {
			@Override
			public GenericDao create(Connection connection) {
				return new PostgreOBMDao(connection);
			}
		});
		log.info("DAO Entities was filled to the Map!");
	}
}

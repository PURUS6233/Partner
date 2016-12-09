package ua.partner.suzuki.dao.postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.dao.DaoFactory;
import ua.partner.suzuki.dao.GenericDao;
import ua.partner.suzuki.database.properties.PropertiesHelper;
import ua.partner.suzuki.domain.customer.Customer;
import ua.partner.suzuki.domain.dealer.Dealer;
import ua.partner.suzuki.domain.obm.OBM;
import ua.partner.suzuki.domain.obm.campaign.Campaign;
import ua.partner.suzuki.domain.obm.maintenance.Maintenance;
import ua.partner.suzuki.domain.obm.registration.Registration;

public class PostgreDaoFactory implements DaoFactory<Connection> {

	private Logger log = LoggerFactory.getLogger(PostgreDaoFactory.class
			.getName());

	private static final PropertiesHelper propertiesReader = new PropertiesHelper();
	private Properties suzuki_prop = propertiesReader
			.propertyReader(PropertiesHelper.DATABASE_PROP_FILE);

	private Map<Class, DaoCreator<Connection>> creators;

	public Connection getConnection() throws DAOException {
		Connection connection = null;
		try {
			String URL = suzuki_prop.getProperty("jdbc.url");
			Properties info = new Properties();
			info.put("user", suzuki_prop.getProperty("jdbc.user"));
			info.put("password", suzuki_prop.getProperty("jdbc.password"));
			log.trace("Open connection");
			connection = DriverManager.getConnection(URL, info);
		} catch (SQLException e) {
			log.error("Error during connecting to DB", e);
			throw new DAOException("Error during connecting to DB", e);
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
			throw new DAOException("Dao object for " + dtoClass.getName()
					+ " not found.");
		}
		log.info("DAO for entity" + dtoClass.getName() + " get from factory!");
		return creator.create(connection);
	}

	public PostgreDaoFactory() {
		log.info("Filling DaoFactory Map with DAO entities.");
		try {
			log.trace("Driver registration");
			Class.forName(suzuki_prop.getProperty("jdbc.driverClassName"));
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
		creators.put(Customer.class, new DaoCreator<Connection>() {
			@Override
			public GenericDao create(Connection connection) {
				return new PostgreCustomerDao(connection);
			}
		});
		creators.put(Dealer.class, new DaoCreator<Connection>() {
			@Override
			public GenericDao create(Connection connection) {
				return new PostgreDealerDao(connection);
			}
		});
		creators.put(Registration.class, new DaoCreator<Connection>() {
			@Override
			public GenericDao create(Connection connection) {
				return new PostgreRegistrationDao(connection);
			}
		});
		creators.put(Maintenance.class, new DaoCreator<Connection>() {
			@Override
			public GenericDao create(Connection connection) {
				return new PostgreMaintenanceDao(connection);
			}
		});
		creators.put(Campaign.class, new DaoCreator<Connection>() {
			@Override
			public GenericDao create(Connection connection) {
				return new PostgreCampaignDao(connection);
			}
		});
	}
}

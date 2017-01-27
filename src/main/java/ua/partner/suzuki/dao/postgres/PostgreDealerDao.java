package ua.partner.suzuki.dao.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.domain.adress.Address;
import ua.partner.suzuki.domain.dealer.Dealer;

public class PostgreDealerDao extends AbstractJDBCDao<Dealer, String> {

	private Logger log = LoggerFactory.getLogger(getEntityClass());
	
	@Override
	public void prepareStatementForInsert(PreparedStatement statement,
			Dealer dealer) throws DAOException {
		log.info("Create prepare statement for Insert");
		try {
			Address address = dealer.getAddress();
			statement.setString(1, dealer.getLogin());
			statement.setString(2, dealer.getName());
			statement.setString(3, dealer.getPassword());
			statement.setString(4, address.getStreet());
			statement.setString(5, address.getCity());
			statement.setString(6, address.getDistrict());
			statement.setString(7, address.getCountry());
			statement.setString(8, address.getPostCode());
			statement.setString(9, address.getPhone());
			statement.setString(10, address.getEmail());
		} catch (SQLException e) {
			log.error(
					"Problems occured while creating PreparedStatement for Insert"
							+ getEntityClass(), e);
			throw new DAOException(
					"Problems occured while creating PreparedStatement for Insert"
							+ getEntityClass(), e);
		}
		log.info("Prepare statement for Insert created");
	}

	@Override
	public void prepareStatementForUpdate(PreparedStatement statement,
			Dealer dealer) throws DAOException {
		log.info("Create prepare statement for Update");
		try {
			Address address = dealer.getAddress();
			statement.setString(1, dealer.getName());
			statement.setString(2, dealer.getPassword());
			statement.setString(3, address.getStreet());
			statement.setString(4, address.getCity());
			statement.setString(5, address.getDistrict());
			statement.setString(6, address.getCountry());
			statement.setString(7, address.getPostCode());
			statement.setString(8, address.getPhone());
			statement.setString(9, address.getEmail());
			statement.setString(10, dealer.getLogin());
		} catch (SQLException e) {
			log.error(
					"Problems occured while creating PreparedStatement for Update"
							+ getEntityClass(), e);
			throw new DAOException(
					"Problems occured while creating PreparedStatement for Update"
							+ getEntityClass(), e);
		}
	}

	@Override
	public void prepareStatementForDelete(PreparedStatement statement,
			String login) throws DAOException {
		log.info("Create prepare statement for Delete");
		try {
			statement.setString(1, login);
		} catch (SQLException e) {
			log.error(
					"Problems occured while creating PreparedStatement for Delate"
							+ getEntityClass(), e);
			throw new DAOException(
					"Problems occured while creating PreparedStatement for Delete"
							+ getEntityClass(), e);
		}

	}

	@Override
	protected List<Dealer> parseResultSet(ResultSet rs)
			throws DAOException {
		List<Dealer> result = new LinkedList<Dealer>();
		log.info("Reading data from ResultSet");
		try {
			log.info("Create Dealer instance");
			while (rs.next()) {
				Dealer dealer = new Dealer();
				Address address = new Address();
				dealer.setLogin(rs.getString("login"));
				dealer.setName(rs.getString("name"));
				dealer.setPassword(rs.getString("password"));
				address.setStreet(rs.getString("street"));
				address.setCity(rs.getString("city"));
				address.setDistrict(rs.getString("district"));
				address.setCountry(rs.getString("country"));
				address.setPostCode(rs.getString("post_code"));
				address.setPhone(rs.getString("phone"));
				address.setEmail(rs.getString("email"));
				dealer.setAddress(address);
				result.add(dealer);
			}
		} catch (SQLException e) {
			log.error("Problems occured while parsing ResultSet"
					+ getEntityClass(), e);
			throw new DAOException("Problems occured while parsing ResultSet"
					+ getEntityClass(), e);
		}
		log.info("Dealer instance created");
		return result;
	}
	
	@Override
	protected List<Dealer> parseResultSetGet(ResultSet rs)
			throws DAOException {
		return parseResultSet(rs);
	}

	@Override
	protected Class<Dealer> getEntityClass() {
		return Dealer.class;
	}

	public PostgreDealerDao(Connection connection) {
		super(connection);
	}

	@Override
	protected String getQueryPropertyName() {
		return "dealer";
	}
}

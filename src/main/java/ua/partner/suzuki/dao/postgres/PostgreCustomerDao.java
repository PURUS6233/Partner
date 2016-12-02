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
import ua.partner.suzuki.domain.customer.Customer;
import ua.partner.suzuki.domain.customer.CustomerType;
import ua.partner.suzuki.domain.customer.GenderType;

public class PostgreCustomerDao extends AbstractJDBCDao<Customer, String> {

	private Logger log = LoggerFactory.getLogger(getEntityClass());

	@Override
	public String getCreateQuery() {
		return "INSERT INTO suzuki.customers (id, engine_number, name, surname, sex,"
				+ " customer_type, street, city, district, country, post_code, phone, email)"
				+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?);";
	}

	@Override
	public String getSelectQuery() {
		return "SELECT * FROM suzuki.customers WHERE id =";
	}

	@Override
	public String getSelectAllQuery() {
		return "SELECT * FROM suzuki.customers";
	}

	@Override
	public String getUpdateQuery() {
		return "UPDATE suzuki.customers SET engine_number = ?, name = ?, surname = ?,"
				+ " sex = ?, customer_type = ?, street = ?, city = ?, district = ?,"
				+ " country = ?, post_code = ?, phone = ?, email = ?" 
				+ " WHERE id = ?;";
	}

	@Override
	public String getDeleteQuery() {
		return "DELETE FROM suzuki.customers WHERE id = ?;";
	}

	@Override
	public void prepareStatementForInsert(PreparedStatement statement,
			Customer customer) throws DAOException {
		log.info("Create prepare statement for Insert");
		try {
			Address address = customer.getAddress();
			statement.setString(1, customer.getId());
			statement.setString(2, customer.getEngineNumber());
			statement.setString(3, customer.getName());
			statement.setString(4, customer.getSurname());
			statement.setString(5, customer.getGender().toString());
			statement.setString(6, customer.getCustomerType().toString());
			statement.setString(7, address.getStreet());
			statement.setString(8, address.getCity());
			statement.setString(9, address.getDistrict());
			statement.setString(10, address.getCountry());
			statement.setString(11, address.getPostCode());
			statement.setString(12, address.getPhone());
			statement.setString(13, address.getEmail());
		} catch (SQLException e) {
			log.error(
					"Problems occured while creating PreparedStatement for Insert"
							+ getEntityClass(), e);
			throw new DAOException(
					"Problems occured while creating PreparedStatement for Insert"
							+ getEntityClass(), e);
		}
	}

	@Override
	public void prepareStatementForUpdate(PreparedStatement statement,
			Customer customer) throws DAOException {
		log.info("Create prepare statement for Update");
		try {
			Address address = customer.getAddress();
			statement.setString(1, customer.getEngineNumber());
			statement.setString(2, customer.getName());
			statement.setString(3, customer.getSurname());
			statement.setString(4, customer.getGender().toString());
			statement.setString(5, customer.getCustomerType().toString());
			statement.setString(6, address.getStreet());
			statement.setString(7, address.getCity());
			statement.setString(8, address.getDistrict());
			statement.setString(9, address.getCountry());
			statement.setString(10, address.getPostCode());
			statement.setString(11, address.getPhone());
			statement.setString(12, address.getEmail());
			statement.setString(13, customer.getId());
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
			Customer customer) throws DAOException {
		log.info("Create prepare statement for Delete");
		try {
			statement.setString(1, customer.getId());
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
	protected List<Customer> parseResultSet(ResultSet rs) throws DAOException {
		List<Customer> result = new LinkedList<Customer>();
		log.info("Reading data from ResultSet");
		try {
			log.info("Create Registration instance");
			while (rs.next()) {
				Customer customer = new Customer();
				Address address = new Address();
				customer.setId(rs.getString("id"));
				customer.setEngineNumber(rs.getString("engine_number"));
				customer.setName(rs.getString("name"));
				customer.setSurname(rs.getString("surname"));
				customer.setGender(GenderType.valueOf(rs.getString("sex")));
				customer.setCustomerType(CustomerType.valueOf(rs.getString("customer_type")));
				address.setStreet(rs.getString("street"));
				address.setCity(rs.getString("city"));
				address.setDistrict(rs.getString("district"));
				address.setCountry(rs.getString("country"));
				address.setPostCode(rs.getString("post_code"));
				address.setPhone(rs.getString("phone"));
				address.setEmail(rs.getString("email"));
				customer.setAddress(address);
				result.add(customer);
			}
		} catch (SQLException e) {
			log.error("Problems occured while parsing ResultSet"
					+ getEntityClass(), e);
			throw new DAOException("Problems occured while parsing ResultSet"
					+ getEntityClass(), e);
		}
		log.info("Registration instance created");
		return result;
	}

	@Override
	protected Class<Customer> getEntityClass() {
		return Customer.class;
	}

	public PostgreCustomerDao(Connection connection) {
		super(connection);
	}
}

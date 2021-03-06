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
import ua.partner.suzuki.domain.obm.registration.Registration;
import ua.partner.suzuki.domain.obm.registration.UnitSurvey;
import ua.partner.suzuki.domain.obm.registration.WarrantyType;

public class PostgreRegistrationDao extends
		AbstractJDBCDao<Registration, String> {

	private Logger log = LoggerFactory.getLogger(getEntityClass());

	@Override
	public void prepareStatementForInsert(PreparedStatement statement,
			Registration entity) throws DAOException {
		log.info("Create prepare statement for Insert");
		try {
			statement.setString(1, entity.getEngineNumber());
			statement.setString(2, entity.getWarrantyType().toString());
			statement.setString(3, entity.getDealerLogin());
			statement.setDate(4, convert(entity.getDateSold()));
			statement.setDate(5, convert(entity.getDateRegistered()));
			statement.setDate(6, convert(entity.getDateDelivered()));
			statement.setDate(7, convert(entity.getWarrantyExpiration()));
			statement.setInt(8, entity.getPenalty());
			statement.setString(9, entity.getUnitSurvey().toString());
			statement.setBoolean(10, entity.isUnitOperation());
			statement.setBoolean(11, entity.isUnitMaintenance());
			statement.setBoolean(12, entity.isSafetyFeatures());
			statement.setBoolean(13, entity.isWarrantyPolicy());
			statement.setBoolean(14, entity.isOwnersSignature());
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
			Registration entity) throws DAOException {
		log.info("Create prepare statement for Update");
		try {
			statement.setString(1, entity.getWarrantyType().toString());
			statement.setString(2, entity.getDealerLogin());
			statement.setDate(3, convert(entity.getDateSold()));
			statement.setDate(4, convert(entity.getDateRegistered()));
			statement.setDate(5, convert(entity.getDateDelivered()));
			statement.setDate(6, convert(entity.getWarrantyExpiration()));
			statement.setInt(7, entity.getPenalty());
			statement.setString(8, entity.getUnitSurvey().toString());
			statement.setBoolean(9, entity.isUnitOperation());
			statement.setBoolean(10, entity.isUnitMaintenance());
			statement.setBoolean(11, entity.isSafetyFeatures());
			statement.setBoolean(12, entity.isWarrantyPolicy());
			statement.setBoolean(13, entity.isOwnersSignature());
			statement.setString(14, entity.getEngineNumber());
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
			String engineNumber) throws DAOException {
		log.info("Create prepare statement for Delete");
		try {
			statement.setString(1, engineNumber);
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
	protected List<Registration> parseResultSet(ResultSet rs)
			throws DAOException {
		List<Registration> result = new LinkedList<Registration>();
		log.info("Reading data from ResultSet");
		try {
			log.info("Create Registration instance");
			while (rs.next()) {
				Registration registration = new Registration();
				registration.setEngineNumber(rs.getString("engine_number"));
				registration.setWarrantyType(WarrantyType.valueOf(rs
						.getString("warranty_type")));
				registration.setDealerLogin(rs.getString("dealer_login"));
				registration.setDateSold(rs.getDate("date_sold"));
				registration.setDateRegistered(rs.getDate("date_registered"));
				registration.setDateDelivered(rs.getDate("date_delivered"));
				registration.setWarrantyExpiration(rs
						.getDate("warranty_expiration"));
				registration.setPenalty(rs.getInt("penalty"));
				registration.setUnitSurvey(UnitSurvey.valueOf(rs
						.getString("unit_survey")));
				registration.setUnitOperation(rs.getBoolean("unit_operation"));
				registration.setUnitMaintenance(rs
						.getBoolean("unit_maintenance"));
				registration
						.setSafetyFeatures(rs.getBoolean("safety_features"));
				registration
						.setWarrantyPolicy(rs.getBoolean("warranty_policy"));
				registration.setOwnersSignature(rs
						.getBoolean("owners_signature"));
				result.add(registration);
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
	protected List<Registration> parseResultSetGet(ResultSet rs)
			throws DAOException {
		List<Registration> result = new LinkedList<Registration>();
		log.info("Reading data from ResultSet");
		try {
			log.info("Create Registration instance");
			while (rs.next()) {
				Registration registration = new Registration();
				Customer customer = new Customer();
				Address address = new Address();
				registration.setEngineNumber(rs.getString("engine_number"));
				registration.setWarrantyType(WarrantyType.valueOf(rs
						.getString("warranty_type")));
				registration.setDealerLogin(rs.getString("dealer_login"));
				registration.setDateSold(rs.getDate("date_sold"));
				registration.setDateRegistered(rs.getDate("date_registered"));
				registration.setDateDelivered(rs.getDate("date_delivered"));
				registration.setWarrantyExpiration(rs
						.getDate("warranty_expiration"));
				registration.setPenalty(rs.getInt("penalty"));
				registration.setUnitSurvey(UnitSurvey.valueOf(rs
						.getString("unit_survey")));
				registration.setUnitOperation(rs.getBoolean("unit_operation"));
				registration.setUnitMaintenance(rs
						.getBoolean("unit_maintenance"));
				registration
						.setSafetyFeatures(rs.getBoolean("safety_features"));
				registration
						.setWarrantyPolicy(rs.getBoolean("warranty_policy"));
				registration.setOwnersSignature(rs
						.getBoolean("owners_signature"));
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
				registration.setCustomer(customer);
				result.add(registration);
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

	protected java.sql.Date convert(java.util.Date date) {
		if (date == null) {
			return null;
		}
		return new java.sql.Date(date.getTime());
	}

	@Override
	protected Class<Registration> getEntityClass() {
		return Registration.class;
	}

	public PostgreRegistrationDao(Connection connection) {
		super(connection);
	}

	@Override
	protected String getQueryPropertyName() {
		return "registration";
	}
}

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
import ua.partner.suzuki.domain.campaign.Campaign;
import ua.partner.suzuki.domain.campaign.CampaignStatus;
import ua.partner.suzuki.domain.campaign.CampaignType;
import ua.partner.suzuki.domain.customer.Customer;
import ua.partner.suzuki.domain.customer.CustomerType;
import ua.partner.suzuki.domain.customer.SexType;

public class PostgreCampaignDao extends AbstractJDBCDao<Campaign, String> {

	private Logger log = LoggerFactory.getLogger(getEntityClass());

	@Override
	public String getCreateQuery() {
		return "INSERT INTO suzuki.campaigns (id, engine_number, name, surname, sex,"
				+ " customer_type, street, city, district, country, post_code, phone, email)"
				+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?);";
	}

	@Override
	public String getSelectQuery() {
		return "SELECT * FROM suzuki.campaigns WHERE id =";
	}

	@Override
	public String getSelectAllQuery() {
		return "SELECT * FROM suzuki.campaigns";
	}

	@Override
	public String getUpdateQuery() {
		return "UPDATE suzuki.campaigns SET engine_number = ?, name = ?, surname = ?,"
				+ " sex = ?, customer_type = ?, street = ?, city = ?, district = ?,"
				+ " country = ?, post_code = ?, phone = ?, email = ?" 
				+ " WHERE id = ?;";
	}

	@Override
	public String getDeleteQuery() {
		return "DELETE FROM suzuki.campaigns WHERE id = ?;";
	}

	@Override
	public void prepareStatementForInsert(PreparedStatement statement,
			Customer customer) throws DAOException {
		log.info("Create prepare statement for Insert");
		try {
			Address address = customer.getAdress();
			statement.setString(1, customer.getId());
			statement.setString(2, customer.getEngineNumber());
			statement.setString(3, customer.getName());
			statement.setString(4, customer.getSurname());
			statement.setString(5, customer.getSex().toString());
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
			Address address = customer.getAdress();
			statement.setString(1, customer.getEngineNumber());
			statement.setString(2, customer.getName());
			statement.setString(3, customer.getSurname());
			statement.setString(4, customer.getSex().toString());
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
			Campaign campaign) throws DAOException {
		log.info("Create prepare statement for Delete");
		try {
			statement.setInt(1, campaign.getId());
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
	protected List<Campaign> parseResultSet(ResultSet rs) throws DAOException {
		List<Campaign> result = new LinkedList<Campaign>();
		log.info("Reading data from ResultSet");
		try {
			log.info("Create Registration instance");
			while (rs.next()) {
				Campaign campaign = new Campaign();
				campaign.setId(rs.getInt("id"));
				campaign.setCampaignNo(rs.getString("campaign_number"));
				campaign.setCampaignType(CampaignType.valueOf(rs.getString("campaign_type")));
				campaign.setCampaignFile(rs.getString("campaign_file"));
				campaign.setCampaignDate(convert(rs.getDate("campaign_date")));
				campaign.setInspection(rs.getBoolean("inspection"));
				campaign.setRepair(rs.getBoolean("repair"));
				campaign.setLabourRate(rs.getString("labour_rate"));
				campaign.setCampaignStatus(CampaignStatus.valueOf(rs.getString("campaign_status")));
				result.add(campaign);
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
	protected Class<Campaign> getEntityClass() {
		return Campaign.class;
	}

	public PostgreCampaignDao(Connection connection) {
		super(connection);
	}
}

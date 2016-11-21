package ua.partner.suzuki.dao.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.partner.suzuki.dao.CampaignDao;
import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.domain.adress.Address;
import ua.partner.suzuki.domain.campaign.Campaign;
import ua.partner.suzuki.domain.campaign.CampaignStatus;
import ua.partner.suzuki.domain.campaign.CampaignType;
import ua.partner.suzuki.domain.customer.Customer;
import ua.partner.suzuki.domain.customer.CustomerType;
import ua.partner.suzuki.domain.customer.SexType;

public class PostgreCampaignDao implements CampaignDao {

	private Connection connection;
	private Logger log = LoggerFactory.getLogger(getEntityClass().getName());
	
	public String getSelectEngineNumber() {
		return "SELECT * FROM suzuki.campaigns WHERE id =";
	}
	
	public String getSelectCampaign() {
		return "SELECT * FROM suzuki.campaigns WHERE id =";
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

	public List<Campaign> getAll() throws DAOException {
		List<Campaign> list;
		String sql = getSelectAllCampaigns();
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			ResultSet rs = statement.executeQuery();
			list = parseResultSetCampaigns(rs);
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

	/*****************************************************************************************************/
	
	public PostgreCampaignDao(Connection connection) {
		this.connection = connection;
	}

	protected java.sql.Date convert(java.util.Date date) {
		if (date == null) {
			return null;
		}
		return new java.sql.Date(date.getTime());
	}

	protected Class<Campaign> getEntityClass() {
		return Campaign.class;
	}
	
	public String getSelectAllCampaigns() {
		return "SELECT * FROM suzuki.campaigns";
	}
	
	protected List<Campaign> parseResultSetCampaigns(ResultSet rs) throws DAOException {
		List<Campaign> result = new LinkedList<Campaign>();
		log.info("Reading data from ResultSet");
		try {
			log.info("Create Registration instance");
			while (rs.next()) {
				Campaign campaign = new Campaign();
				campaign.setCampaignNo(rs.getString("campaign_number"));
				campaign.setDescription(rs.getString("description"));
				campaign.setCampaignType(CampaignType.valueOf(rs
						.getString("campaign_type")));
				campaign.setCampaignFile(rs.getString("campaign_file"));
				campaign.setCampaignDate(convert(rs.getDate("campaign_date")));
				campaign.setInspection(rs.getBoolean("inspection"));
				campaign.setRepair(rs.getBoolean("repair"));
				campaign.setLabourRate(rs.getString("labour_rate"));
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
	
	public String getCreateQueryCampaigns() {
		return "INSERT INTO suzuki.campaigns (campaign_number, description, campaign_type,"
				+ " campaign_file, campaign_date, inspection, repair,labour_rate)"
				+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?);";
	}
	
	public String getCreateQueryOBMCampaign() {
		return "INSERT INTO suzuki.campaigns (campaign_number, engine_number, campaign_status)"
				+ " VALUES(?,?,?);";
	}

	public String getUpdateQuery() {
		return "UPDATE suzuki.campaigns SET description = ?, campaign_type = ?,"
				+ " campaign_file = ?, campaign_date = ?, engine_number = ?, inspection = ?, repair = ?,"
				+ " labour_rate = ?, campaign_status = ?" + " WHERE campaign_number = ?;";
	}

	public String getDeleteQuery() {
		return "DELETE FROM suzuki.campaigns WHERE id = ?;";
	}

	public void prepareStatementForUpdate(PreparedStatement statement,
			Campaign campaign) throws DAOException {
		log.info("Create prepare statement for Update");
		try {
			statement.setString(1, campaign.getDescription());
			statement.setString(2, campaign.getCampaignType().toString());
			statement.setString(3, campaign.getCampaignFile());
			statement.setDate(4, convert(campaign.getCampaignDate()));
			statement.setString(5, campaign.getEngineNumber());
			statement.setBoolean(6, campaign.isInspection());
			statement.setBoolean(7, campaign.isRepair());
			statement.setString(8, campaign.getLabourRate());
			statement.setString(9, campaign.getCampaignStatus().toString());
			statement.setString(10, campaign.getCampaignNo());
		} catch (SQLException e) {
			log.error(
					"Problems occured while creating PreparedStatement for Update"
							+ getEntityClass(), e);
			throw new DAOException(
					"Problems occured while creating PreparedStatement for Update"
							+ getEntityClass(), e);
		}
	}

	public void prepareStatementForDelete(PreparedStatement statement,
			Campaign campaign) throws DAOException {
		log.info("Create prepare statement for Delete");
		try {
			statement.setString(1, campaign.getCampaignNo());
		} catch (SQLException e) {
			log.error(
					"Problems occured while creating PreparedStatement for Delate"
							+ getEntityClass(), e);
			throw new DAOException(
					"Problems occured while creating PreparedStatement for Delete"
							+ getEntityClass(), e);
		}
	}

	protected List<Campaign> parseResultSet(ResultSet rs) throws DAOException {
		List<Campaign> result = new LinkedList<Campaign>();
		log.info("Reading data from ResultSet");
		try {
			log.info("Create Registration instance");
			while (rs.next()) {
				Campaign campaign = new Campaign();
				campaign.setCampaignNo(rs.getString("campaign_number"));
				campaign.setDescription(rs.getString("description"));
				campaign.setCampaignType(CampaignType.valueOf(rs
						.getString("campaign_type")));
				campaign.setCampaignFile(rs.getString("campaign_file"));
				campaign.setCampaignDate(convert(rs.getDate("campaign_date")));
				campaign.setEngineNumber(rs.getString("engine_number"));
				campaign.setInspection(rs.getBoolean("inspection"));
				campaign.setRepair(rs.getBoolean("repair"));
				campaign.setLabourRate(rs.getString("labour_rate"));
				campaign.setCampaignStatus(CampaignStatus.valueOf(rs
						.getString("campaign_status")));
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
	
	public void prepareStatementForInsertCampaigns(PreparedStatement statement,
			Campaign campaign) throws DAOException {
		log.info("Create prepare statement for Insert");
		try {
			statement.setString(1, campaign.getCampaignNo());
			statement.setString(2, campaign.getDescription());
			statement.setString(3, campaign.getCampaignType().toString());
			statement.setString(4, campaign.getCampaignFile());
			statement.setDate(5, convert(campaign.getCampaignDate()));
			statement.setBoolean(6, campaign.isInspection());
			statement.setBoolean(7, campaign.isRepair());
			statement.setString(8, campaign.getLabourRate());
		} catch (SQLException e) {
			log.error(
					"Problems occured while creating PreparedStatement for Insert"
							+ getEntityClass(), e);
			throw new DAOException(
					"Problems occured while creating PreparedStatement for Insert"
							+ getEntityClass(), e);
		}
	}
	
	public void prepareStatementForInsertOBMCampaign(PreparedStatement statement,
			Campaign campaign, String engineNumber) throws DAOException {
		log.info("Create prepare statement for Insert");
		try {
			statement.setString(1, campaign.getCampaignNo());
			statement.setString(2, engineNumber);
			statement.setString(3, campaign.getCampaignStatus().toString());
		} catch (SQLException e) {
			log.error(
					"Problems occured while creating PreparedStatement for Insert"
							+ getEntityClass(), e);
			throw new DAOException(
					"Problems occured while creating PreparedStatement for Insert"
							+ getEntityClass(), e);
		}
	}

	public Campaign add(Campaign campaign) throws DAOException {
		log.info("Adding new entity of " + campaign.getClass());
		Campaign addInstance;
		String sql_campaign = getCreateQueryCampaigns();
		String sql_obmCampaign = getCreateQueryOBMCampaign();
		try (PreparedStatement statement = connection.prepareStatement(sql_campaign)) {
			log.trace("Create PreparedStatement");
			prepareStatementForInsertCampaigns(statement, campaign);
			int count = statement.executeUpdate();
			if (count != 1) {
				log.debug("On add modify more then 1 record: " + count);
				throw new DAOException("On add modify more then 1 record: "
						+ count);
			}
			log.info("New entity of " + campaign.getClass() + " added!");
			try (ResultSet rs = statement.getGeneratedKeys()) {
				log.trace("Create result set");
				log.info("Read instance of " + campaign.getClass());
				List<Campaign> list = parseResultSet(rs);
				if ((list == null) || (list.size() != 1)) {
					log.debug("Receive zero result from DB for"
							+ campaign.getClass());
					throw new DAOException(
							"Exception on findByPK new persist data.");
				}
				log.info("Instance of " + campaign.getClass() + " created!");
				addInstance = list.iterator().next();
			}
		} catch (SQLException e) {
			log.error(
					"Problems occured while adding Object" + campaign.toString()
							+ " to DB. Class: " + getEntityClass(), e);
			throw new DAOException(
					"Problems occured while adding Object to DB. Class: "
							+ getEntityClass(), e);
		}
		log.info("Instance of" + campaign.getClass() + " created!");
		List<String> campaignEngineNumbers = campaign.getEngineNumberList();
		for(String engineNumber: campaignEngineNumbers){
			try (PreparedStatement statement = connection.prepareStatement(sql_obmCampaign)) {
				log.trace("Create PreparedStatement");
				prepareStatementForInsertOBMCampaign(statement, campaign, engineNumber);
				int count = statement.executeUpdate();
				if (count != 1) {
					log.debug("On add modify more then 1 record: " + count);
					throw new DAOException("On add modify more then 1 record: "
							+ count);
				}
				log.info("New entity of " + campaign.getClass() + " added!");
				try (ResultSet rs = statement.getGeneratedKeys()) {
					log.trace("Create result set");
					log.info("Read instance of " + campaign.getClass());
					List<Campaign> list = parseResultSet(rs);
					if ((list == null) || (list.size() != 1)) {
						log.debug("Receive zero result from DB for"
								+ campaign.getClass());
						throw new DAOException(
								"Exception on findByPK new persist data.");
					}
					log.info("Instance of " + campaign.getClass() + " created!");
					addInstance = list.iterator().next();
				}
			} catch (SQLException e) {
				log.error(
						"Problems occured while adding Object" + campaign.toString()
								+ " to DB. Class: " + getEntityClass(), e);
				throw new DAOException(
						"Problems occured while adding Object to DB. Class: "
								+ getEntityClass(), e);
			}
			log.info("Instance of" + campaign.getClass() + " created!");
		}
		return addInstance;
	}

	public Campaign update(Campaign entity) throws DAOException {
		Campaign updateInstance;
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
				List<Campaign> list = parseResultSet(rs);
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
	public boolean delete(Campaign entity) throws DAOException {
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
}

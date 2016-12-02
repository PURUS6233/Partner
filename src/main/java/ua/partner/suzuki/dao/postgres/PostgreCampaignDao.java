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
import ua.partner.suzuki.domain.obm.campaign.Campaign;
import ua.partner.suzuki.domain.obm.campaign.CampaignStatus;
import ua.partner.suzuki.domain.obm.campaign.CampaignType;
import ua.partner.suzuki.domain.obm.campaign.OBMCampaign;

public class PostgreCampaignDao implements CampaignDao {

	private Connection connection;
	private Logger log = LoggerFactory.getLogger(PostgreCampaignDao.class);

	public PostgreCampaignDao(Connection connection) {
		this.connection = connection;
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

	public String getSelectAllCampaigns() {
		return "SELECT * FROM suzuki.campaigns";
	}

	public String getSelectCampaignQuery() {
		return "SELECT * FROM suzuki.obmcampaigns WHERE campaign_number =";
	}

	public String getSelectEngineNumberQuery() {
		return "SELECT suzuki.campaigns.campaign_number, suzuki.campaigns.description, "
				+ "suzuki.campaigns.campaign_type, suzuki.campaigns.campaign_date, "
				+ "suzuki.obmcampaigns.engine_number, suzuki.obmcampaigns.campaign_status "
				+ "FROM suzuki.campaigns "
				+ "INNER JOINT suzuki.obmcampaigns "
				+ "ON suzuki.campaigns.campaign_number=suzuki.obmcampaigns.campaign_number "
				+ "WHERE suzuki.obmcampaigns.engine_number = ?"
				+ "ORDER BY suzuki.campaigns.campaign_date";
	}

	public String getUpdateCampaignQuery() {
		return "UPDATE suzuki.campaigns SET description = ?, campaign_type = ?,"
				+ " campaign_file = ?, campaign_date = ?, engine_number = ?, inspection = ?, repair = ?,"
				+ " labour_rate = ?" + " WHERE campaign_number = ?;";
	}

	public String getUpdateCampaignStatusQuery() {
		return "UPDATE suzuki.obmcampaigns SET campaign_status = ?"
				+ " WHERE campaign_number = ? AND engine_number = ?";
	}

	public String getDeleteQuery() {
		return "DELETE FROM suzuki.campaigns WHERE id = ?;";
	}

	public void prepareStatementForCampaignsInsert(PreparedStatement statement,
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
					"Problems occured while creating PreparedStatement for Insert",
					e);
			throw new DAOException(
					"Problems occured while creating PreparedStatement for Insert"
							+ PostgreCampaignDao.class, e);
		}
	}

	public void prepareStatementForOBMCampaignInsert(
			PreparedStatement statement, Campaign campaign, String engineNumber)
			throws DAOException {
		log.info("Create prepare statement for Insert");
		try {
			statement.setString(1, campaign.getCampaignNo());
			statement.setString(2, engineNumber);
			statement.setString(3, campaign.getCampaignStatus().toString());
		} catch (SQLException e) {
			log.error(
					"Problems occured while creating PreparedStatement for Insert", e);
			throw new DAOException(
					"Problems occured while creating PreparedStatement for Insert"
							+ PostgreCampaignDao.class, e);
		}
	}

	public void prepareStatementForCampaignUpdate(PreparedStatement statement,
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
					"Problems occured while creating PreparedStatement for Update", e);
			throw new DAOException(
					"Problems occured while creating PreparedStatement for Update"
							+ PostgreCampaignDao.class, e);
		}
	}

	public void prepareStatementForCampaignStatusUpdate(
			PreparedStatement statement, OBMCampaign obmCampaign)
			throws DAOException {
		log.info("Create prepare statement for Update");
		try {
			statement.setString(1, obmCampaign.getCampaignStatus().toString());
			statement.setString(2, obmCampaign.getCampaignNumber());
			statement.setString(3, obmCampaign.getEngineNumber());
		} catch (SQLException e) {
			log.error(
					"Problems occured while creating PreparedStatement for Update", e);
			throw new DAOException(
					"Problems occured while creating PreparedStatement for Update"
							+ PostgreCampaignDao.class, e);
		}
	}

	public void prepareStatementForDelete(PreparedStatement statement,
			Campaign campaign) throws DAOException {
		log.info("Create prepare statement for Delete");
		try {
			statement.setString(1, campaign.getCampaignNo());
		} catch (SQLException e) {
			log.error(
					"Problems occured while creating PreparedStatement for Delate", e);
			throw new DAOException(
					"Problems occured while creating PreparedStatement for Delete"
							+ PostgreCampaignDao.class, e);
		}
	}

	protected List<Campaign> parseResultSet(ResultSet rs) throws DAOException {
		List<Campaign> result = new LinkedList<Campaign>();
		log.info("Reading data from ResultSet");
		try {
			log.info("Create Registration instance");
			while (rs.next()) {
				Campaign campaign = new Campaign();
				campaign.setCampaignNumber(rs.getString("campaign_number"));
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
			log.error("Problems occured while parsing ResultSet", e);
			throw new DAOException("Problems occured while parsing ResultSet"
					+ PostgreCampaignDao.class, e);
		}
		log.info("Registration instance created");
		return result;
	}

	protected List<OBMCampaign> parseResultSetByCampaign(ResultSet rs)
			throws DAOException {
		List<OBMCampaign> result = new LinkedList<OBMCampaign>();
		log.info("Reading data from ResultSet");
		try {
			log.info("Create Registration instance");
			while (rs.next()) {
				OBMCampaign obmCampaign = new OBMCampaign();
				obmCampaign.setId(rs.getInt("id"));
				obmCampaign.setCampaignNumber(rs.getString("campaign_number"));
				obmCampaign.setEngineNumber(rs.getString("engine_number"));
				obmCampaign.setCampaignStatus(CampaignStatus.valueOf(rs
						.getString("campaign_status")));
				result.add(obmCampaign);
			}
		} catch (SQLException e) {
			log.error("Problems occured while parsing ResultSet", e);
			throw new DAOException("Problems occured while parsing ResultSet"
					+ PostgreCampaignDao.class, e);
		}
		log.info("Registration instance created");
		return result;
	}

	protected List<Campaign> parseResultSetByEngineNumber(ResultSet rs)
			throws DAOException {
		List<Campaign> result = new LinkedList<Campaign>();
		log.info("Reading data from ResultSet");
		try {
			log.info("Create Registration instance");
			while (rs.next()) {
				Campaign campaign = new Campaign();
				campaign.setCampaignNumber(rs.getString("campaign_number"));
				campaign.setDescription(rs.getString("description"));
				campaign.setCampaignType(CampaignType.valueOf(rs
						.getString("campaign_type")));
				campaign.setCampaignDate(convert(rs.getDate("campaign_date")));
				campaign.setEngineNumber(rs.getString("engine_number"));
				campaign.setCampaignStatus(CampaignStatus.valueOf(rs
						.getString("campaign_status")));
				result.add(campaign);
			}
		} catch (SQLException e) {
			log.error("Problems occured while parsing ResultSet", e);
			throw new DAOException("Problems occured while parsing ResultSet"
					+ PostgreCampaignDao.class, e);
		}
		log.info("Registration instance created");
		return result;
	}

	@Override
	public Campaign add(Campaign campaign) throws DAOException {
		log.info("Adding new entity of " + campaign.getClass());
		Campaign addInstance;
		String sql_campaign = getCreateQueryCampaigns();
		String sql_obmCampaign = getCreateQueryOBMCampaign();
		try (PreparedStatement statement = connection
				.prepareStatement(sql_campaign)) {
			log.trace("Create PreparedStatement");
			prepareStatementForCampaignsInsert(statement, campaign);
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
					"Problems occured while adding Object"
							+ campaign.toString() + " to DB. Class: ", e);
			throw new DAOException(
					"Problems occured while adding Object to DB. Class: "
							+ PostgreCampaignDao.class, e);
		}
		log.info("Instance of" + campaign.getClass() + " created!");
		List<String> campaignEngineNumbers = campaign.getEngineNumberList();
		for (String engineNumber : campaignEngineNumbers) {
			try (PreparedStatement statement = connection
					.prepareStatement(sql_obmCampaign)) {
				log.trace("Create PreparedStatement");
				prepareStatementForOBMCampaignInsert(statement, campaign,
						engineNumber);
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
						"Problems occured while adding Object"
								+ campaign.toString() + " to DB. Class: ", e);
				throw new DAOException(
						"Problems occured while adding Object to DB. Class: "
								+ PostgreCampaignDao.class, e);
			}
			log.info("Instance of" + campaign.getClass() + " created!");
		}
		return addInstance;
	}

	@Override
	public List<Campaign> getAll() throws DAOException {
		List<Campaign> list;
		String sql = getSelectAllCampaigns();
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			ResultSet rs = statement.executeQuery();
			list = parseResultSet(rs);
		} catch (SQLException e) {
			log.error("Problems occured while loading objects from DB. ", e);
			throw new DAOException(
					"Problems occured while loading objects from DB. "
							+ PostgreCampaignDao.class, e);
		}
		log.info("Entitys loaded from DB");
		return list;
	}

	@Override
	public List<OBMCampaign> getByCampaign(String campaignNumber)
			throws DAOException {
		log.info("Looking for entity " + OBMCampaign.class
				+ " with key: " + campaignNumber);
		List<OBMCampaign> list;
		String sql = getSelectCampaignQuery();
		sql += " = ?";
		log.trace("Create PrepareStatement");
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, campaignNumber);
			log.trace("Create ResultSet");
			ResultSet rs = statement.executeQuery();
			log.trace("Create entity of " + OBMCampaign.class
					+ " to return");
			list = parseResultSetByCampaign(rs);
		} catch (SQLException e) {
			log.error("Problems occured while Object with key:"
					+ campaignNumber + " fetch from DB. Class: "
					+ OBMCampaign.class, e);
			throw new DAOException(
					"Problems occured while Object fetch from DB. Class: "
							+ OBMCampaign.class, e);
		}
		if (list == null || list.size() == 0) {
			log.debug("Entity with key " + campaignNumber
					+ "not found. Class: " + OBMCampaign.class);
			throw new DAOException("Entity with key " + campaignNumber
					+ "not found. Class: " + OBMCampaign.class);
		}
		if (list.size() > 1) {
			log.debug("Received more than one record from DB with the following key"
					+ campaignNumber + ". Class: " + OBMCampaign.class);
			throw new DAOException(
					"Received more than one record from DB with the following key"
							+ campaignNumber + ". Class: "
							+ OBMCampaign.class);
		}
		log.info("Returning Entity of Class: " + OBMCampaign.class
				+ ". With key: " + campaignNumber);
		return list;
	}

	@Override
	public List<Campaign> getByEngineNumber(String engineNumber)
			throws DAOException {
		log.info("Looking for entity " + Campaign.class
				+ " with key: " + engineNumber);
		List<Campaign> list;
		String sql = getSelectCampaignQuery();
		log.trace("Create PrepareStatement");
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, engineNumber);
			log.trace("Create ResultSet");
			ResultSet rs = statement.executeQuery();
			log.trace("Create entity of " + Campaign.class
					+ " to return");
			list = parseResultSetByEngineNumber(rs);
		} catch (SQLException e) {
			log.error("Problems occured while Object with key:" + engineNumber
					+ " fetch from DB. Class: " + Campaign.class, e);
			throw new DAOException(
					"Problems occured while Object fetch from DB. Class: "
							+ Campaign.class, e);
		}
		if (list == null || list.size() == 0) {
			log.debug("Entity with key " + engineNumber + "not found. Class: "
					+ Campaign.class);
			throw new DAOException("Entity with key " + engineNumber
					+ "not found. Class: " + Campaign.class);
		}
		if (list.size() > 1) {
			log.debug("Received more than one record from DB with the following key"
					+ engineNumber + ". Class: " + Campaign.class);
			throw new DAOException(
					"Received more than one record from DB with the following key"
							+ engineNumber + ". Class: "
							+ Campaign.class);
		}
		log.info("Returning Entity of Class: " + Campaign.class
				+ ". With key: " + engineNumber);
		return list;
	}

	@Override
	public Campaign updateCampaign(Campaign entity) throws DAOException {
		Campaign updateInstance;
		log.info("Updating instance " + entity.getClass().getName());
		String sql = getUpdateCampaignQuery();
		log.trace("Create result set");
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			log.trace("Create prepared statement");
			prepareStatementForCampaignUpdate(statement, entity);
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
		log.info("Entity : " + entity.toString() + Campaign.class
				+ " updated in DB");
		return updateInstance;
	}

	@Override
	public OBMCampaign updateOBMCampaignStatus(OBMCampaign entity)
			throws DAOException {
		OBMCampaign updateInstance;
		log.info("Updating instance " + entity.getClass().getName());
		String sql = getUpdateCampaignQuery();
		log.trace("Create result set");
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			log.trace("Create prepared statement");
			prepareStatementForCampaignStatusUpdate(statement, entity);
			int count = statement.executeUpdate();
			if (count != 1) {
				log.debug("On update modify more then 1 record: " + count);
				throw new DAOException("On update modify more then 1 record: "
						+ count);
			}
			try (ResultSet rs = statement.getGeneratedKeys()) {
				log.trace("Create result set");
				log.info("Read instance of " + entity.getClass());
				List<OBMCampaign> list = parseResultSetByCampaign(rs);
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
		log.info("Entity : " + entity.toString() + Campaign.class
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
							+ entity.toString() + " in DB. " + Campaign.class,
					e);
			throw new DAOException(
					"Problems occured during deleting object in DB. "
							+ Campaign.class, e);
		}
		log.info("Entity : " + entity.toString() + Campaign.class
				+ " deleted from DB");
		return true;
	}

	protected java.sql.Date convert(java.util.Date date) {
		if (date == null) {
			return null;
		}
		return new java.sql.Date(date.getTime());
	}
}

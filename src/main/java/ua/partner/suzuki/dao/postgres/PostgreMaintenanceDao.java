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
import ua.partner.suzuki.domain.obm.maintenance.Maintenance;
import ua.partner.suzuki.domain.obm.maintenance.MaintenanceType;
import ua.partner.suzuki.domain.obm.maintenance.ServiceType;

public class PostgreMaintenanceDao extends AbstractJDBCDao<Maintenance, Integer> {

	private Logger log = LoggerFactory.getLogger(getEntityClass());
	private Connection connection;
	
	public PostgreMaintenanceDao(Connection connection) {
		super(connection);
		this.connection = connection;
	}
	
	@Override
	public void prepareStatementForInsert(PreparedStatement statement,
			Maintenance entity) throws DAOException {
		log.info("Create prepare statement for Insert");
		try {
			statement.setString(1, entity.getEngineNumber());
			statement.setDate(2, convert(entity.getExecutionDate()));
			statement.setString(3, entity.getMaintenanceType().toString());
			statement.setString(4, entity.getServiceType().toString());
			statement.setString(5, entity.getHours());
			statement.setString(6, entity.getNote());
			statement.setString(7, entity.getSDSFile());
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
			Maintenance entity) throws DAOException {
		log.info("Create prepare statement for Update");
		try {
			statement.setString(1, entity.getEngineNumber());
			statement.setDate(2, convert(entity.getExecutionDate()));
			statement.setString(3, entity.getMaintenanceType().toString());
			statement.setString(4, entity.getServiceType().toString());
			statement.setString(5, entity.getHours());
			statement.setString(6, entity.getNote());
			statement.setString(7, entity.getSDSFile());
			statement.setInt(8, entity.getId());
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
			Integer id) throws DAOException {
		log.info("Create prepare statement for Delete");
		try {
			statement.setInt(1, id);
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
	protected List<Maintenance> parseResultSet(ResultSet rs)
			throws DAOException {
		List<Maintenance> result = new LinkedList<Maintenance>();
		log.info("Reading data from ResultSet");
		try {
			log.info("Create Registration instance");
			while (rs.next()) {
				Maintenance maintenance = new Maintenance();
				maintenance.setId(rs.getInt("id"));
				maintenance.setEngineNumber(rs.getString("engine_number"));
				maintenance.setExecutionDate(rs.getDate("execution_date"));
				maintenance.setMaintenanceType(MaintenanceType
						.valueOf(rs.getString("maintenance_type")));
				maintenance.setServiceType(ServiceType.valueOf(rs.getString("service_type")));
				maintenance.setHours(rs.getString("hours"));
				maintenance.setNote(rs.getString("note"));
				maintenance.setSDSFile(rs.getString("SDS_file"));
				result.add(maintenance);
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
	protected List<Maintenance> parseResultSetGet(ResultSet rs)
			throws DAOException {
		return parseResultSet(rs);
	}
	
	public List<Maintenance> getAll(String engineNumber) throws DAOException { //TODO
		List<Maintenance> list;
		String sql = getSelectAllQuery();
		System.out.println(sql);
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, engineNumber);
			log.trace("Create ResultSet");
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

	protected java.sql.Date convert(java.util.Date date) {
		if (date == null) {
			return null;
		}
		return new java.sql.Date(date.getTime());
	}

	@Override
	protected Class<Maintenance> getEntityClass() {
		return Maintenance.class;
	}

	@Override
	protected String getQueryPropertyName() {
		return "maintenance";
	}
}

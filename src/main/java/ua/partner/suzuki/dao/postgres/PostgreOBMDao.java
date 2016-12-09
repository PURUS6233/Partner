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
import ua.partner.suzuki.domain.obm.Model;
import ua.partner.suzuki.domain.obm.OBM;
import ua.partner.suzuki.domain.obm.Status;

public class PostgreOBMDao extends AbstractJDBCDao<OBM, String> {

	private Logger log = LoggerFactory.getLogger(getEntityClass());

	@Override
	public void prepareStatementForInsert(PreparedStatement statement,
			OBM entity) throws DAOException {
		log.info("Create prepare statement for Insert");
		try {
			statement.setString(1, entity.getEngineNumber());
			statement.setString(2, entity.getModel().toString());
			statement.setString(3, entity.getModelYear());
			statement.setString(4, entity.getStatus().toString());
		} catch (SQLException e) {
			log.error("Problems occured while creating PreparedStatement for Insert"
					+ getEntityClass(), e);
			throw new DAOException(
					"Problems occured while creating PreparedStatement for Insert"
							+ getEntityClass(), e);
		}
	}

	@Override
	public void prepareStatementForUpdate(PreparedStatement statement,
			OBM entity) throws DAOException {
		log.info("Create prepare statement for Update");
		try {
			statement.setString(1, entity.getModel().toString());
			statement.setString(2, entity.getModelYear());
			statement.setString(3, entity.getStatus().toString());
			statement.setString(4, entity.getEngineNumber());
		} catch (SQLException e) {
			log.error("Problems occured while creating PreparedStatement for Update"
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
			log.error("Problems occured while creating PreparedStatement for Delate"
					+ getEntityClass(), e);
			throw new DAOException(
					"Problems occured while creating PreparedStatement for Delete"
							+ getEntityClass(), e);
		}

	}

	@Override
	protected List<OBM> parseResultSet(ResultSet rs) throws DAOException {
		List<OBM> result = new LinkedList<OBM>();
		log.info("Reading data from ResultSet");
		try {
			log.info("Create OBM instance");
			while (rs.next()) {
				OBM obm = new OBM();
				obm.setEngineNumber(rs.getString("engine_number"));
				obm.setModel(Model.valueOf(rs.getString("model")));
				obm.setModelYear(rs.getString("model_year"));
				obm.setStatus(Status.valueOf(rs.getString("status")));
				result.add(obm);
			}
		} catch (SQLException e) {
			log.error("Problems occured while parsing ResultSet"
					+ getEntityClass(), e);
			throw new DAOException("Problems occured while parsing ResultSet"
					+ getEntityClass(), e);
		}
		log.info("OBM instance created");
		return result;
	}
	
	@Override
	protected List<OBM> parseResultSetGet(ResultSet rs)
			throws DAOException {
		return parseResultSet(rs);
	}

	@Override
	protected Class<OBM> getEntityClass() {
		return OBM.class;
	}

	public PostgreOBMDao(Connection connection) {
		super(connection);
	}

	@Override
	protected String getQueryPropertyName() {
		return "obm";
	}
}
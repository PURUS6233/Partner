package ua.partner.suzuki.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import ua.partner.suzuki.dao.CampaignDao;
import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.dao.DaoFactory;
import ua.partner.suzuki.dao.GenericDao;
import ua.partner.suzuki.dao.postgres.PostgreDaoFactory;
import ua.partner.suzuki.domain.obm.campaign.Campaign;
import ua.partner.suzuki.domain.obm.campaign.OBMCampaign;
import ua.partner.suzuki.service.CampaignService;
import ua.partner.suzuki.service.ServiceException;

public class CampaignServiceImpl implements CampaignService {

	private DaoFactory<Connection> daoFactory = new PostgreDaoFactory();
	private Logger logger = LoggerFactory.getLogger(CampaignServiceImpl.class);

	public Campaign add(Campaign campaign) throws ServiceException {
		Campaign entity;
		try (Connection connection = daoFactory.getConnection()) {
			CampaignDao dao = (CampaignDao) daoFactory.getDao(connection,
					Campaign.class);
			entity = dao.add(campaign);
			Preconditions.checkState(!entity.equals(null));
		} catch (IllegalStateException e) {
			logger.error(
					"Problem occured while adding Campaign with campaign number: "
							+ campaign.getCampaignNumber(), e);
			throw new ServiceException(
					"Problem occured while adding Campaign with campaign number: "
							+ campaign.getCampaignNumber(), e);
		} catch (SQLException e) {
			logger.error("Error occured while closing connection", e);
			throw new ServiceException(
					"Error occured while closing connection", e);
		} catch (DAOException e) {
			logger.error("Error while adding Campaign with campaign number: "
					+ campaign.getCampaignNumber(), e);
			throw new ServiceException(
					"Error while adding Campaign with campaign number: "
							+ campaign.getCampaignNumber(), e);
		}
		return entity;
	}

	@Override
	public List<Campaign> getByEngineNumber(String engineNumber)
			throws ServiceException {
		List<Campaign> campaigns = null;
		try (Connection connection = daoFactory.getConnection()) {
			CampaignDao dao = (CampaignDao) daoFactory.getDao(connection,
					Campaign.class);
			campaigns = dao.getByEngineNumber(engineNumber);
			Preconditions.checkState(!campaigns.equals(null));
		} catch (IllegalStateException e) {
			logger.error("Campaign with engine number - " + engineNumber
					+ ", does not exist!", e);
			throw new ServiceException("Campaign with engine number - "
					+ engineNumber + ", does not exist!", e);
		} catch (SQLException e) {
			logger.error("Error occured while closing connection", e);
			throw new ServiceException(
					"Error occured while closing connection", e);
		} catch (DAOException e) {
			logger.error(
					"Error occured while loading campaign for engine number: "
							+ engineNumber, e);
			throw new ServiceException(
					"Error occured while loading campaign for engine number: "
							+ engineNumber, e);
		}
		return campaigns;
	}

	@Override
	public List<OBMCampaign> getByCampaign(String campaignNumber)
			throws ServiceException {
		List<OBMCampaign> obmCampaigns = null;
		try (Connection connection = daoFactory.getConnection()) {
			CampaignDao dao = (CampaignDao) daoFactory.getDao(connection,
					Campaign.class);
			obmCampaigns = dao.getByCampaign(campaignNumber);
			Preconditions.checkState(!obmCampaigns.equals(null));
		} catch (IllegalStateException e) {
			logger.error("Campaign with campaign number - " + campaignNumber
					+ ", does not exist!", e);
			throw new ServiceException("Campaign with campaign number - "
					+ campaignNumber + ", does not exist!", e);
		} catch (SQLException e) {
			logger.error("Error occured while closing connection", e);
			throw new ServiceException(
					"Error occured while closing connection", e);
		} catch (DAOException e) {
			logger.error(
					"Error occured while loading campaign with campaign number: "
							+ campaignNumber, e);
			throw new ServiceException(
					"Error occured while loading campaign with campaign number: "
							+ campaignNumber, e);
		}
		return obmCampaigns;
	}

	@Override
	public List<Campaign> getAll() throws ServiceException {
		List<Campaign> campaigns = null;
		try (Connection connection = daoFactory.getConnection()) {
			CampaignDao dao = (CampaignDao) daoFactory.getDao(connection,
					Campaign.class);
			campaigns = dao.getAll();
			Preconditions.checkState(!campaigns.equals(null));
		} catch (IllegalStateException e) {
			logger.error("Error while loading campaigns!", e);
			throw new ServiceException("Error while loading campaigns!", e);
		} catch (SQLException e) {
			logger.error("Error occured while closing connection", e);
			throw new ServiceException(
					"Error occured while closing connection", e);
		} catch (DAOException e) {
			logger.error("Error while loading campaigns!", e);
			throw new ServiceException("Error while loading campaigns!", e);
		}
		return campaigns;
	}

	@Override
	public Campaign updateCampaign(Campaign campaign) throws ServiceException {
		Campaign entity;
		try (Connection connection = daoFactory.getConnection()) {
			CampaignDao dao = (CampaignDao) daoFactory.getDao(connection,
					Campaign.class);
			entity = dao.update(campaign);
		} catch (SQLException e) {
			logger.error("Error occured while closing connection", e);
			throw new ServiceException(
					"Error occured while closing connection", e);
		} catch (DAOException e) {
			logger.error(
					"Problem occured during updating Campaign with campaign number: "
							+ campaign.getCampaignNumber(), e);
			throw new ServiceException(
					"Problem occured during updating Campaign with campaign number: "
							+ campaign.getCampaignNumber(), e);
		}
		return entity;
	}

	@Override
	public OBMCampaign updateOBMCampaignStatus(OBMCampaign obmCampaign)
			throws ServiceException {
		OBMCampaign entity;
		try (Connection connection = daoFactory.getConnection()) {
			CampaignDao dao = (CampaignDao) daoFactory.getDao(connection,
					Campaign.class);
			entity = dao.updateOBMCampaignStatus(obmCampaign);
		} catch (SQLException e) {
			logger.error("Error occured while closing connection", e);
			throw new ServiceException(
					"Error occured while closing connection", e);
		} catch (DAOException e) {
			logger.error(
					"Problem occured during updating OBMCampaign with campaign number: "
							+ obmCampaign.getCampaignNumber()
							+ ", engine number: "
							+ obmCampaign.getEngineNumber() + "! ", e);
			throw new ServiceException(
					"Problem occured during updating OBMCampaign with campaign number: "
							+ obmCampaign.getCampaignNumber()
							+ ", engine number: "
							+ obmCampaign.getEngineNumber() + "! ", e);
		}
		return entity;
	}

	@Override
	public boolean remove(String campaignNumber) throws ServiceException {
		try (Connection connection = daoFactory.getConnection()) {
			GenericDao<Campaign, String> dao = (CampaignDao) daoFactory
					.getDao(connection, Campaign.class);
			return dao.delete(campaignNumber);
		} catch (SQLException e) {
			logger.error("Error occured while closing connection", e);
			throw new ServiceException(
					"Error occured while closing connection", e);
		} catch (DAOException e) {
			logger.error(
					"Problem occured during deleting Campaign with campaign number: "
							+ campaignNumber, e);
			throw new ServiceException(
					"Problem occured during deleting Campaign with campaign number: "
							+ campaignNumber, e);
		}
	}
}

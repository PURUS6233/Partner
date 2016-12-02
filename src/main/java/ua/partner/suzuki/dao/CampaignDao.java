package ua.partner.suzuki.dao;

import java.util.List;

import ua.partner.suzuki.domain.obm.campaign.Campaign;
import ua.partner.suzuki.domain.obm.campaign.OBMCampaign;

public interface CampaignDao {

	Campaign add(Campaign entity) throws DAOException;

	List<Campaign> getByEngineNumber(String engineNumber) throws DAOException;
	
	List<OBMCampaign> getByCampaign(String id) throws DAOException;

	List<Campaign> getAll() throws DAOException;

	Campaign updateCampaign(Campaign entity) throws DAOException;
	
	OBMCampaign updateOBMCampaignStatus(OBMCampaign entity) throws DAOException;

	boolean delete(Campaign entity) throws DAOException;
}

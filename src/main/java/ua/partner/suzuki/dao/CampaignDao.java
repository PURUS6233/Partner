package ua.partner.suzuki.dao;

import java.util.List;

import ua.partner.suzuki.domain.obm.campaign.Campaign;
import ua.partner.suzuki.domain.obm.campaign.OBMCampaign;

public interface CampaignDao extends GenericDao<Campaign, String>{

	List<Campaign> getByEngineNumber(String engineNumber) throws DAOException;
	
	List<OBMCampaign> getByCampaign(String campaignNumber) throws DAOException;
	
	OBMCampaign updateOBMCampaignStatus(OBMCampaign entity) throws DAOException;
}

package ua.partner.suzuki.service;

import java.util.List;

import ua.partner.suzuki.domain.obm.campaign.Campaign;
import ua.partner.suzuki.domain.obm.campaign.OBMCampaign;

public interface CampaignService {

	Campaign add(Campaign entity) throws ServiceException;

	List<Campaign> getByEngineNumber(String engineNumber) throws ServiceException;
	
	List<OBMCampaign> getByCampaign(String campaignNumber) throws ServiceException;

	List<Campaign> getAll() throws ServiceException;

	Campaign updateCampaign(Campaign entity) throws ServiceException;
	
	OBMCampaign updateOBMCampaignStatus(OBMCampaign entity) throws ServiceException;

	boolean remove(String campaignNumber) throws ServiceException;
}

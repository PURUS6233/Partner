package ua.partner.suzuki.dao;

import java.util.List;

import ua.partner.suzuki.domain.campaign.Campaign;


public interface CampaignDao {

	Campaign add(Campaign entity) throws DAOException;

	List<Campaign> getByEngineNumber(String engineNumber) throws DAOException;
	
	List<String> getById(String id) throws DAOException;

	List<Campaign> getAll() throws DAOException;

	Campaign updateCampaign(Campaign entity) throws DAOException;

	boolean delete(Campaign entity) throws DAOException;
}

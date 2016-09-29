package ua.partner.suzuki.service;

import java.util.List;

import ua.partner.suzuki.domain.obm.OBM;

public interface OBMService {
	
	void add (OBM entity) throws ServiceException;
	
	OBM get(String engineNumber) throws ServiceException;
	
	List<OBM> getAll() throws ServiceException ;
	
	void update (String engineNumber, OBM entity) throws ServiceException ;
	
	void remove (String engineNumber) throws ServiceException ;
}
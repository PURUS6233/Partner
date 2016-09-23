package ua.partner.suzuki.services;

import java.util.List;

import ua.partner.suzuki.domain.obm.OBM;

public interface OBMServices {
	
	void add (OBM entity) throws ServiceException;
	
	OBM retrieve(String engineNumber) throws ServiceException;
	
	List<OBM> retrieveAll() throws ServiceException ;
	
	void update (String engineNumber, OBM entity) throws ServiceException ;
	
	void delete (String engineNumber) throws ServiceException ;

}

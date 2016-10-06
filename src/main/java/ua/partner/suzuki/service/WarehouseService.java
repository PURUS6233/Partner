package ua.partner.suzuki.service;

import java.util.List;

import ua.partner.suzuki.domain.obm.OBM;

public interface WarehouseService {
	
	OBM add (OBM entity) throws ServiceException;
	
	OBM get(String engineNumber) throws ServiceException;
	
	List<OBM> getAll() throws ServiceException ;
	
	OBM update (String engineNumber, OBM entity) throws ServiceException ;
	
	OBM remove (String engineNumber) throws ServiceException ;
}
package ua.partner.suzuki.service;

import java.util.Collection;
import java.util.List;

import ua.partner.suzuki.domain.obm.OBM;

public interface WarehouseService {
	
	Collection<OBM> add (String engineNumber) throws ServiceException;
	
	OBM get(String engineNumber) throws ServiceException;
	
	List<OBM> getAll() throws ServiceException ;
	
	OBM update (OBM entity) throws ServiceException ;
	
	OBM remove (String engineNumber) throws ServiceException ;
	
	boolean isExist(String engineNumber) throws OBMWarehouseException,
	ServiceException;
}
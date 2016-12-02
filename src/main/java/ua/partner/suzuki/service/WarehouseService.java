package ua.partner.suzuki.service;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import ua.partner.suzuki.domain.obm.OBM;

public interface WarehouseService {
	
	Collection<String> add (InputStream engineNumber) throws ServiceException;
	
	OBM get(String engineNumber) throws ServiceException;
	
	List<OBM> getAll() throws ServiceException ;
	
	OBM update (OBM entity) throws ServiceException ;
	
	OBM remove (String engineNumber) throws ServiceException ;
	
	boolean isExist(String engineNumber) throws OBMWarehouseException,
	ServiceException;
}
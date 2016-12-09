package ua.partner.suzuki.service;

import java.io.InputStream;
import java.util.Collection;

import ua.partner.suzuki.domain.obm.OBM;

public interface WarehouseService {

	Collection<String> add (InputStream inputStream) throws ServiceException;
	
	OBM get(String key) throws ServiceException;
	
	Collection<OBM> getAll() throws ServiceException ;
	
	OBM update (OBM entity) throws ServiceException ;
	
	boolean remove (String key) throws ServiceException ;
}

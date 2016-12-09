package ua.partner.suzuki.service;

import java.io.Serializable;
import java.util.List;

public interface GenericService<T, PK extends Serializable> {

	T add (T entity) throws ServiceException;
	
	T get(PK key) throws ServiceException;
	
	List<T> getAll() throws ServiceException ;
	
	T update (T entity) throws ServiceException ;
	
	boolean remove (PK key) throws ServiceException ;
}

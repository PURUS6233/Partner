package ua.partner.suzuki.dao;

import java.util.List;

import ua.partner.suzuki.domain.AbstractIntEngineNumberEntity;

public interface EngineNumberDao<T extends AbstractIntEngineNumberEntity> {

	boolean find (String engineNumber);
	
	T add (T entity) throws DAOException;
	
	T get(String engineNumber);
	
	List<T> getAll();
	
	T update (String engineNumber, T entity) throws DAOException;
	
	boolean delete (String engineNumber) throws DAOException;

	boolean init() throws DAOException;
	
	boolean writeMapToFile() throws DAOException;
}

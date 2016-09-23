package ua.partner.suzuki.dao;

import java.util.List;

import ua.partner.suzuki.domain.AbstractIntEngineNumberEntity;

public interface EngineNumberDao<T extends AbstractIntEngineNumberEntity> {

	boolean find (String engineNumber);
	
	void add (T entity) throws DAOException;
	
	T get(String engineNumber);
	
	List<T> getAll();
	
	void update (String engineNumber, T entity) throws DAOException;
	
	void delete (String engineNumber) throws DAOException;

	void init() throws DAOException;
	
	void writeMapToJson() throws DAOException;
}

package ua.partner.suzuki.dao;

import java.io.Serializable;
import java.util.List;


public interface GenericDao<T, PK extends Serializable> {

	T add(T entity) throws DAOException;

	T getByPK(PK key) throws DAOException;

	List<T> getAll() throws DAOException;

	T update(T entity) throws DAOException;

	boolean delete(PK key) throws DAOException;

}

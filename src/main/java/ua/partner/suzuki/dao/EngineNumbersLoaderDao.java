package ua.partner.suzuki.dao;

import java.util.Collection;

import ua.partner.suzuki.domain.obm.EngineNumbersLoader;

public interface EngineNumbersLoaderDao {

	boolean writeToFile(String engineNumbers)throws DAOException;

	Collection<String> readFromFile() throws DAOException;

	Class<EngineNumbersLoader> getEntityClass();

	String getFileName();

}

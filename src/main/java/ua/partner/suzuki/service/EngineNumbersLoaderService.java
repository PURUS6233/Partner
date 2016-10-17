package ua.partner.suzuki.service;

import java.util.Collection;

import ua.partner.suzuki.dao.DAOException;

public interface EngineNumbersLoaderService {
	
	public boolean saveToFile(String numbers) throws DAOException;
	
	public Collection<String> readFromFile() throws DAOException;

}

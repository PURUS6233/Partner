package ua.partner.suzuki.service;

import java.io.InputStream;
import java.util.Collection;

import ua.partner.suzuki.dao.DAOException;

public interface EngineNumbersLoaderService {
	
	public boolean saveToFile(InputStream inputStream) throws DAOException;
	
	public Collection<String> readFromFile() throws DAOException;

}

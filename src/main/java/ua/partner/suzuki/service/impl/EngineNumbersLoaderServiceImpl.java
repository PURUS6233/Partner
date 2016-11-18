package ua.partner.suzuki.service.impl;

import java.util.Collection;

import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.dao.EngineNumbersLoaderDao;
import ua.partner.suzuki.dao.file.EngineNumbersLoaderDaoImpl;
import ua.partner.suzuki.service.EngineNumbersLoaderService;

public class EngineNumbersLoaderServiceImpl implements
		EngineNumbersLoaderService {
	
	private EngineNumbersLoaderDao dao = new EngineNumbersLoaderDaoImpl();

	@Override
	public boolean saveToFile(String engineNumbers) throws DAOException {
		return dao.writeToFile(engineNumbers);
	}

	@Override
	public Collection<String> readFromFile() throws DAOException {
		return dao.readFromFile();
	}

}

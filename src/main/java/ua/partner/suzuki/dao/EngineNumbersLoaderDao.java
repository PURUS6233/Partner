package ua.partner.suzuki.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;

import ua.partner.suzuki.domain.EngineNumbersLoader;

public interface EngineNumbersLoaderDao {

	boolean writeToFile()throws DAOException;

	Collection<String> readFromFile() throws DAOException;

	Class<EngineNumbersLoader> getEntityClass();

	String getFileName();

}

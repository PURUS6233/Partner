package ua.partner.suzuki.dao.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.dao.EngineNumbersLoaderDao;
import ua.partner.suzuki.database.properties.PropertiesReader;
import ua.partner.suzuki.domain.obm.EngineNumbersLoader;

public class EngineNumbersLoaderDaoImpl implements EngineNumbersLoaderDao {

	private Logger logger = LoggerFactory.getLogger(getEntityClass());
	private static PropertiesReader prop = new PropertiesReader();

	@Override
	public boolean writeToFile(String engineNumbers) {
		String pathToFile = prop.getDatabaseLocation() + "/" + getFileName();
		try (FileWriter writer = new FileWriter(pathToFile);
				BufferedWriter bw = new BufferedWriter(writer)) {
			bw.write(engineNumbers);
		} catch (IOException e) {
			logger.error(
					"Error occured during saving loaded engine Numbers to file"
							+ getFileName(), e);
			;
		}
		logger.info(
				"Engine numbers loaded to the storage file" + getFileName(),
				getEntityClass().getSimpleName());
		return true;
	}
	
	private static final String WORDS_DELIMITERS_2_SKIP_REGEX = "[\\s+.!?,/]";

	@Override
	public Collection<String> readFromFile() throws DAOException {
		Collection<String> list = new ArrayList<String>();
		// Get file from resources folder
		try (Scanner s = new Scanner(new File(prop.getDatabaseLocation() + "/"
				+ getFileName()))) {
			while (s.hasNext()) {
				list.add(s.next().replaceAll(WORDS_DELIMITERS_2_SKIP_REGEX, ""));
			}
		} catch (FileNotFoundException e) {
			logger.error("Can not find" + getFileName() + "file", e);
			throw new DAOException("Can not find" + getFileName() + "file", e);
		}
		logger.info("Engine numbers list loaded from file" + getFileName()
				+ "file", getEntityClass().getSimpleName());
		return list;
	}

	@Override
	public Class<EngineNumbersLoader> getEntityClass() {
		return EngineNumbersLoader.class;
	}

	@Override
	public String getFileName() {
		return "engineNumbersLoader.txt";
	}

}

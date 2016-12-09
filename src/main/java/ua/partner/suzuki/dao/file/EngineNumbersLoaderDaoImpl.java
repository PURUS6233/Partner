package ua.partner.suzuki.dao.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.dao.EngineNumbersLoaderDao;
import ua.partner.suzuki.database.properties.PropertiesHelper;
import ua.partner.suzuki.domain.EngineNumbersLoader;

public class EngineNumbersLoaderDaoImpl implements EngineNumbersLoaderDao {

	private Logger logger = LoggerFactory.getLogger(getEntityClass());
	
	private static final PropertiesHelper propertiesHelper = new PropertiesHelper();
	private Properties suzuki_prop = propertiesHelper.propertyReader(PropertiesHelper.CONFIG_PROPERTIES_FILE);

	@Override
	public boolean writeToFile(InputStream inputStream) throws DAOException {
		String pathToFile = suzuki_prop.getProperty("loader_file.location") + "/"
				+ getFileName();
		try (OutputStream outputStream = new FileOutputStream(new File(
				pathToFile))) {
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
		} catch (IOException e) {
			logger.error(
					"Error occured during saving loaded engine Numbers to file"
							+ getFileName(), e);
			throw new DAOException("Can not find" + getFileName() + "file", e);
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
		try (Scanner s = new Scanner(new File(
				suzuki_prop.getProperty("loader_file.location") + "/"
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

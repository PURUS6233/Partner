package ua.partner.suzuki.dao.impl.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.dao.EngineNumbersLoaderDao;
import ua.partner.suzuki.domain.EngineNumbersLoader;

public class EngineNumberLoaderDaoImpl implements EngineNumbersLoaderDao {
	private Logger logger = LoggerFactory.getLogger(getEntityClass());
	Collection<String> list = Collections
			.synchronizedList(new ArrayList<String>());

	public Collection<String> getList() {
		return list;
	}

	@Override
	public boolean writeToFile() throws DAOException {
		Writer writer = null;
		ClassLoader classLoader = getClass().getClassLoader();
		try {
			try {
				writer = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(classLoader.getResource(getFileName()).getFile()),
						StandardCharsets.UTF_8.name()));
			} catch (UnsupportedEncodingException | FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (String number : getList()) {
				try {
					writer.write(number);
					((BufferedWriter) writer).newLine();
				} catch (FileNotFoundException e) {
					logger.error("The file is not found", e);
					throw new DAOException("The file is not found", e);
				} catch (IOException e) {
					logger.error("Can not write data to the file", e);
					throw new DAOException("Can not write data to the file", e);
				}
			}
		
		} finally {
			if (writer != null) {
				try {
					writer.flush();
					writer.close();
				} catch (IOException e) {
					logger.error("The file can not be closed", e);
					throw new DAOException("The file can not be closed", e);
				}
			}
		}
		logger.info("Engine numbers list loaded to the storage file", getEntityClass()
				.getSimpleName());
		return true;
	}

	@Override
	public Collection<String> readFromFile() throws DAOException {
		Collection<String> words = new ArrayList<>();
		// Get file from resources folder
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(getFileName()).getFile());

		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				words.add(line);
			}
			scanner.close();
		} catch (IOException e) {
			logger.error("Can not read data from file", e);
			throw new DAOException("Can not read data from file", e);
		}
		logger.info("Engine numbers list data upload from file", getEntityClass()
				.getSimpleName());
		return words;
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

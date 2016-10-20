package ua.partner.suzuki.dao.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.database.properties.PropertiesReader;
import ua.partner.suzuki.domain.AbstractIntEngineNumberEntity;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Maps;
import com.google.gson.Gson;

public abstract class AbstractFileDao<T extends AbstractIntEngineNumberEntity> {

	private Logger logger = LoggerFactory.getLogger(getEntityClass());
	private static final Gson gson = new Gson();
	private Map<String, T> map = new ConcurrentHashMap<String, T>();
	//private final PropertiesReader propertiesReader = new PropertiesReader();
	private Properties suzuki_prop;

	public Map<String, T> getMap() {
		return map;
	}

	public boolean init() throws DAOException {

		PropertiesReader propertiesReader = new PropertiesReader();

		BufferedReader reader;
		suzuki_prop = propertiesReader.propertyReader();
		try {
			reader = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(
									(suzuki_prop
											.getProperty("database.location")
											+ "/" + getFileName())), StandardCharsets.UTF_8));
		} catch (IOException e) {
			logger.error("Can not read json file", e);
			throw new DAOException("Can not read json file", e);
		}
		List<T> entities = gson.fromJson(reader, getListType());
		if (entities != null) {
			getMap().putAll(
					Maps.uniqueIndex(entities, new Function<T, String>() {
						public String apply(T input) {
							return input.getEngineNumber();
						}
					}));
		}
		logger.info("Entities of class '{}' loaded from json", getEntityClass()
				.getSimpleName());
		return true;
	}

	public boolean isExist(String engineNumber) {
		return getMap().containsKey(engineNumber);
	}

	public T add(T entity) throws DAOException {
		getMap().put(entity.getEngineNumber(), entity);
		return entity;
	}

	public T get(String engineNumber) {
		return getMap().get(engineNumber);
	}

	public List<T> getAll() {
		return FluentIterable.from(getMap().values()).toList();
	}

	public T update(String engineNumber, T entity) throws DAOException {
		getMap().put(engineNumber, entity);
		return entity;
	}

	public boolean delete(String engineNumber) throws DAOException {
		getMap().remove(engineNumber);
		return true;
	}

	public boolean writeMapToFile() throws DAOException {
		Collection<T> mapValues = new ArrayList<T>();
		mapValues = getMap().values();
		String pathToFile = suzuki_prop.getProperty("database.location") + "/"
				+ getFileName();
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(pathToFile), StandardCharsets.UTF_8));){
			gson.toJson(mapValues, writer);
		} catch (IOException e) {
			logger.error("Can not write map to file.", e);
			throw new DAOException("Can not write map to file.", e);
		}
		return true;
	}

	protected abstract Class<T> getEntityClass();

	protected abstract Type getListType();

	protected abstract String getFileName();
}

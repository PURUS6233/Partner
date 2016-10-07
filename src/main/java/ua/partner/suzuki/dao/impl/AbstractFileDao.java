package ua.partner.suzuki.dao.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.dao.properties.PropertiesReader;
import ua.partner.suzuki.domain.AbstractIntEngineNumberEntity;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Maps;
import com.google.gson.Gson;

public abstract class AbstractFileDao<T extends AbstractIntEngineNumberEntity> {
	private Logger logger = LoggerFactory.getLogger(getEntityClass());
	private static final Gson gson = new Gson();
	private Map<String, T> map = new ConcurrentHashMap<String, T>();
	private static PropertiesReader prop = new PropertiesReader();

	public Map<String, T> getMap() {
		return map;
	}

	public boolean init() throws DAOException {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader((prop.getDatabaseLocation() + "/"
					+ getFileName())));
		} catch (IOException e) {
			logger.error("Can not read json file", e);
			throw new DAOException("Can not read json file", e);
		}
		List<T> entities = gson.fromJson(reader, getListType());
		getMap().putAll(Maps.uniqueIndex(entities, new Function<T, String>() {
			public String apply(T input) {
				return input.getEngineNumber();
			}
		}));
		logger.info("Entities of class '{}' loaded from json", getEntityClass()
				.getSimpleName());
		return true;
	}

	public boolean find(String engineNumber) {
		return getMap().containsKey(engineNumber);
	}

	public T add(T entity) throws DAOException {
		String dataToJsonFile = gson.toJson(entity);
		String pathToFile = prop.getDatabaseLocation() + "/" + getFileName();
		try (FileWriter writer = new FileWriter(pathToFile, true)) {

			gson.toJson(dataToJsonFile, writer);

		} catch (IOException e) {
			logger.error("Can not write entity to file.", e);
			throw new DAOException("Can not write entity to file.", e);
		}
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
		String dataToJsonFile = gson.toJson(mapValues);
		String pathToFile = prop.getDatabaseLocation() + "/" + getFileName();
		try (FileWriter writer = new FileWriter(pathToFile)) {

			gson.toJson(dataToJsonFile, writer);

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

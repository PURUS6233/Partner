package ua.partner.suzuki.dao.impl;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.dao.properties.PropertiesReader;
import ua.partner.suzuki.domain.AbstractIntEngineNumberEntity;

import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import com.google.gson.Gson;

public abstract class AbstractFileDao<T extends AbstractIntEngineNumberEntity> {
	private Logger logger = LoggerFactory.getLogger(getEntityClass());
	private static final Gson gson = new Gson();
	private Map<String, T> map = new ConcurrentHashMap<String, T>();
	private PropertiesReader prop = new PropertiesReader();

	public Map<String, T> getMap() {
		return map;
	}

	public boolean init() throws DAOException {
		String json = "";
		try {
			json = Resources.toString(
					Resources.getResource(prop.getDatabaseLocation() + "/"
							+ getFileName()), Charsets.UTF_8);
		} catch (IOException e) {
			logger.error("Can not read json file", e);
			throw new DAOException("Can not read json file", e);
		}
		List<T> entities = gson.fromJson(json, getListType());
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

		try (FileWriter writer = new FileWriter(getFileName(), true)) {
			getMap().put(entity.getEngineNumber(), entity);
			String jsonObject = gson.toJson(entity);
			gson.toJson(jsonObject, writer);

		} catch (UnsupportedOperationException e) {
			logger.error(
					"Can not add entity to map. put() operation is not supported by this map",
					e);
			throw new DAOException("Can not add entity to map.", e);
		} catch (ClassCastException e) {
			logger.error(
					"Can not add entity to map. Class of the specified key or value prevents it from being stored in this map",
					e);
			throw new DAOException("Can not add entity to map.", e);
		} catch (NullPointerException e) {
			logger.error(
					"Can not add entity to map. The specified key or value is null",
					e);
			throw new DAOException("Can not add entity to map.", e);
		} catch (IllegalArgumentException e) {
			logger.error(
					"Can not add entity to map. Property of the specified key or value prevents it from being stored in this map",
					e);
			throw new DAOException("Can not add entity to map.", e);
		} catch (IOException e) {
			logger.error("Can not add jsonObject to file", e);
			throw new DAOException("Can not add jsonObject to file", e);
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
		String absolutePath = prop.getDatabaseLocation() + "/" + getFileName();
		try (FileWriter writer = new FileWriter(absolutePath, true)) {

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

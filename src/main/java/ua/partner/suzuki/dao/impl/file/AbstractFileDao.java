package ua.partner.suzuki.dao.impl.file;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.partner.suzuki.dao.DAOException;
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
 
	public Map<String, T> getMap() {
		return map;
	}

	public void init() throws DAOException {
		String json = "";
		try {
			json = Resources.toString(
					Resources.getResource("data/" + getFileName()),
					Charsets.UTF_8);
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
	}

	public boolean find(String engineNumber) {
		return getMap().containsKey(engineNumber);
	}

	public void add(T entity) throws DAOException {

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
	}

	public T get(String engineNumber) {
		return getMap().get(engineNumber);
	}

	public List<T> getAll() {
		return FluentIterable.from(getMap().values()).toList();
	}

	public void update(String engineNumber, T entity) throws DAOException {
		getMap().put(engineNumber, entity);
	}

	public void delete(String engineNumber) throws DAOException {
		getMap().remove(engineNumber);
	}

	public void writeMapToJson() throws DAOException {
		Collection<T> mapValues = new ArrayList<T>();
		mapValues = getMap().values();
		StringBuffer buffer = new StringBuffer();
		for (T t : mapValues) {
			buffer.append(gson.toJson(t));
		}
		String dataToJsonFile = buffer.toString();
		Writer writer = null;
		ClassLoader classLoader = getClass().getClassLoader();
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(classLoader.getResource(getFileName())
							.getFile()), StandardCharsets.UTF_8.name()));

			gson.toJson(dataToJsonFile, writer);

		} catch (IOException e) {
			logger.error("Can not write map to file.", e);
			throw new DAOException("Can not write map to file.", e);
		} finally {
			if (writer != null) {
				try {
					writer.flush();
					writer.close();
				} catch (IOException e) {
					logger.error("Can not close writer.", e);
					throw new DAOException("Can not close writer.", e);
				}
			}
		}
	}
	protected abstract Class<T> getEntityClass();

	protected abstract Type getListType();

	protected abstract String getFileName();
}

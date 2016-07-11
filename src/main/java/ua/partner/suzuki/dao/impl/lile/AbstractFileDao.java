package ua.partner.suzuki.dao.impl.lile;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.partner.suzuki.domain.AbstractIntEngineNumberEntity;

import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import com.google.gson.Gson;

public abstract class AbstractFileDao<T extends AbstractIntEngineNumberEntity> {
	private Logger logger;
	private static final Gson gson = new Gson();
	private Map<String, T> map = new ConcurrentHashMap<String, T>();

	
	public void init() throws IOException {
		logger = LoggerFactory.getLogger(getEntityClass());
		String json = Resources.toString(Resources.getResource("data/" + getFileName()), Charsets.UTF_8);
		List<T> entities = gson.fromJson(json, getListType());
		map.putAll(Maps.uniqueIndex(entities, new Function<T, String>() {
			public String apply(T input) {
				return input.getEngineNumber();
			}
		}));
		logger.info("Entities of class '{}' loaded from json", getEntityClass().getSimpleName());
	}
	
	public boolean find(String engineNumber){
		return map.	containsKey(engineNumber);
	}

	public T get(String engineNumber) {
		return map.get(engineNumber);
	}

	public List<T> getAll() {
		return FluentIterable.from(map.values()).toList();
	}
	
	public void delete (String engineNumber){
		map.remove(engineNumber);
	}

	protected abstract Class<T> getEntityClass();

	protected abstract Type getListType();

	protected abstract String getFileName();
}

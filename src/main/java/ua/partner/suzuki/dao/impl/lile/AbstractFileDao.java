package ua.partner.suzuki.dao.impl.lile;

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

import ua.partner.suzuki.domain.AbstractIntEngineNumberEntity;
import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class AbstractFileDao<T extends AbstractIntEngineNumberEntity> {
	private Logger logger;
	private static final Gson gson = new Gson();
	private static final Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
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
		return map.containsKey(engineNumber);
	}
	
	public void add(T entity){
		map.put(entity.getEngineNumber(), entity);
		Collection<T> mapValues = new ArrayList<T>();
		mapValues = map.values();
		StringBuffer buffer = new StringBuffer();
		for (T t: mapValues){
			buffer.append(prettyGson.toJson(t));
		} 
		String dataToJsonFile =buffer.toString();
		try (FileWriter writer = new FileWriter(getFileName())) {

            gson.toJson(dataToJsonFile, writer);

        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public T get(String engineNumber) {
		return map.get(engineNumber);
	}

	public List<T> getAll() {
		return FluentIterable.from(map.values()).toList();
	}
	
	public void put(T entity, String engineNumber){
		map.put(engineNumber, entity);
	}
	
	public void delete (String engineNumber){
		map.remove(engineNumber);
	}

	protected abstract Class<T> getEntityClass();

	protected abstract Type getListType();

	protected abstract String getFileName();
}

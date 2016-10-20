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

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.dao.DealerDao;
import ua.partner.suzuki.database.properties.PropertiesReader;
import ua.partner.suzuki.domain.dealer.Dealer;

public class DealerDaoImpl implements DealerDao {

	private Logger logger = LoggerFactory.getLogger(getEntityClass());;
	private static final Gson gson = new Gson();
	private Map<String, Dealer> map = new ConcurrentHashMap<String, Dealer>();
	private Properties suzuki_prop;

	public Map<String, Dealer> getMap() {
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
		List<Dealer> entities = gson.fromJson(reader, getListType());
		if (entities != null) {
			getMap().putAll(
					Maps.uniqueIndex(entities, new Function<Dealer, String>() {
						public String apply(Dealer dealer) {
							return dealer.getLogin();
						}
					}));
		}
		logger.info("Entities of class '{}' loaded from json", getEntityClass()
				.getSimpleName());
		return true;
	}

	public boolean isExist(String login) {
		return getMap().containsKey(login);
	}

	public Dealer add(Dealer entity) {
		getMap().put(entity.getLogin(), entity);
		return entity;
	}

	public Dealer get(String login) throws DAOException {
		return getMap().get(login);
	}

	public List<Dealer> getAll() {
		return FluentIterable.from(getMap().values()).toList();
	}

	public Dealer update(String login, Dealer entity) {
		getMap().put(login, entity);
		return entity;
	}

	public boolean delete(String login) {
		getMap().remove(login);
		return true;
	}

	public boolean writeMapToFile() throws DAOException {
		Collection<Dealer> mapValues = new ArrayList<Dealer>();
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

	protected Class<Dealer> getEntityClass() {
		return Dealer.class;
	}

	protected Type getListType() {
		return new TypeToken<List<Dealer>>() {
		}.getType();
	}

	protected String getFileName() {
		return "dealers.json";
	}
}
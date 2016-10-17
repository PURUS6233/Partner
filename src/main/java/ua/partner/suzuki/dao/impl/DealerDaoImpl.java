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

import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.dao.DealerDao;
import ua.partner.suzuki.domain.customer.Customer;
import ua.partner.suzuki.domain.dealer.Dealer;

public class DealerDaoImpl implements DealerDao{
	private Logger logger = LoggerFactory.getLogger(getEntityClass());;
	private static final Gson gson = new Gson();
	private static final Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
	private Map<String, Dealer> map = new ConcurrentHashMap<String, Dealer>();

	
	public void init() throws IOException {
		String json = Resources.toString(Resources.getResource("data/" + getFileName()), Charsets.UTF_8);
		List<Dealer> entities = gson.fromJson(json, getListType());
		map.putAll(Maps.uniqueIndex(entities, new Function<Dealer, String>() {
			public String apply(Dealer input) {
				return input.getLogin();
			}
		}));
		logger.info("Entities of class '{}' loaded from json", getEntityClass().getSimpleName());
	}
	
	public boolean find(String login){
		return map.containsKey(login);
	}
	
	public void add(Dealer entity){
		map.put(entity.getLogin(), entity);
		Collection<Dealer> mapValues = new ArrayList<Dealer>();
		mapValues = map.values();
		StringBuffer buffer = new StringBuffer();
		for (Dealer t: mapValues){
			buffer.append(prettyGson.toJson(t));
		} 
		String dataToJsonFile =buffer.toString();
		try (FileWriter writer = new FileWriter(getFileName())) {

            gson.toJson(dataToJsonFile, writer);

        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public Dealer get(String login, String password) throws DAOException{
		Dealer dealer = map.get(login);
		if(dealer == null){
			logger.error("The Dealer can not be found by login!" + login);
			throw new DAOException("The Dealer can not be found by login!" + login);
		} else if((!dealer.getPassword().equals(password))){
			logger.error("The Dealer password is not valid!");
			throw new DAOException("The Dealer password is not valid!");
		}
		return dealer;
	}

	public List<Dealer> getAll() {
		return FluentIterable.from(map.values()).toList();
	}
	
	public void update(Dealer entity, String login){
		map.put(login, entity);
	}
	
	public void delete (String login){
		map.remove(login);
	}

	@SuppressWarnings("rawtypes")
	protected Class getEntityClass() {
		return Customer.class;
	}

	protected Type getListType() {
		return new TypeToken<List<Customer>>(){}.getType();
	}

	protected String getFileName() {
		return "dealers.json";
	}
}
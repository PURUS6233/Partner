package ua.partner.suzuki.dao;

import java.util.List;

import ua.partner.suzuki.domain.OBM;

public interface OBMDao {

	boolean find (String engineNumber);
	
	void add (OBM entity);
	
	OBM get(String engineNumber);
	
	List<OBM> getAll();
	
	void put (OBM entity, String engineNumber);
	
	void delete (String engineNumber);

}

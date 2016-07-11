package ua.partner.suzuki.dao;

import java.util.List;

import ua.partner.suzuki.domain.OBM;

public interface OBMDao {
	
	void create (OBM obm);
	
	boolean find (String engineNumber);
	
	OBM get(String engineNumber);
	
	List<OBM> getAll();
	
	OBM change (OBM obm);
	
	void delete (String engineNumber);

}

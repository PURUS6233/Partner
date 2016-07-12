package ua.partner.suzuki.dao;

import java.util.List;

import ua.partner.suzuki.domain.OBMRegistration;

public interface OBMRegistrationDao {
	
	boolean find (String engineNumber);
	
	void add (OBMRegistration entity);
	
	OBMRegistration get(String engineNumber);
	
	List<OBMRegistration> getAll();
	
	void put (OBMRegistration entity, String engineNumber);
	
	void delete (String engineNumber);
	

}

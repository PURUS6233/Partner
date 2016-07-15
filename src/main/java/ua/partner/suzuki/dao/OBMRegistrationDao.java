package ua.partner.suzuki.dao;

import java.util.List;

import ua.partner.suzuki.domain.OBMRegistration;

public interface OBMRegistrationDao {
	
	boolean find (String engineNumber);
	
	void add (OBMRegistration entity) throws DAOException;
	
	OBMRegistration get(String engineNumber);
	
	List<OBMRegistration> getAll();
	
	void update (String engineNumber, OBMRegistration entity) throws DAOException;
	
	void delete (String engineNumber) throws DAOException;
	

}

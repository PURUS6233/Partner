package ua.partner.suzuki.dao;

import java.util.List;

import ua.partner.suzuki.domain.OBMRegistration;

public interface OBMRegistrationDao {
	
	OBMRegistration get(String engineNumber);
	
	List<OBMRegistration> getAll();

}

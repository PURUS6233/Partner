package ua.partner.suzuki.dao;

import java.util.List;

import ua.partner.suzuki.domain.OBMMaintenance;

public interface OBMMaintenanceDao {
	
	boolean find (String engineNumber);
	
	void add (OBMMaintenance entity);
	
	OBMMaintenance get(String engineNumber);
	
	List<OBMMaintenance> getAll();
	
	void put (OBMMaintenance entity, String engineNumber);
	
	void delete (String engineNumber);
	

}

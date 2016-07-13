package ua.partner.suzuki.dao;

import java.util.List;

import ua.partner.suzuki.domain.OBMMaintenance;

public interface OBMMaintenanceDao {
	
	boolean find (String engineNumber);
	
	void add (OBMMaintenance entity) throws DAOException;
	
	OBMMaintenance get(String engineNumber);
	
	List<OBMMaintenance> getAll();
	
	void put (String engineNumber, OBMMaintenance entity);
	
	void delete (String engineNumber);
	

}

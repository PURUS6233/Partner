package ua.partner.suzuki.dao;

import java.util.List;

import ua.partner.suzuki.domain.OBMMaintenance;

public interface OBMMaintenanceDao {
	
	OBMMaintenance get(String engineNumber);
	
	List<OBMMaintenance> getAll();

}

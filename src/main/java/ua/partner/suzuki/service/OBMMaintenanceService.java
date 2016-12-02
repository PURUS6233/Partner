package ua.partner.suzuki.service;

import java.util.Date;
import java.util.Map;

import ua.partner.suzuki.domain.obm.maintenance.Maintenance;

public interface OBMMaintenanceService {
	
	Maintenance add(String engineNumber, Maintenance entity) throws ServiceException;

	Maintenance get(String engineNumber, Date maintenanceDate) throws ServiceException;

	Map<Date, Maintenance> getAll(String engineNumber)throws ServiceException;

	Maintenance update(String engineNumber, Maintenance entity) throws ServiceException;

	Maintenance remove(String engineNumber, Date maintenanceDate) throws ServiceException;

}

package ua.partner.suzuki.dao;

import java.util.List;

import ua.partner.suzuki.domain.Customer;

public interface CustomerDao {
	
	boolean find (String engineNumber);
	
	void add (Customer entity) throws DAOException;
	
	Customer get(String engineNumber);
	
	List<Customer> getAll();
	
	void put (String engineNumber, Customer entity);
	
	void delete (String engineNumber);
}

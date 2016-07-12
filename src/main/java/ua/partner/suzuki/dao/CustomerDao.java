package ua.partner.suzuki.dao;

import java.util.List;

import ua.partner.suzuki.domain.Customer;

public interface CustomerDao {
	
	boolean find (String engineNumber);
	
	void add (Customer entity);
	
	Customer get(String engineNumber);
	
	List<Customer> getAll();
	
	void put (Customer entity, String engineNumber);
	
	void delete (String engineNumber);
}

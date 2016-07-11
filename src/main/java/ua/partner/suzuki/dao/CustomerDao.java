package ua.partner.suzuki.dao;

import java.util.List;

import ua.partner.suzuki.domain.Customer;

public interface CustomerDao {
	
	Customer get(String engineNumber);
	
	List<Customer> getAll();

}

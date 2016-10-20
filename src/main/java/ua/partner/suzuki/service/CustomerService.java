package ua.partner.suzuki.service;

import java.util.List;

import ua.partner.suzuki.domain.customer.Customer;

public interface CustomerService {
	
	Customer add (Customer entity) throws ServiceException;
	
	Customer get(String engineNumber) throws ServiceException;
	
	List<Customer> getAll() throws ServiceException ;
	
	Customer update (Customer entity) throws ServiceException ;
	
	Customer remove (String engineNumber) throws ServiceException ;

}

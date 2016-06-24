package ua.partner.suzuki.domain;

import java.util.Collection;

public class CustomersGroup {
	
	private Collection<Customer> customersGroup;
	
	public void addCustomer(Customer customer){
		customersGroup.add(customer);
	}
}
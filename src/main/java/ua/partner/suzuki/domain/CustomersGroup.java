package ua.partner.suzuki.domain;

import java.util.ArrayList;
import java.util.Collection;

public class CustomersGroup {
	
	public CustomersGroup(Customer customer){
		setCustomersGroup(customer);
	}
	
	private Collection<Customer> customers;
	
	public Collection<Customer> getCustomersGroup() {
		return customers;
	}

	public void setCustomersGroup(Customer customer) {
		this.customers = addCustomer(customer);
	}
	
	public Collection<Customer> addCustomer(Customer customer){
		Collection<Customer> customers = new ArrayList<Customer>();
		customers.add(customer);
	return customers;
	}
}
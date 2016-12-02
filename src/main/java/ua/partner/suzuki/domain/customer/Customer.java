package ua.partner.suzuki.domain.customer;

import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.partner.suzuki.domain.EngineNumberIdentifiable;
import ua.partner.suzuki.domain.Validatable;
import ua.partner.suzuki.domain.adress.Address;

import com.google.common.base.Preconditions;

@XmlRootElement
public class Customer implements EngineNumberIdentifiable<String>, Validatable {

	private String id;
	private String engineNumber;
	private String name;
	private String surname;
	private GenderType gender;
	private Address address;
	private CustomerType customerType;

	private static Logger log = LoggerFactory.getLogger(Address.class);

	public Customer() {

	}

	public Customer(String engineNumber, String name, String surname,
			GenderType gender, Address address, CustomerType customerType) {
		setEngineNumber(engineNumber);
		setName(name);
		setSurname(surname);
		setGender(gender);
		setAddress(address);
		setCustomerType(customerType);
		log.trace("Created customer with name: " + name + ", surname: "
				+ surname + ", engineNumber = " + engineNumber + ".");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
		log.trace("Set id to = " + id);
	}

	@Override
	public String getEngineNumber() {
		return engineNumber;
	}

	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
		log.trace("Set engineNumber to = " + engineNumber);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		log.trace("Set name to = " + name);
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
		log.trace("Set surname to = " + surname);
	}

	public GenderType getGender() {
		return gender;
	}

	public void setGender(GenderType gender) {
		this.gender = gender;
		log.trace("Set gender to = " + gender);
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
		log.trace("Set address to = " + address.toString());
	}

	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
		log.trace("Set customerType to = " + customerType.toString());
	}

	public boolean validate() {
		log.trace("Start validating Customer object.");
		Preconditions.checkState(
				!(getEngineNumber().isEmpty() || getEngineNumber() == null),
				"The engine number is not valid!");
		Preconditions.checkState(!(getName().isEmpty() || getName() == null),
				"The customer name is not valid!");
		Preconditions.checkState(
				!(getSurname().isEmpty() || getSurname() == null),
				"The customer surname is not valid!");
		Preconditions.checkState(!(getGender() == null),
				"The customer gender is not valid!");
		Preconditions.checkState(!(getAddress().validate()),
				"The customer address is not valid!");
		Preconditions.checkState(!(getCustomerType() == null),
				"The customer type is not valid!");
		log.trace("Customer object validated.");
		return true;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", engineNumber=" + engineNumber
				+ ", name=" + name + ", surname=" + surname + ", sex=" + gender
				+ ", adress=" + address + ", customerType=" + customerType + "]";
	}

}

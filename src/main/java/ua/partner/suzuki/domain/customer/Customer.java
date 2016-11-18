package ua.partner.suzuki.domain.customer;

import javax.xml.bind.annotation.XmlRootElement;

import ua.partner.suzuki.domain.EngineNumberIdentified;
import ua.partner.suzuki.domain.DomainException;
import ua.partner.suzuki.domain.PersonalDataValidator;
import ua.partner.suzuki.domain.adress.Address;

import com.google.common.base.Preconditions;

@XmlRootElement
public class Customer implements EngineNumberIdentified {

	private String id;
	private String engineNumber;
	private String name;
	private String surname;
	private SexType sex;
	private Address adress;
	private CustomerType customerType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getEngineNumber() {
		return engineNumber;
	}

	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public SexType getSex() {
		return sex;
	}

	public void setSex(SexType sex) {
		this.sex = sex;
	}

	public Address getAdress() {
		return adress;
	}

	public void setAdress(Address adress) {
		this.adress = adress;
	}

	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}
	
	public Customer(){
		
	}
	
	public Customer(String engineNumber, String name, String surname,
			SexType sex, Address adress,	CustomerType customerType) {
		setEngineNumber(engineNumber);
		setName(name);
		setSurname(surname);
		setSex(sex);
		setAdress(adress);
		setCustomerType(customerType);
	}

	public void validate() throws DomainException {
		// Name validate
		Preconditions.checkState(!(getName().length() <= 1),
				"The Customer name is not valid!");
		// Surname validate
		Preconditions.checkState(!(getSurname().length() <= 1),
				"The Customer surname is not valid!");
		// Male validate
		
		PersonalDataValidator validator = new PersonalDataValidator();
		
		Preconditions.checkState(!(validator.maleValidator(getSex())),
				"There is no such buyerType identifier. Please, correct it!\n"
						+ "You may use: COMPANY or PRIVATE_PERSON");
		// Address validate
		Preconditions.checkNotNull(adress,
				"The Customer adress can not be NULL!");
		// BuyerType validate
		Preconditions.checkState(
				!(validator.buyerTypeValidator(getCustomerType())),
				"There is no such buyerType identifier. Please, correct it!\n"
						+ "You may use: COMPANY or PRIVATE_PERSON");
	}

	public String toString() {

		return "Customer{" + "Id=" + id + "Engine Number=" + engineNumber + ", Name='"
				+ name + ", Surname=" + surname + ", Male=" + sex + ", Adress="
				+ adress.toString() + ", Customer Type=" + customerType + '}';
	}
}

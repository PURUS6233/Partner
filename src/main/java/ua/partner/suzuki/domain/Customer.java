package ua.partner.suzuki.domain;

import com.google.common.base.Preconditions;

public class Customer extends AbstractEngineNumberEntity {

	public Customer(String engineNumber, String name, String surname,
			String male, Adress adress, String phone, String email,
			String buyerType) throws DomainException {
		setEngineNumber(engineNumber);
		setName(name);
		setSurname(surname);
		setMale(male);
		setAdress(adress);
		setPhone(phone);
		setEmail(email);
		setBuyerType(buyerType);
	}

	private String engineNumber;
	private String name;
	private String surname;
	private String male;
	private Adress adress;
	private String phone;
	private String email;
	private String buyerType;

	PersonalDataValidator validator = new PersonalDataValidator();
	
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

	public void setName(String name) throws DomainException {
		Preconditions.checkState(!(name.length() <= 1),
				"The Customer name is not valid!");
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) throws DomainException {
		Preconditions.checkState(!(surname.length() <= 1),
				"The Customer surname is not valid!");
		this.surname = surname;
	}

	public String getMale() {
		return male;
	}

	public void setMale(String male) throws DomainException {
		this.male = validator.maleValidator(male);
	}

	public Adress getAdress() {
		return adress;
	}

	public void setAdress(Adress adress) throws DomainException {
		Preconditions.checkNotNull(adress,
				"The Customer adress can not be NULL!");
		this.adress = adress;
	}

	public String getPhone() {
		return phone;
	}

	private static final String PHONE_PATTERN = "^\\D\\d{8,13}$";

	public void setPhone(String phone) throws DomainException {
		validator.setPatternExpresion(PHONE_PATTERN);
		Preconditions.checkState(!(validator.checkWithRegExp(phone)),
				"The phone is not valid!");
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	private static final String EMAIL_PATTERN = "^.+@\\w+\\.\\w+$";

	public void setEmail(String email) throws DomainException {
		validator.setPatternExpresion(EMAIL_PATTERN);
		Preconditions.checkState(!(validator.checkWithRegExp(email)),
				"The email is not valid!");
		this.email = email;
	}

	public String getBuyerType() {
		return buyerType;
	}

	public void setBuyerType(String buyerType) throws DomainException {
		this.buyerType = validator.buyerTypeValidator(buyerType);
	}
}

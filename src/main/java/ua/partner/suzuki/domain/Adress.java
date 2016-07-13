package ua.partner.suzuki.domain;

import com.google.common.base.Preconditions;

public class Adress {

	public Adress(String street, String city, String district, String country,
			String postCode, String phone, String email) throws DomainException {
		setStreet(street);
		setCity(city);
		setDistrict(district);
		setCountry(country);
		setPostCode(postCode);
		setPhone(phone);
		setEmail(email);
	}

	private String street;
	private String city;
	private String district;
	private String country;
	private String postCode;
	private String phone;
	private String email;

	PersonalDataValidator validator = new PersonalDataValidator();

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) throws DomainException {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) throws DomainException {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) throws DomainException {
		this.district = district;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) throws DomainException {
		this.country = country;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) throws DomainException {
		this.postCode = postCode;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) throws DomainException {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws DomainException {
		this.email = email;
	}
	
	private static final String POSTCODE_PATTERN = "^\\d\\d\\d\\d\\d$";
	private static final String PHONE_PATTERN = "^\\D\\d{8,13}$";
	private static final String EMAIL_PATTERN = "^.+@\\w+\\.\\w+$";
	
	public void validate(){
		//Street validate
		Preconditions.checkState(!(getStreet().length() <= 1),
				"The street name is not valid!");
		//City validate
		Preconditions.checkState(!(getCity().length() <= 1),
				"The city name is not valid!");
		//District validate
		Preconditions.checkState(!(getDistrict().length() <= 1),
				"The district name is not valid!");
		//Country validate
		Preconditions.checkState(!(getCountry().length() <= 1),
				"The country name is not valid!");
		//PostCode validate
		validator.setPatternExpresion(POSTCODE_PATTERN);
		Preconditions.checkState(!(validator.checkWithRegExp(getPostCode())),
				"The postCode is not valid!");
		//Phone validate
		validator.setPatternExpresion(PHONE_PATTERN);
		Preconditions.checkState(!(validator.checkWithRegExp(getPhone())),
				"The phone is not valid!");
		//Email validate
		validator.setPatternExpresion(EMAIL_PATTERN);
		Preconditions.checkState(!(validator.checkWithRegExp(getEmail())),
				"The email is not valid!");
		
		
	}
	
	public String toString() {

		return "Address :" +
				" Street=" + street +
				", City='" + city + 
				", District=" + district +
				", Country=" + country +
				", Post Code=" + postCode +
				", Phone=" + phone +
				", Email=" + email +
				'}';
	}
}

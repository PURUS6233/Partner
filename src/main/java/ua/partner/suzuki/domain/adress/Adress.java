package ua.partner.suzuki.domain.adress;

import javax.xml.bind.annotation.XmlRootElement;

import ua.partner.suzuki.domain.DomainException;
import ua.partner.suzuki.domain.PersonalDataValidator;

import com.google.common.base.Preconditions;

@XmlRootElement
public class Adress {
	
	public Adress(){
		
	}

	public Adress(String street, String city, String district, String country,
			String postCode, String phone, String email) {
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

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	private static final String POSTCODE_PATTERN = "^\\d{4,5}$";
	private static final String PHONE_PATTERN = "^\\D\\d{8,13}$";
	private static final String EMAIL_PATTERN = "^.+@\\w+\\.\\w+$";

	public boolean validate() throws DomainException {
		// Street validate
		Preconditions.checkState(!(getStreet().length() <= 1),
				"The street name is not valid!");
		// City validate
		Preconditions.checkState(!(getCity().length() <= 1),
				"The city name is not valid!");
		// District validate
		Preconditions.checkState(!(getDistrict().length() <= 1),
				"The district name is not valid!");
		// Country validate
		Preconditions.checkState(!(getCountry().length() <= 1),
				"The country name is not valid!");
		
		PersonalDataValidator validator = new PersonalDataValidator();
		
		// PostCode validate
		Preconditions.checkState(
				(validator.checkWithRegExp(getPostCode(), POSTCODE_PATTERN)),
				"The postCode is not valid!");
		// Phone validate
		Preconditions.checkState(
				(validator.checkWithRegExp(getPhone(), PHONE_PATTERN)),
				"The phone is not valid!");
		// Email validate
		Preconditions.checkState(
				(validator.checkWithRegExp(getEmail(), EMAIL_PATTERN)),
				"The email is not valid!");
		return true;
	}

	public String toString() {

		return "Address {" + " Street=" + street + ", City=" + city
				+ ", District=" + district + ", Country=" + country
				+ ", Post Code=" + postCode + ", Phone=" + phone + ", Email="
				+ email + '}';
	}
}

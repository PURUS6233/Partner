package ua.partner.suzuki.domain;

import com.google.common.base.Preconditions;

import ua.partner.suzuki.exceptions.AddressException;

public class Adress {

	public Adress(String street, String city, String district, String country,
			String postCode) throws AddressException {
		setStreet(street);
		setCity(city);
		setDistrict(district);
		setCountry(country);
		setPostCode(postCode);
	}

	private String street;
	private String city;
	private String district;
	private String country;
	private String postCode;

	PersonalDataValidator validator = new PersonalDataValidator();

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) throws AddressException {
		Preconditions.checkState(!(street.length() <= 1),
				"The street name is not valid!");
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) throws AddressException {
		Preconditions.checkState(!(city.length() <= 1),
				"The city name is not valid!");
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) throws AddressException {
		Preconditions.checkState(!(district.length() <= 1),
				"The district name is not valid!");
		this.district = district;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) throws AddressException {
		Preconditions.checkState(!(country.length() <= 1),
				"The country name is not valid!");
		this.country = country;
	}

	public String getPostCode() {
		return postCode;
	}

	private static final String POSTCODE_PATTERN = "^\\d\\d\\d\\d\\d$";

	public void setPostCode(String postCode) throws AddressException {
		validator.setPatternExpresion(POSTCODE_PATTERN);
		Preconditions.checkState(!(validator.checkWithRegExp(postCode)),
				"The postCode is not valid!");
		this.postCode = postCode;
	}
}

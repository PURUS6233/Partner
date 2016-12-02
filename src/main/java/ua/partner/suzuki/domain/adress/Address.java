package ua.partner.suzuki.domain.adress;

import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.partner.suzuki.domain.Constants;
import ua.partner.suzuki.domain.DataValidator;
import ua.partner.suzuki.domain.Validatable;

import com.google.common.base.Preconditions;

@XmlRootElement
public class Address implements Validatable {

	private String street;
	private String city;
	private String district;
	private String country;
	private String postCode;
	private String phone;
	private String email;

	private static DataValidator validator = new DataValidator();

	private static Logger log = LoggerFactory.getLogger(Address.class);

	public Address() {

	}

	public Address(String street, String city, String district, String country,
			String postCode, String phone, String email) {
		setStreet(street);
		setCity(city);
		setDistrict(district);
		setCountry(country);
		setPostCode(postCode);
		setPhone(phone);
		setEmail(email);
		log.trace("Created address: email = " + email);
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
		log.trace("Set street to = " + street);
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
		log.trace("Set street to = " + street);
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
		log.trace("Set district to = " + district);
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
		log.trace("Set country to = " + country);
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
		log.trace("Set postCode to = " + postCode);
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
		log.trace("Set phone to = " + phone);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
		log.trace("Set email to = " + email);
	}

	@Override
	public boolean validate() {
		log.trace("Start validating Address object.");
		// Street validate
		Preconditions.checkState(
				!(getStreet().isEmpty() || getStreet() == null),
				"The street name is not valid!");
		// City validate
		Preconditions.checkState(!(getCity().isEmpty() || getCity() == null),
				"The city name is not valid!");
		// District validate
		Preconditions.checkState(
				!(getDistrict().isEmpty() || getDistrict() == null),
				"The district name is not valid!");
		// Country validate
		Preconditions.checkState(
				!(getCountry().isEmpty() || getCountry() == null),
				"The country name is not valid!");
		// PostCode validate
		Preconditions.checkState((validator.checkWithRegExp(getPostCode(),
				Constants.POSTCODE_PATTERN)), "The postCode is not valid!");
		// Phone validate
		Preconditions
				.checkState((validator.checkWithRegExp(getPhone(),
						Constants.PHONE_PATTERN)), "The phone is not valid!");
		// Email validate
		Preconditions
				.checkState((validator.checkWithRegExp(getEmail(),
						Constants.EMAIL_PATTERN)), "The email is not valid!");
		log.trace("Address object validated.");
		return true;
	}

	@Override
	public String toString() {
		return "Address [street=" + street + ", city=" + city + ", district="
				+ district + ", country=" + country + ", postCode=" + postCode
				+ ", phone=" + phone + ", email=" + email + "]";
	}
}

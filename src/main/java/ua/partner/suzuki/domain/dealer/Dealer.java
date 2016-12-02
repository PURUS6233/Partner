package ua.partner.suzuki.domain.dealer;

import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.partner.suzuki.domain.EngineNumberIdentifiable;
import ua.partner.suzuki.domain.Validatable;
import ua.partner.suzuki.domain.adress.Address;

import com.google.common.base.Preconditions;

@XmlRootElement
public class Dealer implements EngineNumberIdentifiable<String>, Validatable {

	private String name;
	private Address address;
	private String login;
	private String password;

	private static Logger log = LoggerFactory.getLogger(Dealer.class);

	public Dealer() {

	}

	public Dealer(String name, Address address, String login, String password) {
		setName(name);
		setAddress(address);
		setLogin(login);
		setPassword(password);
		log.trace("Created dealer: login = " + login);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		log.trace("Set dealer name to = " + name);
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
		log.trace("Set address to = " + address);
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
		log.trace("Set dealer login to = " + login);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
		log.trace("Set dealer password");
	}

	public boolean validate() {
		log.trace("Start validating Dealer object.");
		// Name validate
		Preconditions.checkState(!(getName().isEmpty() || getName() == null),
				"The Dealer name is not valid!");
		// Address validate
		Preconditions.checkNotNull(!getAddress().validate(),
				"The Dealer adress can not be NULL!");
		// Login validate
		Preconditions.checkState(!(getLogin().isEmpty() || getLogin() == null),
				"The Dealer login is not valid!");
		// Password validate
		Preconditions.checkState(!(getPassword().length() <= 4),
				"The Dealer password is not valid!");
		log.trace("Dealer object validated.");
		return true;
	}



	@Override
	public String toString() {
		return "Dealer [name=" + name + ", address=" + address + ", login="
				+ login + ", password=" + password + "]";
	}

	@Override
	public String getEngineNumber() {
		// TODO Auto-generated method stub
		return null;
	}

}
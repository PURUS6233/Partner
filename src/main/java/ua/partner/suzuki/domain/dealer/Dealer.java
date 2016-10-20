package ua.partner.suzuki.domain.dealer;

import javax.xml.bind.annotation.XmlRootElement;

import ua.partner.suzuki.domain.DomainException;
import ua.partner.suzuki.domain.adress.Adress;

import com.google.common.base.Preconditions;

@XmlRootElement
public class Dealer {

	public Dealer() {

	}

	public Dealer(String name, Adress adress, String login, String password) {
		setName(name);
		setAdress(adress);
		setLogin(login);
		setPassword(password);
	}

	private String name;
	private Adress adress;
	private String login;
	private String password;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Adress getAdress() {
		return adress;
	}

	public void setAdress(Adress adress) {
		this.adress = adress;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean validate() throws DomainException {

		// Name validate
		Preconditions.checkState(!(getName().length() <= 1),
				"The Customer name is not valid!");
		// Address validate
		Preconditions.checkNotNull(getAdress(),
				"The Customer adress can not be NULL!");
		// Login validate
		Preconditions.checkState(!(getLogin().length() <= 2),
				"The Dealer login is not valid!");
		// Password validate
		Preconditions.checkState(!(getPassword().length() <= 4),
				"The Dealer password is not valid!");
		return true;
	}

	public String toString() {

		return "Dealer{" + "Name='" + name + ", Adress=" + adress.toString()
				+ ", Login=" + login + ", Password=" + password + '}';
	}

}
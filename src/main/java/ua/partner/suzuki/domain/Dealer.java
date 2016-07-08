package ua.partner.suzuki.domain;

import com.google.common.base.Preconditions;

public class Dealer {
	
	public Dealer(String name, Adress adress, String phone, String email,
			String login, String password) throws DomainException {
		setName(name);
		setAdress(adress);
		setPhone(phone);
		setEmail(email);
		setLogin(login);
		setPassword(password);
	}

	private String name;
	private Adress adress;
	private String phone;
	private String email;
	private String login;
	private String password;

	PersonalDataValidator validator = new PersonalDataValidator();

	public String getName() {
		return name;
	}

	public void setName(String name) throws DomainException {
		Preconditions.checkState(!(name.length() <= 1),
				"The Customer name is not valid!");
		this.name = name;
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
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) throws DomainException {
		Preconditions.checkState(!(login.length() <= 3),
				"The Dealer login is not valid!");
		this.login = login;
	}
	
	public String getPassword() {
		final String dealerPassword =  password.toString();
		return dealerPassword;
	}

	public void setPassword(String password) throws DomainException {
		Preconditions.checkState(!(password.length() <= 5),
				"The Dealer password is not valid!");
		this.password = password;
	}

}
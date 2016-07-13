package ua.partner.suzuki.domain;

import com.google.common.base.Preconditions;

public class Customer extends AbstractIntEngineNumberEntity {

	public Customer(String engineNumber, String name, String surname,
			SexType sex, Adress adress, String phone, String email,
			BuyerType buyerType){
		setEngineNumber(engineNumber);
		setName(name);
		setSurname(surname);
		setSex(sex);
		setAdress(adress);
		setBuyerType(buyerType);
	}

	private String engineNumber;
	private String name;
	private String surname;
	private SexType sex;
	private Adress adress;
	private BuyerType buyerType;

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

	public Adress getAdress() {
		return adress;
	}

	public void setAdress(Adress adress) {
		this.adress = adress;
	}

	public BuyerType getBuyerType() {
		return buyerType;
	}

	public void setBuyerType(BuyerType buyerType) {
		this.buyerType = buyerType;
	}

	public void validate() throws DomainException {
		// Name validate
		Preconditions.checkState(!(getName().length() <= 1),
				"The Customer name is not valid!");
		// Surname validate
		Preconditions.checkState(!(getSurname().length() <= 1),
				"The Customer surname is not valid!");
		// Male validate
		Preconditions.checkState(!(validator.maleValidator(getSex())),
				"There is no such buyerType identifier. Please, correct it!\n"
						+ "You may use: COMPANY or PRIVATE_PERSON");
		// Address validate
		Preconditions.checkNotNull(adress,
				"The Customer adress can not be NULL!");
		// BuyerType validate
		Preconditions.checkState(!(validator.buyerTypeValidator(getBuyerType())),
				"There is no such buyerType identifier. Please, correct it!\n"
						+ "You may use: COMPANY or PRIVATE_PERSON");
	}

	public String toString() {

		return "Customer{" + "Engine Number=" + engineNumber + ", Name='"
				+ name + ", Surname=" + surname + ", Male=" + sex
				+ ", Adress=" + adress.toString() + ", Buyer Type=" + buyerType
				+ '}';
	}
}

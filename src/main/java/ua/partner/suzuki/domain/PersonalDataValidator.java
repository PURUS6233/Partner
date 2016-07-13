package ua.partner.suzuki.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

enum BuyerType {
	COMPANY, PRIVATE_PERSON
}

enum SexType {
	MALE, FEMALE
}

public class PersonalDataValidator {
	
	private Logger logger = LoggerFactory.getLogger(PersonalDataValidator.class.getName());
	
	private String patternExpresion;

	public String getPatternExpresion() {
		return patternExpresion;
	}

	public void setPatternExpresion(String exp) {
		this.patternExpresion = exp;
	}
	
	public boolean checkWithRegExp(final String source){ 
		Pattern p = Pattern.compile(getPatternExpresion());
        Matcher m = p.matcher(source);
		return m.matches();
	}
	
	public boolean maleValidator(SexType sex) {
		boolean valid = false;
		for (SexType buyerSex : SexType.values()) {
			if (buyerSex.equals(sex)){
				valid = true;
			}
		}
		return valid;
	}
	
	public boolean buyerTypeValidator(BuyerType type) {
		boolean valid = false;
		for (BuyerType buyerType : BuyerType.values()) {
			if (buyerType.equals(type)){
				valid = true;
			}
		}
		return valid;
	}
	
	/**public String maleValidator(SexType sex){//TODO
	String validMale;
	try {
		SexType buyerMale = Enum.valueOf(SexType.class,
				sex.toUpperCase());
		validMale = buyerMale.toString();
	} catch (IllegalArgumentException e) {
		throw new IllegalArgumentException(
				"There is no such MaleType (Customer_Male) identifier. Please, correct it!\n"
						+ "You may use: MALE or FEMALE");
	} catch (NullPointerException e) {
		throw new NullPointerException("The MaleType can not be NULL!");
	}
	return validMale;
}
	
	public String buyerTypeValidator(BuyerType buyerType) throws DomainException{
		String validBuyerType;
		try {
			BuyerType customerType = Enum.valueOf(BuyerType.class,
					buyerType.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new DomainException(
					"There is no such buyerType identifier. Please, correct it!\n"
							+ "You may use: COMPANY or PRIVATE_PERSON", e);
		} catch (NullPointerException e) {
			throw new NullPointerException("The BuyerType can not be NULL!");
		}
		return validBuyerType;
	}	*/
}

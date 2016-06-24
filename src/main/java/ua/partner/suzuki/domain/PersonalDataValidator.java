package ua.partner.suzuki.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

enum BuyerType {
	COMPANY, PRIVATE_PERSON
}

enum MaleType {
	MALE, FEMALE
}

public class PersonalDataValidator {

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
	
	public String maleValidator(String male){
		String validMale;
		try {
			MaleType buyerMale = Enum.valueOf(MaleType.class,
					male.toUpperCase());
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
	
	public String buyerTypeValidator(String buyerType){
		String validBuyerType;
		try {
			BuyerType customerType = Enum.valueOf(BuyerType.class,
					buyerType.toUpperCase());
			validBuyerType = customerType.toString();
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(
					"There is no such buyerType identifier. Please, correct it!\n"
							+ "You may use: COMPANY or PRIVATE_PERSON");
		} catch (NullPointerException e) {
			throw new NullPointerException("The BuyerType can not be NULL!");
		}
		return validBuyerType;
	}	
}

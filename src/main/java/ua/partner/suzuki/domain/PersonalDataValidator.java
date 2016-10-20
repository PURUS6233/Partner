package ua.partner.suzuki.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ua.partner.suzuki.domain.customer.BuyerType;
import ua.partner.suzuki.domain.customer.SexType;

public class PersonalDataValidator {

	public boolean checkWithRegExp(String source, String pattern) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(source);
		return m.matches();
	}

	public boolean maleValidator(SexType sex) {
		for (SexType buyerSex : SexType.values()) {
			if (buyerSex.equals(sex)) {
				return true;
			}
		}
		return false;
	}

	public boolean buyerTypeValidator(BuyerType type) {
		for (BuyerType buyerType : BuyerType.values()) {
			if (buyerType.equals(type)) {
				return true;
			}
		}
		return false;
	}
}

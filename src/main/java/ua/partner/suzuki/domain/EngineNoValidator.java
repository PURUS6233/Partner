package ua.partner.suzuki.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EngineNoValidator {

	private String patternExpresion;

	public void setPatternExpresion(String exp) {
		this.patternExpresion = exp;
	}

	public String getPatternExpresion() {
		return patternExpresion;
	}

	/**
	 * Validate customer input depending on regular expression *
	 * 
	 * @param source
	 *            . Input source address for validation
	 * @return true valid customer input, false invalid customer input
	 */

	public boolean checkWithRegExp(final String source) {
		Pattern p = Pattern.compile(getPatternExpresion());
		Matcher m = p.matcher(source);
		return m.matches();
	}
	
	public String[] divideEngineNumberToPrefixAndSerialNumber(String sourceEngineNumber)
			throws DomainException {

		String[] str = sourceEngineNumber.split("-");
		return str;
	}
	
	private static final String PREFIX_PATTERN = "^\\d\\d\\d\\d\\d(K|P|F)?$";

	public String checkPrefix(String prefix) throws DomainException {
		boolean valid;
		setPatternExpresion(PREFIX_PATTERN);
		valid = checkWithRegExp(prefix);
		if (valid) {
			return prefix;
		} else {
			throw new DomainException("Correct prefix number!!!" + "\n"
					+ " Prefix number length 6 chars for 4stroke engines,"
					+ " ends with F,K or P.");
		}
	}

	private static final String SERIAL_NUMBER_PATTERN = "^\\d\\d\\d\\d\\d\\d?$";

	public String checkSerialNumber(String serialNumber) throws DomainException {
		boolean valid;
		setPatternExpresion(SERIAL_NUMBER_PATTERN);
		valid = checkWithRegExp(serialNumber);
		if (valid) {
			return serialNumber;
		} else {
			throw new DomainException("Serial Number:" + serialNumber + ";\n"
					+ "Correct serial number!!! Serial number lenghth 6 chars.");
		}
	}

	public String findModelYear(String serialNumber)
			throws DomainException {
		String modelYear;
		try {
			char number = serialNumber.charAt(0);
			modelYear = "1" + number;
		} catch (StringIndexOutOfBoundsException e) {
			throw new StringIndexOutOfBoundsException(
					"The serialNumber must be specified. Please, correct it!");
		}
		return modelYear;
	}
}

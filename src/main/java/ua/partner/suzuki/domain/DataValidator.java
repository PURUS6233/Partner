package ua.partner.suzuki.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataValidator {

	/**
	 * Validate customer input depending on regular expression *
	 * 
	 * @param source
	 *            . Input source address for validation
	 * @return true valid customer input, false invalid customer input
	 */
	
	private static Logger log = LoggerFactory.getLogger(DataValidator.class);

	public String[] divideEngineNumberToPrefixAndSerialNumber(
			String sourceEngineNumber) {
		log.trace("Divide engine number: " + sourceEngineNumber + " to prefix and serial number;");
		String[] str = sourceEngineNumber.split("-");
		log.trace("Engine number split to prefix: " + str[0] + ", and serial number: " + str[1]);
		
		return str;
	}

	public boolean checkWithRegExp(String source, String pattern) {
		log.trace("Check source: " + source + ", with equivalence to pattern: " + pattern);
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(source);
		return m.matches();
	}

	public String findModelYear(String serialNumber) throws DomainException {
		log.trace("Finding model year for serial number: " + serialNumber);
		if(serialNumber.isEmpty()){
			throw new DomainException("Serial Number can not be empty!");
		}
		String modelYear;
		try {
			char number = serialNumber.charAt(0);
			modelYear = "1" + number;
		} catch (StringIndexOutOfBoundsException e) {
			log.error("The serialNumber is not specified. Please, correct it!" + e);
			throw new StringIndexOutOfBoundsException(
					"The serialNumber must be specified. Please, correct it!");
		}
		log.trace("Model year is: " + modelYear);
		return modelYear;
	}
}

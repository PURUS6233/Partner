package ua.partner.suzuki.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

enum Model {
	DF2_5("00252F"), DF4("00402F"), DF4A("00403F"), DF5("00502F"), DF5A(
			"00503F"), DF6("00602F"), DF6A("00603F"), DF8A("00801F"), DF9_9A(
			"00994F"), DF9_9B("00995F"), DF15A("01504F"), DF20A("02002F"), DF25VTwin(
			"02503F"), DF25A("02504F"), DF30A("03003F"), DF40A("04003F"), DF40AST(
			"04004F"), DF50A("05003F"), DF50AV("05004F"), DF60A("06002F"), DF60AV(
			"06003F"), DF70A("07003F"), DF80A("08002F"), DF90A("09003F"), DF100A(
			"10003F"), DF115AT("11503F"), DF115AZ("11503Z"), DF115AST("11504F"), DF140AT(
			"14003F"), DF140AZ("14003Z"), DF150T("15002F"), DF150Z("15002Z"), DF175T(
			"17502F"), DF175Z("17502Z"), DF200AP("20003P"), DF200T("20002F"), DF200Z(
			"20002Z"), DF200AT("20003F"), DF200AZ("20003Z"), DF225T("22503F"), DF225Z(
			"22503Z"), DF250T("25003F"), DF250Z("25003Z"), DF250S("25004F"), DF250AP(
			"25003P"), DF300AP("30002P"), DT9_9A("00996"), DT9_9AK("00993K"), DT15A(
			"01504"), DT15AK("01503K"), DT25K("02503K"), DT30("03005"), DT40W(
			"04005");

	private String modelCode;

	private static Map<String, Model> map = new HashMap<String, Model>();
	static {
		for (Model modelEnum : Model.values()) {
			map.put(modelEnum.modelCode, modelEnum);
		}
	}

	private Model(String modelCode) {
		this.modelCode = modelCode;
	}

	public static Model modelFromPrefix(String modelCode)
			throws DomainException {
		if (map.containsKey(modelCode)) {
			return map.get(modelCode);
		}
		throw new IllegalArgumentException("Model number not found.");
	}
}

enum Status {
	IN_STOCK, PROCESSED, NEEDS_CLARIFICATION, APPROVED, REJECTED, ON_CONSIGNATION, STOLEN
}

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

	public boolean statusValidator(Status status) {
		boolean valid = false;
		for (Status obmStatus : Status.values()) {
			if (obmStatus.equals(status)){
				valid = true;
			}
		}
		return valid;
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

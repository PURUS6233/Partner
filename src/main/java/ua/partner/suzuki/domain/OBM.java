package ua.partner.suzuki.domain;

public class OBM extends AbstractEngineNumberEntity { // FIXME Builder Pattern

	/**
	 * This constructor is must be used when receiving data from UI Forms.
	 * 
	 * @param prefix
	 *            First 5 (for 2stroke engines) or 6 (for 4stroke engines) chars
	 *            of engine number. Prefix describes Model (Horse Power and for
	 *            some engines it can describe production year). It consist from
	 *            literals and numbers.
	 * @param serialNumber
	 *            Second 6 chars of engine number. It is identical serial number
	 *            of the engine. First 2 digits describe production year. It
	 *            consist only from numbers.
	 * @throws EngineNoLoaderException
	 *             Exception is throws when incorrect number is putting into the
	 *             constructor
	 * 
	 */

	public OBM(String prefix, String serialNumber, String status)
			throws DomainException {
		setEngineNumber(prefix, serialNumber);
		setModelYear(serialNumber);
		setModel(prefix);
		setStatus(status);
	}

	/**
	 * This constructor is must be used when receiving multiply entry data.
	 * 
	 * @param EngineNumber
	 *            Engine number consist from 2 parts divided by "-". First part
	 *            is "prefix" and the second part is "serial number".
	 * @throws EngineNoLoaderException
	 *             Exception is throws when incorrect number is putting into the
	 *             constructor
	 */

	public OBM(String engineNumber) throws DomainException {
		setEngineNumber(engineNumber);
		setModelYear(serialNumber);
		setModel(prefix);
		setStatus(status);
	}

	private String engineNumber;
	private String modelYear;
	private String model;
	private String status = "IN_STOCK";
	private String prefix;
	private String serialNumber;

	EngineNoValidator validator = new EngineNoValidator();

	@Override
	public String getEngineNumber() {
		return engineNumber;
	}

	private void setEngineNumber(String sourceEngineNumber) throws DomainException {

		String[] str = sourceEngineNumber.split("-");
		String sourcePrefix = str[0];
		String sourceSerialNumber = str[1];

		setPrefix(sourcePrefix);
		setSerialNumber(sourceSerialNumber);

		String prefix = getPrefix();
		String serialNumber = getSerialNumber();

		String engineNumber = prefix.concat("-").concat(serialNumber);
		this.engineNumber = engineNumber;

	}

	public void setEngineNumber(String prefix, String serialNumber)
			throws DomainException {
		setPrefix(prefix);
		setSerialNumber(serialNumber);

		String enginePrefix = getPrefix();
		String engineSerialNumber = getSerialNumber();

		String engineNumber = enginePrefix.concat("-").concat(
				engineSerialNumber);
		this.engineNumber = engineNumber;
	}

	public String getModelYear() {
		return modelYear;
	}

	public void setModelYear(String serialNumber)
			throws DomainException {
		String modelYear = validator.findModelYear(serialNumber);
		this.modelYear = modelYear;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String prefix) throws DomainException {
		String model = Model.modelFromPrefix(prefix);
		this.model = model;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = validator.statusValidator(status);
	}

	public String getPrefix() {
		return prefix;
	}

	private static final String PREFIX_PATTERN = "^\\d\\d\\d\\d\\d(K|P|F)?$";

	public void setPrefix(String prefix) throws DomainException {
		boolean valid;
		validator.setPatternExpresion(PREFIX_PATTERN);
		valid = validator.checkWithRegExp(prefix);
		if (valid) {
			this.prefix = prefix;
		} else {
			throw new DomainException("Correct prefix number!!!" + "\n"
					+ " Prefix number length 6 chars for 4stroke engines,"
					+ " ends with F,K or P.");
		}
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	private static final String SERIAL_NUMBER_PATTERN = "^\\d\\d\\d\\d\\d\\d?$";

	public void setSerialNumber(String serialNumber) throws DomainException {
		boolean valid;
		validator.setPatternExpresion(SERIAL_NUMBER_PATTERN);
		valid = validator.checkWithRegExp(serialNumber);
		if (valid) {
			this.serialNumber = serialNumber;
		} else {
			throw new DomainException("Serial Number:" + serialNumber + ";\n"
					+ "Correct serial number!!! Serial number lenghth 6 chars.");
		}
	}

	public String toString() {

		String model = getModel();
		String engineNumber = getEngineNumber();
		String modelYear = getModelYear();
		String status = getStatus();

		return new StringBuffer("Model= " + model + ";\n" + "Engine Number= "
				+ engineNumber + ";\n" + "Model Year= " + modelYear + ";\n"
				+ "Status= " + status).toString();
	}

	public boolean checkExistance(String inputEngineNo) {// TODO
		
		return true;
	}
}

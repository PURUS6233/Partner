package ua.partner.suzuki.domain;

public class OBM extends AbstractIntEngineNumberEntity {

	public OBM(String engineNumber, String modelYear, Model model){
		setEngineNumber(engineNumber);
		setModelYear(modelYear);
		setModel(model);
	}
	
	public OBM(String engineNumber, String modelYear, Model model, Status status){
		setEngineNumber(engineNumber);
		setModelYear(modelYear);
		setModel(model);
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

	private String engineNumber;
	private String modelYear;
	private Model model;
	private Status status = Status.IN_STOCK;
	
	@Override
	public String getEngineNumber() {
		return engineNumber;
	}

	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
	}

	public String getModelYear() {
		return modelYear;
	}

	public void setModelYear(String modelYear) {
		this.modelYear = modelYear;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public static OBM createOBMFromEngineNumber(String engineNumber) throws DomainException {
		
		EngineNoValidator validator = new EngineNoValidator();
		String[] engineNumberData = validator.divideEngineNumberToPrefixAndSerialNumber(engineNumber);
		String prefix = validator.checkPrefix(engineNumberData[0]);
		String serialNumber = validator.checkSerialNumber(engineNumberData[1]);
		String modelYear = validator.findModelYear(serialNumber);
		Model model = Model.modelFromPrefix(prefix);
		OBM obm = new OBM(engineNumber, modelYear, model);
		return obm;
	}
	
public static OBM createOBMFromEngineNumber(String engineNumber, Status status) throws DomainException {
		
		EngineNoValidator validator = new EngineNoValidator();
		String[] engineNumberData = validator.divideEngineNumberToPrefixAndSerialNumber(engineNumber);
		String prefix = validator.checkPrefix(engineNumberData[0]);
		String serialNumber = validator.checkSerialNumber(engineNumberData[1]);
		String modelYear = validator.findModelYear(serialNumber);
		Model model = Model.modelFromPrefix(prefix);
		OBM obm = new OBM(engineNumber, modelYear, model, status);
		return obm;
	}

	public String toString() {

		return "OBM{" + "Engine Number=" + engineNumber + ", Model='" + model
				+ ", Model Year=" + modelYear + ", Status=" + status + '}';
	}

	public boolean checkExistance(String inputEngineNo) {// TODO

		return true;
	}
}

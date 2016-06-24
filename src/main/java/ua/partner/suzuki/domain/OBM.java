package ua.partner.suzuki.domain;

public class OBM {

	public OBM(String engineNumber, String modelYear, String model,
			String status) {
		setEngineNumber(engineNumber);
		setModelYear(modelYear);
		setModel(model);
		setStatus(status);
	}

	private String engineNumber;
	private String modelYear;
	private String model;
	private String status;

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

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean checkExistance() {
		return true;
	}
}

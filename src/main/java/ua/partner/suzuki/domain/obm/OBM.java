package ua.partner.suzuki.domain.obm;

import javax.xml.bind.annotation.XmlRootElement;

import ua.partner.suzuki.domain.EngineNumberIdentified;

@XmlRootElement
public class OBM implements EngineNumberIdentified {

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
	
	public OBM() {

	}

	public OBM(String engineNumber, String modelYear, Model model) {
		setEngineNumber(engineNumber);
		setModelYear(modelYear);
		setModel(model);
	}

	public OBM(String engineNumber, String modelYear, Model model, Status status) {
		setEngineNumber(engineNumber);
		setModelYear(modelYear);
		setModel(model);
		setStatus(status);
	}

	public String toString() {

		return "OBM{" + "Engine Number=" + engineNumber + ", Model='" + model
				+ ", Model Year=" + modelYear + ", Status=" + status + '}';
	}

}

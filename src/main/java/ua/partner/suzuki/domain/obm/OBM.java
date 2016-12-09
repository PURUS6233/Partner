package ua.partner.suzuki.domain.obm;

import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import ua.partner.suzuki.domain.Constants;
import ua.partner.suzuki.domain.DataValidator;
import ua.partner.suzuki.domain.EngineNumberIdentifiable;
import ua.partner.suzuki.domain.Validatable;

@XmlRootElement
public class OBM implements EngineNumberIdentifiable<String>, Validatable {

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

	private static DataValidator validator = new DataValidator();

	private static Logger log = LoggerFactory.getLogger(OBM.class);

	public OBM() {

	}

	public OBM(String engineNumber, String modelYear, Model model) {
		setEngineNumber(engineNumber);
		setModelYear(modelYear);
		setModel(model);
		log.trace("Created OBM: engine number = " + engineNumber);
	}

	public OBM(String engineNumber, String modelYear, Model model, Status status) {
		this(engineNumber, modelYear, model);
		setStatus(status);
	}

	@Override
	public String getEngineNumber() {
		return engineNumber;
	}

	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
		log.trace("Set engine number to = " + engineNumber);
	}

	public String getModelYear() {
		return modelYear;
	}

	public void setModelYear(String modelYear) {
		this.modelYear = modelYear;
		log.trace("Set model year to = " + modelYear);
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
		log.trace("Set model to = " + model.toString());
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
		log.trace("Set status to = " + status.toString());
	}

	@Override
	public boolean validate() {
		log.trace("Start validating OBM object.");
		Preconditions.checkState((validator.checkWithRegExp(getEngineNumber(),
				Constants.ENGINE_NUMBER_PATTERN)),
				"The engine number is not valid!");
		Preconditions.checkState(
				!(getModelYear().isEmpty() || getModelYear() == null),
				"The model year is not valid!");
		Preconditions.checkState(!(getModel() == null),
				"The customer surname is not valid!");
		Preconditions.checkState(!(getStatus() == null),
				"The customer gender is not valid!");
		log.trace("OBM object validated.");
		return true;
	}

	@Override
	public String toString() {
		return "OBM [engineNumber=" + engineNumber + ", modelYear=" + modelYear
				+ ", model=" + model + ", status=" + status + "]";
	}
}

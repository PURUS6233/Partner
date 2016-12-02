package ua.partner.suzuki.domain.obm.maintenance;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import ua.partner.suzuki.domain.EngineNumberIdentifiable;
import ua.partner.suzuki.domain.Validatable;
import ua.partner.suzuki.domain.adress.Address;

public class Maintenance implements EngineNumberIdentifiable<String>,
		Validatable {

	private Integer id;
	private String engineNumber;
	private Date executionDate;
	private MaintenanceType maintenanceType;
	private ServiceType serviceType;
	private String hours;
	private String note;
	private String SDSFile;

	private static Logger log = LoggerFactory.getLogger(Address.class);

	public Maintenance() {
	}

	public Maintenance(String engineNumber, Date executionDate,
			MaintenanceType maintenanceType, ServiceType serviceType,
			String hours, String note, String SDSFile) {
		this.engineNumber = engineNumber;
		this.executionDate = executionDate;
		this.maintenanceType = maintenanceType;
		this.serviceType = serviceType;
		this.hours = hours;
		this.note = note;
		this.SDSFile = SDSFile;
		log.trace("Created maintenence: engine number = " + engineNumber
				+ ", maintenance type = " + maintenanceType + ".");
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
		log.trace("Set id to = " + id);
	}

	@Override
	public String getEngineNumber() {
		return engineNumber;
	}

	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
		log.trace("Set engine number to = " + engineNumber);
	}

	public Date getExecutionDate() {
		return executionDate;
	}

	public void setExecutionDate(Date executionDate) {
		this.executionDate = executionDate;
		log.trace("Set execution date to = " + executionDate.toString());
	}

	public MaintenanceType getMaintenanceType() {
		return maintenanceType;
	}

	public void setMaintenanceType(MaintenanceType maintenanceType) {
		this.maintenanceType = maintenanceType;
		log.trace("Set maintenance type to = " + maintenanceType.toString());
	}

	public ServiceType getServiceType() {
		return serviceType;
	}

	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
		log.trace("Set service type to = " + serviceType.toString());
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String mileage) {
		this.hours = mileage;
		log.trace("Set hours to = " + hours);
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
		log.trace("Set note to = " + note);
	}

	public String getSDSFile() {
		return SDSFile;
	}

	public void setSDSFile(String SDSFile) {
		this.SDSFile = SDSFile;
		log.trace("Set SDS File!");
	}

	@Override
	public boolean validate() {
		log.trace("Start validating Maintenance object.");
		Preconditions.checkState(
				!(getEngineNumber().isEmpty() || getEngineNumber() == null),
				"The engine number is not valid!");
		Preconditions.checkState(
				!(getExecutionDate() == null),
				"The execution date year can not be null!");
		Preconditions.checkState(!(getMaintenanceType() == null),
				"The maintenance type is not valid!");
		Preconditions.checkState(!(getServiceType() == null),
				"The service type is not valid!");
		Preconditions.checkState(
				!(getHours().isEmpty() || getHours() == null),
				"The hours number is not valid!");
		Preconditions.checkState(
				!(getNote().isEmpty() || getNote() == null),
				"The note can not be empty or null!");
		Preconditions.checkState(
				!(getSDSFile().isEmpty() || getSDSFile() == null),
				"The SDSFile is not valid!");
		log.trace("Maintenance object validated.");
		return true;
	}

	@Override
	public String toString() {
		return "Maintenance [id=" + id + ", engineNumber=" + engineNumber
				+ ", executionDate=" + executionDate + ", maintenanceType="
				+ maintenanceType + ", serviceType=" + serviceType + ", hours="
				+ hours + ", note=" + note + ", SDSFile=" + SDSFile + "]";
	}

}

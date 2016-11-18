package ua.partner.suzuki.domain.obm;

import java.util.Date;

import ua.partner.suzuki.domain.EngineNumberIdentified;

public class Maintenance implements EngineNumberIdentified {	

	private Integer Id;
	private String engineNumber;
	private Date executionDate;
	private MaintenanceType maintenanceType;
	private ServiceType serviceType;
	private String hours;
	private String note;
	private String SDSFile;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	@Override
	public String getEngineNumber() {
		return engineNumber;
	}

	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
	}

	public Date getExecutionDate() {
		return executionDate;
	}

	public void setExecutionDate(Date executionDate) {
		this.executionDate = executionDate;
	}

	public MaintenanceType getMaintenanceType() {
		return maintenanceType;
	}

	public void setMaintenanceType(MaintenanceType maintenanceType) {
		this.maintenanceType = maintenanceType;
	}

	public ServiceType getServiceType() {
		return serviceType;
	}

	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String mileage) {
		this.hours = mileage;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getSDSFile() {
		return SDSFile;
	}

	public void setSDSFile(String SDSFile) {
		this.SDSFile = SDSFile;
	}
	
	public Maintenance() {
	}
	
	public Maintenance(Date executionDate,
			MaintenanceType maintenanceType, ServiceType serviceType,
			String hours, String note, String SDSFile) {
		super();
		this.executionDate = executionDate;
		this.maintenanceType = maintenanceType;
		this.serviceType = serviceType;
		this.hours = hours;
		this.note = note;
		this.SDSFile = SDSFile;
	}
}

package ua.partner.suzuki.domain.obm;

import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import ua.partner.suzuki.domain.EngineNoValidator;
import ua.partner.suzuki.domain.customer.Customer;

@XmlRootElement
public class RegistrationBuilder {

	private String engineNumber_prefix;
	private String engineNumber_serialNumber;
	private String engineNumber;
	private WarrantyType warrantyType;
	private String dealerLogin;
	private Customer customer;
	private Date dateSold;
	private Date dateRegistered;
	private Date dateDelivered;
	private Date warrantyExpiration;
	private int penalty;
	private UnitSurvey unitSurvey;
	private boolean unitOperation;
	private boolean unitMaintenance;
	private boolean safetyFeatures;
	private boolean warrantyPolicy;
	private boolean ownersSignature;

	public String getEngineNumber_prefix() {
		return engineNumber_prefix;
	}

	public void setEngineNumber_prefix(String engineNumber_prefix) {
		this.engineNumber_prefix = engineNumber_prefix;
	}

	public String getEngineNumber_serialNumber() {
		return engineNumber_serialNumber;
	}

	public void setEngineNumber_serialNumber(String engineNumber_serialNumber) {
		this.engineNumber_serialNumber = engineNumber_serialNumber;
	}

	public WarrantyType getWarrantyType() {
		return warrantyType;
	}

	public void setWarrantyType(WarrantyType warrantyType) {
		this.warrantyType = warrantyType;
	}

	public String getDealerLogin() {
		return dealerLogin;
	}

	public void setDealerLogin(String dealerLogin) {
		this.dealerLogin = dealerLogin;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Date getDateSold() {
		return dateSold;
	}

	public void setDateSold(Date dateSold) {
		this.dateSold = dateSold;
	}

	public Date getDateRegistered() {
		return dateRegistered;
	}

	public void setDateRegistered(Date dateRegistered) {
		this.dateRegistered = dateRegistered;
	}

	public Date getDateDelivered() {
		return dateDelivered;
	}

	public void setDateDelivered(Date dateDelivered) {
		this.dateDelivered = dateDelivered;
	}

	public Date getWarrantyExpiration() {
		return warrantyExpiration;
	}

	public void setWarrantyExpiration(Date warrantyExpiration) {
		this.warrantyExpiration = warrantyExpiration;
	}

	public UnitSurvey getUnitSurvey() {
		return unitSurvey;
	}

	public void setUnitSurvey(UnitSurvey unitSurvey) {
		this.unitSurvey = unitSurvey;
	}

	public boolean isUnitOperation() {
		return unitOperation;
	}

	public void setUnitOperation(boolean unitOperation) {
		this.unitOperation = unitOperation;
	}

	public boolean isUnitMaintenance() {
		return unitMaintenance;
	}

	public void setUnitMaintenance(boolean unitMaintenance) {
		this.unitMaintenance = unitMaintenance;
	}

	public boolean isSafetyFeatures() {
		return safetyFeatures;
	}

	public void setSafetyFeatures(boolean safetyFeatures) {
		this.safetyFeatures = safetyFeatures;
	}

	public boolean isWarrantyPolicy() {
		return warrantyPolicy;
	}

	public void setWarrantyPolicy(boolean warrantyPolicy) {
		this.warrantyPolicy = warrantyPolicy;
	}

	public boolean isOwnersSignature() {
		return ownersSignature;
	}

	public void setOwnersSignature(boolean ownersSignature) {
		this.ownersSignature = ownersSignature;
	}

	public RegistrationBuilder() {

	}
	
	

	public RegistrationBuilder(String engineNumber_prefix,
			String engineNumber_serialNumber, WarrantyType warrantyType,
			String dealerLogin, Customer customer, Date dateSold,
			Date dateRegistered, Date dateDelivered, UnitSurvey unitSurvey,
			boolean unitOperation, boolean unitMaintenance,
			boolean safetyFeatures, boolean warrantyPolicy,
			boolean ownersSignature) {
		super();
		this.engineNumber_prefix = engineNumber_prefix;
		this.engineNumber_serialNumber = engineNumber_serialNumber;
		this.warrantyType = warrantyType;
		this.dealerLogin = dealerLogin;
		this.customer = customer;
		this.dateSold = dateSold;
		this.dateRegistered = new Date();
		this.dateDelivered = dateDelivered;
		this.unitSurvey = unitSurvey;
		this.unitOperation = unitOperation;
		this.unitMaintenance = unitMaintenance;
		this.safetyFeatures = safetyFeatures;
		this.warrantyPolicy = warrantyPolicy;
		this.ownersSignature = ownersSignature;
	}

	private static final int WARRANTY_PERIOD_4_STROKE_PLEASURE = 36;

	private static final int WARRANTY_PERIOD_COMMERCIAL = 12; // period is
																// the same
																// for
																// 2-stroke
																// and
																// 4-stroke
	private static final int WARRANTY_PERIOD_2_STROKE_PLEASURE = 24;

	private static final String PREFIX_4_STROKE_PATTERN = "^\\d\\d\\d\\d\\d(F|Z)$";
	private static final String PREFIX_2_STROKE_PATTERN = "^\\d\\d\\d\\d\\d(K|P)?$";

	private EngineNoValidator validator = new EngineNoValidator();

	public RegistrationBuilder engineNumber() {
		this.engineNumber = engineNumber_prefix + "-"
				+ engineNumber_serialNumber;
		return this;
	}

	public RegistrationBuilder warrantyExpiration() {
		Calendar c = Calendar.getInstance();
		c.setTime(dateDelivered);
		if ((warrantyType.equals(WarrantyType.PRIVATE) || warrantyType
				.equals(WarrantyType.DEMO))
				&& validator.checkWithRegExp(engineNumber_prefix,
						PREFIX_4_STROKE_PATTERN)) {
			c.add(Calendar.MONTH, WARRANTY_PERIOD_4_STROKE_PLEASURE);
			this.warrantyExpiration = c.getTime();
			return this;
		} else if ((warrantyType.equals(WarrantyType.PRIVATE) || warrantyType
				.equals(WarrantyType.DEMO))
				&& validator.checkWithRegExp(engineNumber_prefix,
						PREFIX_2_STROKE_PATTERN)) {
			c.add(Calendar.MONTH, WARRANTY_PERIOD_2_STROKE_PLEASURE);
			this.warrantyExpiration = c.getTime();
			return this;
		} else {
			c.add(Calendar.MONTH, WARRANTY_PERIOD_COMMERCIAL);
			this.warrantyExpiration = c.getTime();
			return this;
		}
	}

	public RegistrationBuilder penalty() {
		Penalty penalty = new Penalty(dateRegistered, dateDelivered);
		this.penalty = penalty.calculatePenalty();
		return this;
	}

	public Registration createRegistration() {
		return new Registration(engineNumber, warrantyType, dealerLogin,
				customer, dateSold, dateRegistered, dateDelivered,
				warrantyExpiration, penalty, unitSurvey, unitOperation, 
				unitMaintenance, safetyFeatures, warrantyPolicy, ownersSignature);
	}
}

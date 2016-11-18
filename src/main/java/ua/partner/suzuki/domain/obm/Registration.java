package ua.partner.suzuki.domain.obm;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import ua.partner.suzuki.domain.EngineNumberIdentified;
import ua.partner.suzuki.domain.customer.Customer;

@XmlRootElement
public class Registration implements EngineNumberIdentified {

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

	@Override
	public String getEngineNumber() {
		return engineNumber;
	}

	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
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

	public int getPenalty() {
		return penalty;
	}

	public void setPenalty(int penalty) {
		this.penalty = penalty;
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

	public Registration() {
	}



	public Registration(String engineNumber, WarrantyType warrantyType,
			String dealerLogin, Customer customer, Date dateSold,
			Date dateRegistered, Date dateDelivered, Date warrantyExpiration,
			int penalty, UnitSurvey unitSurvey, boolean unitOperation,
			boolean unitMaintenance, boolean safetyFeatures,
			boolean warrantyPolicy, boolean ownersSignature) {
		super();
		this.engineNumber = engineNumber;
		this.warrantyType = warrantyType;
		this.dealerLogin = dealerLogin;
		this.customer = customer;
		this.dateSold = dateSold;
		this.dateRegistered = dateRegistered;
		this.dateDelivered = dateDelivered;
		this.warrantyExpiration = warrantyExpiration;
		this.penalty = penalty;
		this.unitSurvey = unitSurvey;
		this.unitOperation = unitOperation;
		this.unitMaintenance = unitMaintenance;
		this.safetyFeatures = safetyFeatures;
		this.warrantyPolicy = warrantyPolicy;
		this.ownersSignature = ownersSignature;
	}

	@Override
	public String toString() {
		return "Registration [engineNumber=" + engineNumber + ", warrantyType="
				+ warrantyType + ", dealerLogin=" + dealerLogin + ", customer="
				+ customer + ", dateSold=" + dateSold + ", dateRegistered="
				+ dateRegistered + ", dateDelivered=" + dateDelivered
				+ ", warrantyExpiration=" + warrantyExpiration + ", penalty="
				+ penalty + ", unitSurvey=" + unitSurvey + ", unitOperation="
				+ unitOperation + ", unitMaintenance=" + unitMaintenance
				+ ", safetyFeatures=" + safetyFeatures + ", warrantyPolicy="
				+ warrantyPolicy + ", ownersSignature=" + ownersSignature + "]";
	}

	
}

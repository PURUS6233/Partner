package ua.partner.suzuki.domain.obm.registration;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import ua.partner.suzuki.domain.EngineNumberIdentifiable;
import ua.partner.suzuki.domain.Validatable;
import ua.partner.suzuki.domain.customer.Customer;

@XmlRootElement
public class Registration implements EngineNumberIdentifiable<String>,
		Validatable {

	private String engineNumber;
	private WarrantyType warrantyType;
	private String dealerLogin;
	private Customer customer;
	private Date dateSold;
	private Date dateRegistered = new Date();
	private Date dateDelivered;
	private Date warrantyExpiration;
	private int penalty;
	private UnitSurvey unitSurvey;
	private boolean unitOperation;
	private boolean unitMaintenance;
	private boolean safetyFeatures;
	private boolean warrantyPolicy;
	private boolean ownersSignature;

	private static Logger log = LoggerFactory.getLogger(Registration.class);

	public Registration() {
	}

	public Registration(String engineNumber, WarrantyType warrantyType,
			String dealerLogin, Customer customer, Date dateSold,
			Date dateDelivered, UnitSurvey unitSurvey, boolean unitOperation,
			boolean unitMaintenance, boolean safetyFeatures,
			boolean warrantyPolicy, boolean ownersSignature) {
		this.engineNumber = engineNumber;
		this.warrantyType = warrantyType;
		this.dealerLogin = dealerLogin;
		this.customer = customer;
		this.dateSold = dateSold;
		this.dateDelivered = dateDelivered;
		this.unitSurvey = unitSurvey;
		this.unitOperation = unitOperation;
		this.unitMaintenance = unitMaintenance;
		this.safetyFeatures = safetyFeatures;
		this.warrantyPolicy = warrantyPolicy;
		this.ownersSignature = ownersSignature;
		log.trace("Created Registration for engine number = " + engineNumber);
	}

	@Override
	public String getEngineNumber() {
		return engineNumber;
	}

	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
		log.trace("Set engine number to = " + engineNumber);
	}

	public WarrantyType getWarrantyType() {
		return warrantyType;
	}

	public void setWarrantyType(WarrantyType warrantyType) {
		this.warrantyType = warrantyType;
		log.trace("Set warranty type to = " + warrantyType);
	}

	public String getDealerLogin() {
		return dealerLogin;
	}

	public void setDealerLogin(String dealerLogin) {
		this.dealerLogin = dealerLogin;
		log.trace("Set dealer login to = " + dealerLogin);
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
		log.trace("Set customer to = " + customer.toString());
	}

	public Date getDateSold() {
		return dateSold;
	}

	public void setDateSold(Date dateSold) {
		this.dateSold = dateSold;
		log.trace("Set date sold to = " + dateSold.toString());
	}

	public Date getDateRegistered() {
		return dateRegistered;
	}

	public void setDateRegistered(Date dateRegistered) {
		this.dateRegistered = dateRegistered;
		log.trace("Set date registered to = " + dateRegistered.toString());
	}

	public Date getDateDelivered() {
		return dateDelivered;
	}

	public void setDateDelivered(Date dateDelivered) {
		this.dateDelivered = dateDelivered;
		log.trace("Set date delivered to = " + dateDelivered.toString());
	}

	public Date getWarrantyExpiration() {
		return warrantyExpiration;
	}

	public void setWarrantyExpiration(Date warrantyExpiration) {
		this.warrantyExpiration = warrantyExpiration;
		log.trace("Set warranty expiration to = "
				+ warrantyExpiration.toString());
	}

	public int getPenalty() {
		return penalty;
	}

	public void setPenalty(int penalty) {
		this.penalty = penalty;
		log.trace("Set penalty to = " + penalty);
	}

	public UnitSurvey getUnitSurvey() {
		return unitSurvey;
	}

	public void setUnitSurvey(UnitSurvey unitSurvey) {
		this.unitSurvey = unitSurvey;
		log.trace("Set unit survey to = " + unitSurvey);
	}

	public boolean isUnitOperation() {
		return unitOperation;
	}

	public void setUnitOperation(boolean unitOperation) {
		this.unitOperation = unitOperation;
		log.trace("Set unit operation to = " + unitOperation);
	}

	public boolean isUnitMaintenance() {
		return unitMaintenance;
	}

	public void setUnitMaintenance(boolean unitMaintenance) {
		this.unitMaintenance = unitMaintenance;
		log.trace("Set unit maintenance to = " + unitMaintenance);
	}

	public boolean isSafetyFeatures() {
		return safetyFeatures;
	}

	public void setSafetyFeatures(boolean safetyFeatures) {
		this.safetyFeatures = safetyFeatures;
		log.trace("Set safety features to = " + safetyFeatures);
	}

	public boolean isWarrantyPolicy() {
		return warrantyPolicy;
	}

	public void setWarrantyPolicy(boolean warrantyPolicy) {
		this.warrantyPolicy = warrantyPolicy;
		log.trace("Set warranty policy to = " + warrantyPolicy);
	}

	public boolean isOwnersSignature() {
		return ownersSignature;
	}

	public void setOwnersSignature(boolean ownersSignature) {
		this.ownersSignature = ownersSignature;
		log.trace("Set owners signature to = " + ownersSignature);
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

	@Override
	public boolean validate() {
		log.trace("Start validating Registration object.");
		Preconditions.checkState(!(getEngineNumber() == null),
				"The engine number is not valid!");
		Preconditions.checkState(!(getWarrantyType() == null),
				"The warranty type is not valid!");
		Preconditions.checkState(
				!(getDealerLogin().isEmpty() || getDealerLogin() == null),
				"The dealer login is not valid!");
		Preconditions.checkState(!(getCustomer().validate()),
				"The customer is not valid!");
		Preconditions.checkState(!(getDateSold() == null),
				"The date sold is not valid!");
		Preconditions.checkState(!(getDateRegistered() == null),
				"The date registered is not valid!");
		Preconditions.checkState(!(getDateDelivered() == null),
				"The date delivered is not valid!");
		Preconditions.checkState(!(getWarrantyExpiration() == null),
				"The warranty expiration date is not valid!");
		Preconditions.checkState(!(getUnitSurvey() == null),
				"The unit survey is not valid!");
		Preconditions.checkState(!(isUnitOperation() == false),
				"The unit operation can not be false!");
		Preconditions.checkState(!(isUnitMaintenance() == false),
				"The unit maintenance can not be false!");
		Preconditions.checkState(!(isSafetyFeatures() == false),
				"The safety features can not be false!");
		Preconditions.checkState(!(isWarrantyPolicy() == false),
				"The warranty policy can not be false!");
		Preconditions.checkState(!(isOwnersSignature() == false),
				"The owners signature can not be false!");
		log.trace("Registration object validated.");
		return true;
	}
}

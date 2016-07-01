package ua.partner.suzuki.domain;

enum WarrantyType {
	PRIVATE, COMMERCE, SPORT, DEMO
}

public class OBMRegistration {

	public OBMRegistration(String inputEngineNumber, String usage,
			String dealer, String dateSold, String dateRegistered,
			String dateDelivered) {
		
	}
	
	private String engineNumber;
	private String usageType;
	private OBM obm;
	private String dealer;
	private String dateSold;
	private String dateRegistered;
	private String dateDelivered;
	private String warrantyExpiration;
	private int penalty;
	private String warrantyCampaign;

	public String getEngineNumber() {
		return engineNumber;
	}
	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
	}
	
	public String getUsageType() {
		return usageType;
	}
	public void setUsageType(String usageType) {
		this.usageType = usageType;
	}

	public OBM getObm() {
		return obm;
	}
	public void setObm(OBM obm) {
		this.obm = obm;
	}

	public String getDealer() {
		return dealer;
	}
	public void setDealer(String dealer) {
		this.dealer = dealer;
	}

	public String getDateSold() {
		return dateSold;
	}

	public void setDateSold(String dateSold) {
		this.dateSold = dateSold;
	}

	public String getDateRegistered() {
		return dateRegistered;
	}

	public void setDateRegistered(String dateRegistered) {
		this.dateRegistered = dateRegistered;
	}

	public String getDateDelivered() {
		return dateDelivered;
	}

	public void setDateDelivered(String dateDelivered) {
		this.dateDelivered = dateDelivered;
	}

	public String getWarrantyExpiration() {
		return warrantyExpiration;
	}

	public void setWarrantyExpiration(String warrantyExpiration) {
		this.warrantyExpiration = warrantyExpiration;
	}

	public int getPenalty() {
		return penalty;
	}

	public void setPenalty(int penalty) {
		this.penalty = penalty;
	}

	public String getWarrantyCampaign() {
		return warrantyCampaign;
	}

	public void setWarrantyCampaign(String warrantyCampaign) {
		this.warrantyCampaign = warrantyCampaign;
	}
}

package ua.partner.suzuki.domain.obm.campaign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import ua.partner.suzuki.domain.EngineNumberIdentifiable;
import ua.partner.suzuki.domain.Validatable;

public class OBMCampaign implements EngineNumberIdentifiable<String>, Validatable {

	private Integer id;
	private String campaignNumber;
	private String engineNumber;
	private CampaignStatus campaignStatus;

	private static Logger log = LoggerFactory.getLogger(OBMCampaign.class);

	public OBMCampaign() {

	}

	public OBMCampaign(String campaignNumber, String engineNumber,
			CampaignStatus campaignStatus) {
		this.campaignNumber = campaignNumber;
		this.engineNumber = engineNumber;
		this.campaignStatus = campaignStatus;
		log.trace("Created OBMCampaign: engineNumber = " + engineNumber
				+ " , campaignNumber = " + campaignNumber);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
		log.trace("Set id to = " + id);
	}

	public String getCampaignNumber() {
		return campaignNumber;
	}

	public void setCampaignNumber(String campaignNumber) {
		this.campaignNumber = campaignNumber;
		log.trace("Set campaignNumber to = " + campaignNumber);
	}

	public String getEngineNumber() {
		return engineNumber;
	}

	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
		log.trace("Set engineNumber to = " + engineNumber);
	}

	public CampaignStatus getCampaignStatus() {
		return campaignStatus;
	}

	public void setCampaignStatus(CampaignStatus campaignStatus) {
		this.campaignStatus = campaignStatus;
		log.trace("Set campaignStatus to = " + campaignStatus);
	}

	@Override
	public String toString() {
		return "OBMCampaign [id=" + id + ", campaignNumber=" + campaignNumber
				+ ", engineNumber=" + engineNumber + ", campaignStatus="
				+ campaignStatus.toString() + "]";
	}

	@Override
	public boolean validate() {
		log.trace("Start validating OBMCampaign object.");
		Preconditions.checkState(
				!(getCampaignNumber().isEmpty() || getCampaignNumber() == null),
				"The street name is not valid!");
		Preconditions.checkState(
				!(getEngineNumber().isEmpty() || getEngineNumber() == null),
				"The street name is not valid!");
		Preconditions.checkState(!(getCampaignStatus() == null),
				"The street name is not valid!");
		log.trace("Campaign object validated.");
		return true;
	}
}

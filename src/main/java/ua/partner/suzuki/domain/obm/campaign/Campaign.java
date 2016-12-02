package ua.partner.suzuki.domain.obm.campaign;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import ua.partner.suzuki.domain.Validatable;

public class Campaign implements Validatable {

	private String campaignNo;
	private String description;
	private CampaignType campaignType;
	private String campaignFile;
	private Date campaignDate;
	private List<String> engineNumberList;
	private String engineNumber;
	private boolean inspection;
	private boolean repair;
	private String labourRate;
	private CampaignStatus campaignStatus;

	private static Logger log = LoggerFactory.getLogger(Campaign.class);

	public Campaign() {

	}

	public Campaign(String campaignNo, String description,
			CampaignType campaignType, String campaignFile, Date campaignDate,
			List<String> engineNumberList, boolean inspection, boolean repair,
			String labourRate) {
		super();
		this.campaignNo = campaignNo;
		this.description = description;
		this.campaignType = campaignType;
		this.campaignFile = campaignFile;
		this.campaignDate = campaignDate;
		this.engineNumberList = engineNumberList;
		this.inspection = inspection;
		this.repair = repair;
		this.labourRate = labourRate;
	}

	public String getCampaignNo() {
		return campaignNo;
	}

	public void setCampaignNumber(String campaignNo) {
		this.campaignNo = campaignNo;
		log.trace("Set campaignNo to = " + campaignNo);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		log.trace("Set description to = " + description);
	}

	public CampaignType getCampaignType() {
		return campaignType;
	}

	public void setCampaignType(CampaignType campaignType) {
		this.campaignType = campaignType;
		log.trace("Set campaignType to = " + campaignType);
	}

	public String getCampaignFile() {
		return campaignFile;
	}

	public void setCampaignFile(String campaignFile) {
		this.campaignFile = campaignFile;
		log.trace("Set campaignFile to = " + campaignFile);
	}

	public Date getCampaignDate() {
		return campaignDate;
	}

	public void setCampaignDate(Date campaignDate) {
		this.campaignDate = campaignDate;
		log.trace("Set campaignDate to = " + campaignDate);
	}

	public List<String> getEngineNumberList() {
		return engineNumberList;
	}

	public void setEngineNumberList(List<String> engineNumberList) {
		this.engineNumberList = engineNumberList;
		log.trace("Set engineNumberList to = " + engineNumberList.toString());
	}

	public String getEngineNumber() {
		return engineNumber;
	}

	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
		log.trace("Set engineNumber to = " + engineNumber);
	}

	public boolean isInspection() {
		return inspection;
	}

	public void setInspection(boolean inspection) {
		this.inspection = inspection;
		log.trace("Set inspection to = " + inspection);
	}

	public boolean isRepair() {
		return repair;
	}

	public void setRepair(boolean repair) {
		this.repair = repair;
		log.trace("Set repair to = " + repair);
	}

	public String getLabourRate() {
		return labourRate;
	}

	public void setLabourRate(String labourRate) {
		this.labourRate = labourRate;
		log.trace("Set labourRate to = " + labourRate);
	}

	public CampaignStatus getCampaignStatus() {
		return campaignStatus;
	}

	public void setCampaignStatus(CampaignStatus campaignStatus) {
		this.campaignStatus = campaignStatus;
		log.trace("Set campaignStatus to = " + campaignStatus);
	}

	@Override
	public boolean validate() {
		log.trace("Start validating Campaign object.");
		Preconditions.checkState(
				!(getCampaignNo().isEmpty() || getCampaignNo() == null),
				"The street name is not valid!");
		Preconditions.checkState(
				!(getDescription().isEmpty() || getDescription() == null),
				"The street name is not valid!");
		Preconditions.checkState(!(getCampaignType() == null),
				"The street name is not valid!");
		Preconditions.checkState(
				!(getCampaignFile().isEmpty() || getCampaignFile() == null),
				"The street name is not valid!");
		Preconditions.checkState(!(getCampaignDate() == null),
				"The street name is not valid!");
		log.trace("Campaign object validated.");
		return true;
	}
}

package ua.partner.suzuki.domain.campaign;

import java.util.Date;
import java.util.List;

public class Campaign {

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

	public String getCampaignNo() {
		return campaignNo;
	}

	public void setCampaignNo(String campaignNo) {
		this.campaignNo = campaignNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CampaignType getCampaignType() {
		return campaignType;
	}

	public void setCampaignType(CampaignType campaignType) {
		this.campaignType = campaignType;
	}

	public String getCampaignFile() {
		return campaignFile;
	}

	public void setCampaignFile(String campaignFile) {
		this.campaignFile = campaignFile;
	}

	public Date getCampaignDate() {
		return campaignDate;
	}

	public void setCampaignDate(Date campaignDate) {
		this.campaignDate = campaignDate;
	}

	public List<String> getEngineNumberList() {
		return engineNumberList;
	}

	public void setEngineNumberList(List<String> engineNumberList) {
		this.engineNumberList = engineNumberList;
	}

	public String getEngineNumber() {
		return engineNumber;
	}

	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
	}

	public boolean isInspection() {
		return inspection;
	}

	public void setInspection(boolean inspection) {
		this.inspection = inspection;
	}

	public boolean isRepair() {
		return repair;
	}

	public void setRepair(boolean repair) {
		this.repair = repair;
	}

	public String getLabourRate() {
		return labourRate;
	}

	public void setLabourRate(String labourRate) {
		this.labourRate = labourRate;
	}

	public CampaignStatus getCampaignStatus() {
		return campaignStatus;
	}

	public void setCampaignStatus(CampaignStatus campaignStatus) {
		this.campaignStatus = campaignStatus;
	}
}

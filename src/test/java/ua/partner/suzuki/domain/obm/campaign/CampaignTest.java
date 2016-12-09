package ua.partner.suzuki.domain.obm.campaign;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;

public class CampaignTest {

	@Test
	public void test_type() throws Exception {
		assertNotNull(Campaign.class);
	}

	private static final String campaignNumber = "RC1016";
	private static final String description = "test";
	private static final CampaignType campaignType = CampaignType.REGULAR_CAMPAIGN;
	private static final String campaignFile = "file";;
	private static final Date campaignDate = new Date();
	private static final List<String> engineNumberList = Arrays
			.asList("02002F-112233");
	private static final boolean inspection = true;
	private static final boolean repair = true;
	private static final Integer labourRate = 4;

	private Campaign campaign = new Campaign(campaignNumber, description,
			campaignType, campaignFile, campaignDate,
			engineNumberList, inspection, repair,
			labourRate);

	@Test
	public void test_instantiation() throws Exception {
		assertNotNull(campaign);
	}

	@Test
	public void campaign() throws Exception{
		assertTrue(campaign.validate());
	}
}

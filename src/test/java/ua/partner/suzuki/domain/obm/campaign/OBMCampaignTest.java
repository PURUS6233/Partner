package ua.partner.suzuki.domain.obm.campaign;

import static org.junit.Assert.*;

import org.junit.Test;

public class OBMCampaignTest {

	@Test
	public void test_type() throws Exception {
		assertNotNull(OBMCampaign.class);
	}

	private static final String campaignNumber = "RC1016";
	private static final String engineNumber = "02003F-001122";
	private static final CampaignStatus campaignStatus = CampaignStatus.NOT_DONE;

	private OBMCampaign obmCampaign = new OBMCampaign(campaignNumber,
			engineNumber, campaignStatus);

	@Test
	public void test_instantiation() throws Exception {
		assertNotNull(obmCampaign);
	}

	@Test
	public void obmCampaign() throws Exception {
		assertTrue(obmCampaign.validate());
	}
}

package ua.partner.suzuki.service.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ua.partner.suzuki.dao.CampaignDao;
import ua.partner.suzuki.dao.DAOException;
import ua.partner.suzuki.dao.DaoFactory;
import ua.partner.suzuki.domain.obm.campaign.Campaign;
import ua.partner.suzuki.domain.obm.campaign.CampaignStatus;
import ua.partner.suzuki.domain.obm.campaign.CampaignType;
import ua.partner.suzuki.domain.obm.campaign.OBMCampaign;
import ua.partner.suzuki.service.CampaignService;
import ua.partner.suzuki.service.ServiceException;

public class CampaignServiceImplTest {

	@Test
	public void test_type() throws Exception {
		assertNotNull(CampaignServiceImpl.class);
	}

	private static final String campaignNumber = "RC1016";
	private static final String description = "test";
	private static final CampaignType campaignType = CampaignType.REGULAR_CAMPAIGN;
	private static final String campaignFile = "file";;
	private static final Date campaignDate = new Date();
	private static final List<String> engineNumberList = Arrays
			.asList("02002F-112233", "02002F-777777");
	private static final boolean inspection = true;
	private static final boolean repair = true;
	private static final Integer labourRate = 4;

	private Campaign campaign = new Campaign(campaignNumber, description,
			campaignType, campaignFile, campaignDate, engineNumberList,
			inspection, repair, labourRate);
	
	private final List<Campaign> campaigns = Arrays
			.asList(campaign);
	
	private static final String engineNumber = "02002F-112233";
	private static final CampaignStatus campaignStatus = CampaignStatus.DONE;
	
	private OBMCampaign obmCampaign = new OBMCampaign(campaignNumber, engineNumber, campaignStatus);
	
	private final List<OBMCampaign> obmCampaigns = Arrays
			.asList(obmCampaign);

	@Mock
	private DaoFactory<Connection> daoFactory;

	@Mock
	private CampaignDao dao;

	@InjectMocks
	private CampaignService service = new CampaignServiceImpl();

	@Before
	public void setUp() throws DAOException {
		MockitoAnnotations.initMocks(this);
		when(daoFactory.getConnection()).thenReturn(null);
		when(daoFactory.getDao(null, Campaign.class)).thenReturn(dao);
		when(dao.add(campaign)).thenReturn(campaign);
		when(dao.getByEngineNumber(engineNumber)).thenReturn(campaigns);
		when(dao.getByCampaign(campaignNumber)).thenReturn(obmCampaigns);
		when(dao.getAll()).thenReturn(campaigns);
		when(dao.update(campaign)).thenReturn(campaign);
		when(dao.updateOBMCampaignStatus(obmCampaign)).thenReturn(obmCampaign);
		when(dao.delete(campaignNumber)).thenReturn(true);
	}

	@Test
	public void test_add() throws Exception {
		assertEquals(campaign, service.add(campaign));
		verify(dao).add(campaign);
	}
	
	@Test
	public void test_getByEngineNumber() throws DAOException, ServiceException {
		assertEquals(campaigns, service.getByEngineNumber(engineNumber));
		verify(dao).getByEngineNumber(engineNumber);
	}
	
	@Test
	public void test_getByCampaign() throws DAOException, ServiceException {
		assertEquals(obmCampaigns, service.getByCampaign(campaignNumber));
		verify(dao).getByCampaign(campaignNumber);
	}
	
	@Test
	public void test_getAll() throws DAOException, ServiceException {
		assertEquals(campaigns, service.getAll());
		verify(dao).getAll();
	}
	
	@Test
	public void test_updateCampaign() throws DAOException, ServiceException {
		campaign.setCampaignStatus(CampaignStatus.DONE);
		campaign.setCampaignType(CampaignType.SAFETY_CAMPAIGN);
		assertEquals(campaign, service.updateCampaign(campaign));
		verify(dao).update(campaign);
	}
	
	@Test
	public void test_updateStatus() throws DAOException, ServiceException {
		obmCampaign.setCampaignStatus(CampaignStatus.NOT_DONE);
		assertEquals(obmCampaign, service.updateOBMCampaignStatus(obmCampaign));
		verify(dao).updateOBMCampaignStatus(obmCampaign);
	}
	
	@Test
	public void test_delete() throws DAOException, ServiceException {
		assertEquals(true, service.remove(campaignNumber));
		verify(dao).delete(campaignNumber);
	}
}

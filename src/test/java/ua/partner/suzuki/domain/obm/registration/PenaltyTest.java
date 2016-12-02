package ua.partner.suzuki.domain.obm.registration;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import ua.partner.suzuki.domain.obm.registration.Penalty;

public class PenaltyTest {

	@Test
	public void test_type() throws Exception {
		assertNotNull(Penalty.class);
	}

	private static final Date currentDate = new Date();
	private static final Calendar c = Calendar.getInstance();

	@Test
	public void testCalculatePenalty_inTime_A() {
		c.setTime(currentDate);
		c.add(Calendar.HOUR, 3); // add 3 hours to registration date
		Penalty penalty = new Penalty(currentDate, c.getTime());
		assertEquals(0, penalty.calculatePenalty());
	}
	
	@Test
	public void testCalculatePenalty_inTime_B() {
		c.setTime(currentDate);
		c.add(Calendar.HOUR, 216); // add 9 days to registration date
		Penalty penalty = new Penalty(currentDate, c.getTime());
		assertEquals(0, penalty.calculatePenalty());
	}
	
	@Test
	public void testCalculatePenalty_afterTime() {
		c.setTime(currentDate);
		c.add(Calendar.HOUR, 600); // add 25 days to registration date
		Penalty penalty = new Penalty(c.getTime(), currentDate);
		assertEquals(15, penalty.calculatePenalty());
	}

}

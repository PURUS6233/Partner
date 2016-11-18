package ua.partner.suzuki.domain.obm;

import java.util.Date;

public class Penalty {

	private int penalty;
	private Date registered;
	private Date delivered;

	public Penalty(Date registered, Date delivered) {
		this.registered = registered;
		this.delivered = delivered;
	}

	public int getPenalty() {
		return penalty;
	}

	public void setPenalty(int penalty) {
		this.penalty = penalty;
	}

	private static final int registrPeriod = 10; // 10 days for registration of
													// sold unit
	private static final long millisecondToDays = 86400000; // number of
															// milliseconds/day;

	public int calculatePenalty() {
		int registrGap = (int) ((registered.getTime() - delivered.getTime())
				/ millisecondToDays);
		if (registrGap <= registrPeriod) {
			return 0;
		} else {
			return registrGap - 10;
		}
	}
}

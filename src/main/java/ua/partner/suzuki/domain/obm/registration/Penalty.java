package ua.partner.suzuki.domain.obm.registration;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.partner.suzuki.domain.Constants;
import ua.partner.suzuki.domain.obm.OBM;

public class Penalty {

	private int penalty;
	private Date registered;
	private Date delivered;

	private static Logger log = LoggerFactory.getLogger(OBM.class);

	public Penalty(Date registered, Date delivered) {
		this.registered = registered;
		this.delivered = delivered;
		log.trace("Created Penalty with: date registered = " + registered
				+ ", date delivered = " + delivered);
	}

	public int getPenalty() {
		return penalty;
	}

	public void setPenalty(int penalty) {
		this.penalty = penalty;
		log.trace("Set penalty to = " + penalty);
	}

	public int calculatePenalty() {
		log.trace("Starting penaly calculating");
		int registrGap = (int) ((registered.getTime() - delivered.getTime()) / Constants.MILLISECOND_TO_DAY);
		if (registrGap <= Constants.REGISTRATION_PERIOD) {
			log.trace("Penalty calculated");
			return 0;
		} else {
			log.trace("Penalty calculated");
			return registrGap - 10;
		}
	}
}

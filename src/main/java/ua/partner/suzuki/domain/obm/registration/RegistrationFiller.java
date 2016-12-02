package ua.partner.suzuki.domain.obm.registration;

import java.util.Calendar;

import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.partner.suzuki.domain.Constants;
import ua.partner.suzuki.domain.DataValidator;

@XmlRootElement
public class RegistrationFiller {

	private Registration registration;

	private static Logger log = LoggerFactory
			.getLogger(RegistrationFiller.class);

	public RegistrationFiller(Registration registration) {
		this.registration = registration;
		log.trace("Created RegistrationFiller for engine number = "
				+ registration.getEngineNumber());
	}

	private static final DataValidator VALIDATOR = new DataValidator();

	public Registration warrantyExpiration() {
		log.trace("Warranty expiration date serching");
		Calendar c = Calendar.getInstance();
		String[] number = VALIDATOR
				.divideEngineNumberToPrefixAndSerialNumber(registration
						.getEngineNumber());
		c.setTime(registration.getDateDelivered());
		log.trace("Warranty period determination based on warranty type & engine type!");
		
		if ((registration.getWarrantyType().equals(WarrantyType.PRIVATE) || registration
				.getWarrantyType().equals(WarrantyType.DEMO))
				&& VALIDATOR.checkWithRegExp(number[0],
						Constants.PREFIX_4_STROKE_PATTERN)) {
			c.add(Calendar.MONTH, Constants.WARRANTY_PERIOD_4_STROKE);
			registration.setWarrantyExpiration(c.getTime());
			log.trace("Warranty expiration date is = " + c.getTime());
			return registration;
		} else if ((registration.getWarrantyType().equals(WarrantyType.PRIVATE) || registration
				.getWarrantyType().equals(WarrantyType.DEMO))
				&& VALIDATOR.checkWithRegExp(number[0],
						Constants.PREFIX_2_STROKE_PATTERN)) {
			c.add(Calendar.MONTH, Constants.WARRANTY_PERIOD_2_STROKE_PLEASURE);
			registration.setWarrantyExpiration(c.getTime());
			log.trace("Warranty expiration date is = " + c.getTime());
			return registration;
		} else {
			c.add(Calendar.MONTH, Constants.WARRANTY_PERIOD_COMMERCIAL);
			registration.setWarrantyExpiration(c.getTime());
			log.trace("Warranty expiration date is = " + c.getTime());
			return registration;
		}
	}

	public Registration penalty() {
		log.trace("Penalty counting based of registered & delivered date.");
		Penalty penalty = new Penalty(registration.getDateRegistered(),
				registration.getDateDelivered());
		registration.setPenalty(penalty.calculatePenalty());
		log.trace("Penalty culculated!");
		return registration;
	}

	public Registration fill() {
		log.trace("Starting registration filler!");
		warrantyExpiration();
		penalty();
		log.trace("Registration succesfully filled with warranty expiration date and penalty!");
		return registration;
	}
}

package ua.partner.suzuki.domain.obm;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.partner.suzuki.domain.DomainException;
import ua.partner.suzuki.domain.DataValidator;
import ua.partner.suzuki.domain.EngineNoLoaderException;

public class OBMConverter {

	private static DataValidator validator = new DataValidator();
	private Collection<OBM> obms;
	private static Logger log = LoggerFactory.getLogger(OBM.class);

	public OBMConverter() {
	}

	public OBMConverter(Collection<String> engineNumbers)
			throws DomainException, EngineNoLoaderException {
		setObms(engineNumbers);
	}

	public Collection<OBM> getObms() {
		return obms;
	}

	public void setObms(Collection<String> engineNumbers)
			throws DomainException, EngineNoLoaderException {
		this.obms = createOBMFromEngineNumberList(engineNumbers);
	}

	public Collection<OBM> createOBMFromEngineNumberList(
			Collection<String> engineNumbers) throws DomainException,
			EngineNoLoaderException {

		Collection<OBM> localObms = new ArrayList<OBM>();

		for (String number : engineNumbers) {
			OBM obm = createOBMFromEngineNumber(number);
			localObms.add(obm);
		}
		return localObms;
	}

	public OBM createOBMFromEngineNumber(String engineNumber)
			throws DomainException {
		if (engineNumber.isEmpty()) {
			throw new DomainException("Engine Number can not be empty!");
		}
		String[] engineNumberData = validator
				.divideEngineNumberToPrefixAndSerialNumber(engineNumber);
		String modelYear = validator.findModelYear(engineNumberData[1]);
		Model model = Model.modelFromPrefix(engineNumberData[0]);
		OBM obm = new OBM(engineNumber, modelYear, model);
		return obm;
	}
}

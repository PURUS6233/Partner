package ua.partner.suzuki.domain.obm;

import java.util.ArrayList;
import java.util.Collection;

import ua.partner.suzuki.domain.DomainException;
import ua.partner.suzuki.domain.EngineNoValidator;

public class OBMBuilder {

	public OBMBuilder(Collection<String> engineNumbers)
			throws DomainException {
		setObms(engineNumbers);
	}

	private Collection<OBM> obms;

	public Collection<OBM> getObms() {
		return obms;
	}

	public void setObms(Collection<String> engineNumbers)
			throws DomainException {
		this.obms = buildOBMFromEngineNumberList(engineNumbers);
	}

	public Collection<OBM> buildOBMFromEngineNumberList(
			Collection<String> engineNumbers)
			throws DomainException {
		Collection<OBM> localObms = new ArrayList<OBM>();
		for (String number : engineNumbers) {
			OBM obm = createOBMFromEngineNumber(number);
			localObms.add(obm);
		}
		return localObms;
	}
	
	public boolean checkExistance(String inputEngineNo) {// TODO

		return true;
	}
	
	public static OBM createOBMFromEngineNumber(String engineNumber) throws DomainException {
		
		EngineNoValidator validator = new EngineNoValidator();
		String[] engineNumberData = validator.divideEngineNumberToPrefixAndSerialNumber(engineNumber);
		String prefix = validator.checkPrefix(engineNumberData[0]);
		String serialNumber = validator.checkSerialNumber(engineNumberData[1]);
		String modelYear = validator.findModelYear(serialNumber);
		Model model = Model.modelFromPrefix(prefix);
		OBM obm = new OBM(engineNumber, modelYear, model);
		return obm;
	}
	
	public static OBM createOBMFromEngineNumber(String engineNumber, Status status) throws DomainException {
		
		EngineNoValidator validator = new EngineNoValidator();
		String[] engineNumberData = validator.divideEngineNumberToPrefixAndSerialNumber(engineNumber);
		String prefix = validator.checkPrefix(engineNumberData[0]);
		String serialNumber = validator.checkSerialNumber(engineNumberData[1]);
		String modelYear = validator.findModelYear(serialNumber);
		Model model = Model.modelFromPrefix(prefix);
		OBM obm = new OBM(engineNumber, modelYear, model, status);
		return obm;
	}
}

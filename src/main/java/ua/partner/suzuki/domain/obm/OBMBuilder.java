package ua.partner.suzuki.domain.obm;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

import ua.partner.suzuki.domain.DomainException;
import ua.partner.suzuki.domain.EngineNoValidator;

public class OBMBuilder {

	private Collection<OBM> obms;

	public Collection<OBM> getObms() {
		return obms;
	}

	public void setObms(String engineNumbers)
			throws DomainException, EngineNoLoaderException {
		this.obms = buildOBMFromEngineNumberList(engineNumbers);
	}
	
	public OBMBuilder(){
	}

	public OBMBuilder(String engineNumbers)
			throws DomainException, EngineNoLoaderException {
		setObms(engineNumbers);
	}
	
	public Collection<OBM> buildOBMFromEngineNumberList(
			String engineNumbers)
			throws DomainException, EngineNoLoaderException {
		InputStream stream = new ByteArrayInputStream(
				engineNumbers.getBytes(StandardCharsets.UTF_8));
		EngineNumbersLoader loader = new EngineNumbersLoader(stream);
		Collection<String> engineNumbersList = loader.getEngineNumbers();
		
		Collection<OBM> localObms = new ArrayList<OBM>();
		
		for (String number : engineNumbersList) {
			OBM obm = createOBMFromEngineNumber(number);
			localObms.add(obm);
		}
		return localObms;
	}
	
	public OBM createOBMFromEngineNumber(String engineNumber) throws DomainException {
		EngineNoValidator validator = new EngineNoValidator();
		String[] engineNumberData = validator.divideEngineNumberToPrefixAndSerialNumber(engineNumber);
		String prefix = validator.checkPrefix(engineNumberData[0]);
		String serialNumber = validator.checkSerialNumber(engineNumberData[1]);
		String modelYear = validator.findModelYear(serialNumber);
		Model model = Model.modelFromPrefix(prefix);
		OBM obm = new OBM(engineNumber, modelYear, model);
		return obm;
	}
}

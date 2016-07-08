package ua.partner.suzuki.domain;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

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
			OBM obm = new OBM(number);
			localObms.add(obm);
		}
		return localObms;
	}

	public static void main(String args[]) throws DomainException, EngineNoLoaderException {
		String source = "01504F-123456 00602F-123456 05003F-123456 15002F-123456 03005-123456";
		InputStream stream = new ByteArrayInputStream(
				source.getBytes(StandardCharsets.UTF_8));
		EngineNumbersLoader loader = new EngineNumbersLoader(stream);
		Collection<String> engineNumbers = loader.getEngineNumbers();
		OBMBuilder builder = new OBMBuilder(engineNumbers);
		Collection<OBM> obms = builder.getObms();

		OBM obm1 = new OBM("05003F-123456");
		
		OBM obm2 = new OBM("15002F", "923456", "APPROVED");

		System.out.println(obm1.toString());
		System.out.println();
		System.out.println(obm2.toString());
		System.out.println();
		for (OBM item : obms) {
			System.out.println(item.toString());
			System.out.println();
		}

	}
}

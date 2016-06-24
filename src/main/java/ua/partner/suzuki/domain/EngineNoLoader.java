package ua.partner.suzuki.domain;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Scanner;

import ua.partner.suzuki.exceptions.EngineNoLoaderException;

public class EngineNoLoader {

	private String prefix;
	private String serialNumber;
	private String engineNumber;
	private Collection<String> engineNumbers;
	private Scanner sourceSc;
	
	/**
	 * This constructor is must be used when receiving data from UI Forms.
	 * 
	 * @param prefix
	 *            First 5 (for 2stroke engines) or 6 (for 4stroke engines) chars
	 *            of engine number. Prefix describes Model (Horse Power and for
	 *            some engines it can describe production year). It consist from
	 *            literals and numbers.
	 * @param serialNumber
	 *            Second 6 chars of engine number. It is identical serial number
	 *            of the engine. First 2 digits describe production year. It
	 *            consist only from numbers.
	 * @throws EngineNoLoaderException
	 *             Exception is throws when incorrect number is putting into the
	 *             constructor
	 * 
	 */

	public EngineNoLoader(String prefix, String serialNumber)
			throws EngineNoLoaderException {
		setEngineNumber(prefix, serialNumber);
	}

	/**
	 * This constructor is must be used when receiving multiply entry data.
	 * 
	 * @param EngineNumber
	 *            Engine number consist from 2 parts divided by "-". First part
	 *            is "prefix" and the second part is "serial number".
	 * @throws EngineNoLoaderException
	 *             Exception is throws when incorrect number is putting into the
	 *             constructor
	 */
	
	public EngineNoLoader(InputStream inStream) throws EngineNoLoaderException {
		this.sourceSc = sourceRemake(inStream);
	}
	
	public Scanner getSourceSc() {
		return sourceSc;
	}

	private Scanner sourceRemake(InputStream inStream) {
		Scanner sc = new Scanner(inStream,
				StandardCharsets.UTF_8.name());
		return sc;
	}
	
	

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getEngineNumber() {
		return engineNumber;
	}

	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
	}

	public Collection<String> getEngineNumbers() {
		return engineNumbers;
	}

	public void setEngineNumbers(Collection<String> engineNumbers) {
		this.engineNumbers = engineNumbers;
	}
}

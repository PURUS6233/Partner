package ua.partner.suzuki.domain;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import com.google.common.base.Preconditions;

import ua.partner.suzuki.exceptions.EngineNoLoaderException;

public class EngineNumbersLoader {

	private Collection<String> engineNumbers;
	private Scanner sourceSc;

	/**
	 * This constructor must be used when receiving multiply entry data from UI
	 * multiply form.
	 * 
	 * @param inStream
	 *            Represent the source data.
	 * @throws EngineNoLoaderException
	 *             Exception is throws when incorrect source is putting into the
	 *             constructor
	 */

	public EngineNumbersLoader(InputStream inStream) throws EngineNoLoaderException {
		this.sourceSc = sourceRemake(inStream);
		setEngineNumbers();
	}

	public Scanner getSourceSc() {
		return sourceSc;
	}

	private Scanner sourceRemake(InputStream inStream) {
		Scanner sc = new Scanner(inStream, StandardCharsets.UTF_8.name());
		return sc;
	}

	public Collection<String> getEngineNumbers() {
		return engineNumbers;
	}

	public void setEngineNumbers() {
		Collection<String> engineNumbers = engineNoLoader(getSourceSc());
		this.engineNumbers = engineNumbers;
	}

	EngineNoValidator validator = new EngineNoValidator();

	private static final String ENGINE_NUMBER_PATTERN = "^\\d\\d\\d\\d\\d(K|P|F)?-\\d\\d\\d\\d\\d\\d$";
	private static final String WORDS_DELIMITERS_2_SKIP_REGEX = "[\\s+.!?,]";

	public Collection<String> engineNoLoader(Scanner sourceSc) {
		Collection<String> engineNumberList = new ArrayList<>();
		boolean valid;
		validator.setPatternExpresion(ENGINE_NUMBER_PATTERN);
		while (sourceSc.hasNext()) {
			String word = sourceSc.next().replaceAll(
					WORDS_DELIMITERS_2_SKIP_REGEX, "");
			valid = validator.checkWithRegExp(word);
			if (valid) {
				engineNumberList.add(word);
			}
		}
		Preconditions.checkArgument(!blankInputTermination(engineNumberList),
				"No valid Engine Numbers. Program exit.");
		return engineNumberList;
	}

	public boolean blankInputTermination(Collection<String> list) {
		if (list.isEmpty()) {
			return true;
		}
		return false;
	}

}

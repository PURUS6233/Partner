package ua.partner.suzuki.domain.obm;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.partner.suzuki.domain.EngineNoValidator;
import com.google.common.base.Preconditions;

public class EngineNumbersLoader {

	private Logger logger = LoggerFactory.getLogger(getClass()); //TODO
	private Collection<String> engineNumbers;
	private Scanner sourceSc;

	/**
	 * This constructor must be used when receiving multiply entry data from UI
	 * multiply form.
	 * 
	 * @param inStream
	 *            Represent the source data.
	 * @throws EngineNoLoaderException
	 *             Exception is thrown when incorrect source attached into the
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
				"Input contains no valid Engine Numbers.");
		
		return engineNumberList;
	}

	public boolean blankInputTermination(Collection<String> list) {
		if (list.isEmpty()) {
			return true;
		}
		return false;	
	}
}

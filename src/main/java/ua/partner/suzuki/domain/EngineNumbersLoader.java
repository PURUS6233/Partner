package ua.partner.suzuki.domain;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

public class EngineNumbersLoader {

	private Logger log = LoggerFactory.getLogger(getClass());
	private Scanner sourceSc;

	private static DataValidator validator = new DataValidator();

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

	public EngineNumbersLoader(InputStream inStream) {
		this.sourceSc = sourceRemake(inStream);
		setEngineNumbers();
	}

	public Scanner getSourceSc() {
		return sourceSc;
	}

	private Scanner sourceRemake(InputStream inStream) {
		Scanner sc = new Scanner(inStream, StandardCharsets.UTF_8.name());
		log.info("Remaking InputStream to Scanner", getClass().getSimpleName());
		return sc;
	}

	private Collection<String> engineNumbers = new ArrayList<>();
	private Collection<String> engineNumbers_wrong = new ArrayList<>();

	public Collection<String> getEngineNumbers_wrong() {
		return engineNumbers_wrong;
	}

	public Collection<String> engineNoLoader(Scanner sourceSc) {
		String engineNumber = "";
		while (sourceSc.hasNext()) {
			try {
				engineNumber = sourceSc.next().replaceAll(
						Constants.WORDS_DELIMITERS_2_SKIP_REGEX, "");
				if (validator.checkWithRegExp(engineNumber,
						Constants.ENGINE_NUMBER_4_STROKE_PATTERN)
						|| validator.checkWithRegExp(engineNumber,
								Constants.ENGINE_NUMBER_2_STROKE_PATTERN)) {
					engineNumbers.add(engineNumber);
				} else {
					log.error("Mistake in engine number" + engineNumber + "!");
					engineNumbers_wrong.add(engineNumber);
				}
			} catch (Exception e) {
				log.error("Problem occured while loading engine numbers", e);
			}
		}
		Preconditions.checkArgument(!engineNumbers.isEmpty(),
				"Input contains no valid Engine Numbers.");
		log.info("Engine numbers loaded from Source", getClass()
				.getSimpleName());
		return engineNumbers;
	}

	public Collection<String> getEngineNumbers() {
		return engineNumbers;
	}

	public void setEngineNumbers() {
		this.engineNumbers = engineNoLoader(getSourceSc());
	}

}

package ua.partner.suzuki.domain;

public abstract class Constants {

//	/** Engine number prefix pattern */
//	public static final String PREFIX_PATTERN = "^\\d{5}(K|P|F)?$";
//
//	/** Engine number serial_number pattern */
//	public static final String SERIAL_NUMBER_PATTERN = "^\\d{5}\\d?$";

	public static final String POSTCODE_PATTERN = "^\\d{4,5}$";
	public static final String PHONE_PATTERN = "^\\D\\d{8,13}$";
	public static final String EMAIL_PATTERN = "^.+@\\w+\\.\\w+$";
	
	public static final String ENGINE_NUMBER_PATTERN = "^\\d{5}(K|P|F|Z)?-\\d{6}$";
	
	public static final String ENGINE_NUMBER_2_STROKE_PATTERN = "^\\d{5}(K|P)?-\\d{5}\\d?$";
	
	public static final String ENGINE_NUMBER_4_STROKE_PATTERN = "^\\d{5}(F|Z)?-\\d{6}$";
	
	/** Engine number prefix pattern for 4-stroke units*/
	public static final String PREFIX_4_STROKE_PATTERN = "^\\d{5}(F|Z)$";
	
	/** Engine number prefix pattern for 2-stroke units*/
	public static final String PREFIX_2_STROKE_PATTERN = "^\\d{5}(K|P)?$";
	
	public static final String WORDS_DELIMITERS_2_SKIP_REGEX = "[\\s+.!?,]";

	/**period for registration of sold unit*/
	public static final int REGISTRATION_PERIOD = 10;
	
	/**number of milliseconds_per_day*/
	public static final long MILLISECOND_TO_DAY = 86400000;

	/**Warranty period in months for 4-stroke units*/
	public static final int WARRANTY_PERIOD_4_STROKE = 36;
	
	/**Warranty period in months for 2-stroke units*/
	public static final int WARRANTY_PERIOD_2_STROKE_PLEASURE = 24;

	/**Warranty period in months for 2-stroke and 4-stroke units*/
	public static final int WARRANTY_PERIOD_COMMERCIAL = 12; 
}

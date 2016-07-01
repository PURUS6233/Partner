package ua.partner.suzuki.exceptions;

@SuppressWarnings("serial")
public class EngineNoValidatorException extends Exception {
			
	public EngineNoValidatorException(Throwable e) {
		initCause(e);
	}
}

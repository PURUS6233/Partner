package ua.partner.suzuki.exceptions;


@SuppressWarnings("serial")
public class ModelException extends Exception {
		
	public ModelException(Throwable e) {
		initCause(e);
	}
}

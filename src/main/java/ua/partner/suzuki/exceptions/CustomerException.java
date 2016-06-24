package ua.partner.suzuki.exceptions;

@SuppressWarnings("serial")
public class CustomerException extends Exception {
	
	public CustomerException(Throwable e) {
		initCause(e);
	}
}
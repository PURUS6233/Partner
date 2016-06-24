package ua.partner.suzuki.exceptions;

@SuppressWarnings("serial")
public class AddressException extends Exception {
	
	public AddressException(Throwable e) { 
        initCause(e);
    }
}

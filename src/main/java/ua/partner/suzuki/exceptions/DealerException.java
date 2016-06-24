package ua.partner.suzuki.exceptions;

@SuppressWarnings("serial")
public class DealerException extends Exception {
	
	public DealerException(Throwable e) { 
        initCause(e);
    }
}

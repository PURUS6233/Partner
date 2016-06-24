package ua.partner.suzuki.exceptions;

@SuppressWarnings("serial")
public class EngineNoLoaderException extends Exception {
	
	public EngineNoLoaderException(Throwable e) { 
        initCause(e);
    }
}
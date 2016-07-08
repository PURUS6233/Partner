package ua.partner.suzuki.domain;

@SuppressWarnings("serial")
public class EngineNoLoaderException extends Exception {
	
	public EngineNoLoaderException(Throwable e) { 
        initCause(e);
    }
}
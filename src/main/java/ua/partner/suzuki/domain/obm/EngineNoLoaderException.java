package ua.partner.suzuki.domain.obm;

@SuppressWarnings("serial")
public class EngineNoLoaderException extends Exception {
	
	public EngineNoLoaderException(Throwable e) { 
        initCause(e);
    }
}
package ua.partner.suzuki.domain;

@SuppressWarnings("serial")
public class EngineNoLoaderException extends Exception {
	
	public EngineNoLoaderException(){
		super();
	}
		
	public EngineNoLoaderException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public EngineNoLoaderException(String message){
		super(message);
	}
}
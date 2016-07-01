package ua.partner.suzuki.exceptions;

@SuppressWarnings("serial")
public class OBMException extends Exception {
		
	public OBMException(Throwable e) {
		initCause(e);
	}
	
	public OBMException(String str){
		super(str);
	}
}


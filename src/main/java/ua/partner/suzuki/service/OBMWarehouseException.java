package ua.partner.suzuki.service;

@SuppressWarnings("serial")
public class OBMWarehouseException extends Exception {
	
	public OBMWarehouseException(){
		super();
	}
	
	public OBMWarehouseException(String message, Throwable cause){
		super(message, cause);
	}
	
	public OBMWarehouseException(String message){
		super(message);
	}

}

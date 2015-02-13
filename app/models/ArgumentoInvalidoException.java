package models;

public class ArgumentoInvalidoException extends Exception {

	private static final long serialVersionUID = 1L;
	private String msg;
	
	public ArgumentoInvalidoException(String msg) {  
		super(msg);
        this.msg = msg;
	}
	
    public String getMessage() {
        return msg;
    }
}

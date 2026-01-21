package in.co.rays.proj3.exception;

import java.util.Arrays;

/**
 * @author Aniket Rajput
 */
public class DatabaseException extends ApplicationException{
	
	private static final long serialVersionUID = 1L;

	public DatabaseException(String msg){
		super(msg);
	}

	@Override
	public String toString() {
		return  getClass().toString();
	}
	
	
}

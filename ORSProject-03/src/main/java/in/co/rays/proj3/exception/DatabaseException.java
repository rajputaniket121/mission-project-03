package in.co.rays.proj3.exception;

/**
 * @author Aniket Rajput
 */
public class DatabaseException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public DatabaseException(String msg){
		super(msg);
	}
}

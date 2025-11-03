package in.co.rays.proj3.exception;

/**
 * @author Anshul Prajapati
 */
public class DatabaseException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public DatabaseException(String msg){
		super(msg);
	}
}

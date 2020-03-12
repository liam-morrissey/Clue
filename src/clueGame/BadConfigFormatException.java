package clueGame;
/**
 * 
 * @author Liam Morrissey
 * @author Brandt Ross
 *
 */

public class BadConfigFormatException extends Exception {

	public BadConfigFormatException() {
		super();
	}

	public BadConfigFormatException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BadConfigFormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public BadConfigFormatException(String message) {
		super(message);
	}

	public BadConfigFormatException(Throwable cause) {
		super(cause);
	}

}

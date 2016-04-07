package core;

/**
 * Exception raised by the {@link Segment} constructor to avoid nasty bugs
 */
public class InvalidSegmentException extends RuntimeException {
	public InvalidSegmentException(String message) {
		super(message);
	}
}

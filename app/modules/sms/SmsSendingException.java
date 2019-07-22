package modules.sms;

/**
 * method.
 */
public class SmsSendingException extends RuntimeException {

    public SmsSendingException(String message) {
        super(message);
    }

    public SmsSendingException(String message, Throwable cause) {
        super(message, cause);
    }
}

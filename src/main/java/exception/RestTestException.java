package exception;

/**
 * User: mark
 * Date: 14-4-27
 * Time: 下午3:06
 */

public class RestTestException extends Exception {
    private final int statusCode;


    public int getStatusCode() {
        return statusCode;
    }

    public RestTestException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

}
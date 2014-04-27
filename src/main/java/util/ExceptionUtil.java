package util;

import exception.RestTestException;
import org.apache.commons.httpclient.HttpMethodBase;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpStatusCodeException;

/**
 * Created with IntelliJ IDEA @ 8/12/11 4:03 PM
 * All rights reserved.
 *
 * @author wangy23
 */
public class ExceptionUtil {


    public static RestTestException getRestTestException(HttpStatusCodeException e) {

        int statusCode = e.getStatusCode().value();
        MediaType contentType = e.getResponseHeaders().getContentType();
        return getRestTestException(e.getResponseBodyAsString(), statusCode);

    }

    public static RestTestException getRestTestException(HttpMethodBase method) {
        // TODO, to be implemented...
        return new RestTestException("", 0);

    }

    public static RestTestException getRestTestException(String response, int statusCode) {
        return new RestTestException(response, statusCode);

    }

}

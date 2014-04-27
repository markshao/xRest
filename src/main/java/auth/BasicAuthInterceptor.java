package auth;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

/**
 * User: mark
 * Date: 14-4-27
 * Time: 下午2:48
 */

public class BasicAuthInterceptor extends NamePasswordInterceptor {
    @Override
    public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
        request.setHeader(
                new BasicScheme().authenticate(new UsernamePasswordCredentials(userName, password), request, null));
    }
}

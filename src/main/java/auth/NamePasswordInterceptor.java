package auth;

import org.apache.http.HttpRequestInterceptor;

/**
 * User: mark
 * Date: 14-4-27
 * Time: 下午2:48
 */

public abstract class NamePasswordInterceptor implements HttpRequestInterceptor {
    protected String userName;
    protected String password;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

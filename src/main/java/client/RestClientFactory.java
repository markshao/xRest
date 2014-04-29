package client;

import auth.BasicAuthInterceptor;
import auth.NamePasswordInterceptor;
import client.impl.RestClient;
import context.SpringContextUtil;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.client.HttpClient;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import util.Const;

/**
 * User: mark
 * Date: 14-4-29
 * Time: 下午8:33
 */

public class RestClientFactory {
    private static RestClientFactory INSTANCE = null;

    private static final String DEFAULT_HTTP_CLIENT4 = "defaultHttpClient4";

    private static IRestClient client = null;

    private RestClientFactory() {
        HttpRequestInterceptor user = createAuthInterceptor(Const.USERNAME, Const.PASSWORD);
        client = createClient((HttpClient) SpringContextUtil.getApplicationContextJava().getBean(DEFAULT_HTTP_CLIENT4, user));
    }

    public static RestClientFactory getInstance() {
        synchronized (RestClientFactory.class) {
            if (INSTANCE == null) {
                INSTANCE = new RestClientFactory();
            }
        }
        return INSTANCE;
    }

    // default the http client can only executed in a single thread.
    public IRestClient getRestClient() {
        return client;
    }


    public IRestClient createClient(String userName, String password) {
        HttpRequestInterceptor user = createAuthInterceptor(userName, password);
        HttpClient client = (HttpClient) SpringContextUtil.getApplicationContextJava().getBean(DEFAULT_HTTP_CLIENT4, user);
        return createClient(client);
    }

    public IRestClient createClient(HttpClient client) {
        RestClient restClient = new RestClient(new HttpComponentsClientHttpRequestFactory(client));

        restClient.addMessageConverter(new ResourceHttpMessageConverter());
        restClient.addMessageConverter(new StringHttpMessageConverter());

        // add the convertors for the rest client
//        RestJsonHttpMessageConverter jsonConverter = SpringContextUtil.getApplicationContextJava().getBean(RestJsonHttpMessageConverter.class);
//        restClient.addMessageConverter(jsonConverter);

        return restClient;
    }

    /**
     * The function will create a new interceptor for the http basic authentication
     *
     * @param userName
     * @param password
     * @return
     */

    private HttpRequestInterceptor createAuthInterceptor(String userName, String password) {
        NamePasswordInterceptor interceptor = new BasicAuthInterceptor();
        interceptor.setUserName(userName);
        interceptor.setPassword(password);
        return interceptor;
    }
}
package context;

import org.apache.http.HttpRequestInterceptor;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * User: mark
 * Date: 14-4-29
 * Time: 下午8:41
 */

@Configuration
public class ClientSpringConfiguration {
    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public org.apache.commons.httpclient.HttpClient httpClient() {
        return new org.apache.commons.httpclient.HttpClient();
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public org.apache.http.client.HttpClient defaultHttpClient4(HttpRequestInterceptor interceptor) {
        PoolingClientConnectionManager cm = new PoolingClientConnectionManager();
        cm.setMaxTotal(20);
        cm.setDefaultMaxPerRoute(10);
        DefaultHttpClient httpClient = new DefaultHttpClient(cm);
        httpClient.addRequestInterceptor(interceptor);

        return httpClient;
    }
}

package client.impl;

/**
 * User: mark
 * Date: 14-4-27
 * Time: 下午9:07
 */

import client.IRestClient;
import exception.RestTestException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import util.ExceptionUtil;
import util.HttpUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Set;

public class RestClient extends RestTemplate implements IRestClient {
    private Log logger = LogFactory.getLog(RestClient.class);

    public RestClient() {
        super();

        super.getMessageConverters().clear();
    }

    public RestClient(ClientHttpRequestFactory requestFactory) {
        super(requestFactory);

        super.getMessageConverters().clear();
    }

    /**
     * Add the converter to RestClient
     *
     * @param converter The converter are used to convert from and to HTTP requests and responses.
     */
    public void addMessageConverter(HttpMessageConverter<?> converter) {
        getMessageConverters().add(converter);
    }

    /* ========================================= GET ========================================= */

    /**
     * get a object by an accept header and the response type,
     *
     * @param uri             a String format uri
     * @param acceptMediaType expected content-type of the response
     * @param responseType    a null response type will result a NPE
     * @param <T>             response type for instantiate a java object through the response body
     * @return a java object of type T
     */
    public <T> T getObject(String uri, MediaType acceptMediaType, Class<T> responseType) throws RestTestException {
        HttpHeaders requestHeaders = new HttpHeaders();
        if (acceptMediaType != null) {
            requestHeaders.setAccept(Arrays.asList(acceptMediaType));
        }
        return getObject(uri, requestHeaders, responseType);
    }

    public <T> T getObject(String uri, HttpHeaders requestHeaders, Class<T> responseType) throws RestTestException {
        return exchangeObject(uri, requestHeaders, responseType, null, HttpMethod.GET).getBody();
    }

    public <T> ResponseEntity<T> getEntity(String uri, MediaType acceptMediaType, Class<T> responseType) throws RestTestException {
        HttpHeaders requestHeaders = new HttpHeaders();
        if (acceptMediaType != null) {
            requestHeaders.setAccept(Arrays.asList(acceptMediaType));
        }
        return getEntity(uri, requestHeaders, responseType);
    }


    public <T> ResponseEntity<T> getEntity(String uri, HttpHeaders requestHeaders, Class<T> responseType) throws RestTestException {
        return exchangeObject(uri, requestHeaders, responseType, null, HttpMethod.GET);
    }

    public <T> T getFeed(String uri, MediaType acceptMediaType, Class<T> responseType) throws RestTestException {
        HttpHeaders requestHeaders = new HttpHeaders();
        if (acceptMediaType != null) {
            requestHeaders.setAccept(Arrays.asList(acceptMediaType));
        }
        return getFeed(uri, requestHeaders, responseType);
    }

    public <T> T getFeed(String uri, HttpHeaders requestHeaders, Class<T> responseType) throws RestTestException {
        return exchangeObject(uri, requestHeaders, responseType, null, HttpMethod.GET).getBody();
    }

    /* ========================================= POST ========================================= */

    public <T> T postObject(String uri, MediaType contentType, Object request, MediaType acceptMediaType, Class<T> responseType) throws RestTestException {
        return postObject(uri, HttpUtil.setHttpHeaders(contentType, acceptMediaType), request, responseType);
    }


    public <T> T postObject(String uri, HttpHeaders requestHeaders, Object request, Class<T> responseType) throws RestTestException {
        return exchangeObject(uri, requestHeaders, responseType, request, HttpMethod.POST).getBody();
    }

    public <T> ResponseEntity<T> postForEntity(String uri, MediaType contentType, Object request, MediaType acceptMediaType, Class<T> responseType) throws RestTestException {
        return postForEntity(uri, HttpUtil.setHttpHeaders(contentType, acceptMediaType), request, responseType);
    }

    public <T> ResponseEntity<T> postForEntity(String uri, HttpHeaders requestHeaders, Object request, Class<T> responseType) throws RestTestException {
        return exchangeObject(uri, requestHeaders, responseType, request, HttpMethod.POST);
    }
    /* ========================================= PUT ========================================= */

    public <T> T putObject(String uri, MediaType contentType, Object request, MediaType acceptMediaType, Class<T> responseType) throws RestTestException {
        return putObject(uri, HttpUtil.setHttpHeaders(contentType, acceptMediaType), request, responseType);
    }

    public <T> T putObject(String uri, HttpHeaders requestHeaders, Object request, Class<T> responseType) throws RestTestException {
        return exchangeObject(uri, requestHeaders, responseType, request, HttpMethod.PUT).getBody();
    }

    @Override
    public <T> ResponseEntity<T> putForEntity(String uri, MediaType contentType, Object request, MediaType acceptMediaType, Class<T> responseType) throws RestTestException {
        // TODO Auto-generated method stub
        return putForEntity(uri, HttpUtil.setHttpHeaders(contentType, acceptMediaType), request, responseType);
    }

    public <T> ResponseEntity<T> putForEntity(String uri, HttpHeaders requestHeaders, Object request, Class<T> responseType) throws RestTestException {
        return exchangeObject(uri, requestHeaders, responseType, request, HttpMethod.PUT);
    }

    public int delete(String uri) throws RestTestException {
        try {
            delete(new URI(uri));
        } catch (HttpStatusCodeException e) {
            throw ExceptionUtil.getRestTestException(e);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return 204;

    }

    public Set<HttpMethod> option(String uri) throws RestTestException {
        try {
            return optionsForAllow(uri);
        } catch (HttpStatusCodeException e) {
            throw ExceptionUtil.getRestTestException(e);
        }
    }

    private <T, S> ResponseEntity<T> exchangeObject(String uri, HttpHeaders headers, Class<T> responseType, S data, HttpMethod method) throws RestTestException {
        return exchange(uri, headers, responseType, data, method);
    }


    /* ============================= private converter implementation for JSON ends============================= */

    private <T, S> T exchangeForBody(String uri, HttpHeaders requestHeader, Class<T> responseType, S data, HttpMethod method) throws RestTestException {
        return exchange(uri, requestHeader, responseType, data, method).getBody();
    }

    public <T, S> ResponseEntity<T> exchange(String uri, HttpHeaders requestHeader, Class<T> responseType, S data, HttpMethod method) throws RestTestException {
        try {
            HttpEntity<S> requestEntity;

            if (data != null)
                requestEntity = new HttpEntity<S>(data, requestHeader);
            else
                requestEntity = new HttpEntity<S>(requestHeader);

            return exchange(new URI(uri), method, requestEntity, responseType);

        } catch (HttpStatusCodeException e) {

            throw ExceptionUtil.getRestTestException(e);

        } catch (URISyntaxException e) {
            Assert.fail(e.getMessage());
        }
        return null;
    }
}

package client;

import exception.RestTestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Set;

/**
 * User: mark
 * Date: 14-4-27
 * Time: 下午9:05
 */

public interface IRestClient {
    public <T> T getObject(String uri, MediaType acceptMediaType, Class<T> responseType) throws RestTestException;

    public <T> T getObject(String uri, HttpHeaders requestHeaders, Class<T> responseType) throws RestTestException;

    public <T> ResponseEntity<T> getEntity(String uri, HttpHeaders requestHeaders, Class<T> responseType) throws RestTestException;

    public <T> ResponseEntity<T> getEntity(String uri, MediaType acceptMediaType, Class<T> responseType) throws RestTestException;

    public <T> T getFeed(String uri, MediaType acceptMediaType, Class<T> responseType) throws RestTestException;

    public <T> T getFeed(String uri, HttpHeaders requestHeaders, Class<T> responseType) throws RestTestException;

    public <T> T postObject(String uri, MediaType contentType, Object request, MediaType acceptMediaType, Class<T> responseType) throws RestTestException;

    public <T> T postObject(String uri, HttpHeaders requestHeaders, Object request, Class<T> responseType) throws RestTestException;

    public <T> ResponseEntity<T> postForEntity(String uri, HttpHeaders requestHeaders, Object request, Class<T> responseType) throws RestTestException;

    public <T> ResponseEntity<T> postForEntity(String uri, MediaType contentType, Object request, MediaType acceptMediaType, Class<T> responseType) throws RestTestException;

    public <T> T putObject(String uri, MediaType contentType, Object request, MediaType acceptMediaType, Class<T> responseType) throws RestTestException;

    public <T> T putObject(String uri, HttpHeaders requestHeaders, Object request, Class<T> responseType) throws RestTestException;

    public <T> ResponseEntity<T> putForEntity(String uri, HttpHeaders requestHeaders, Object request, Class<T> responseType) throws RestTestException;

    public <T> ResponseEntity<T> putForEntity(String uri, MediaType contentType, Object request, MediaType acceptMediaType, Class<T> responseType) throws RestTestException;

    public int delete(String uri) throws RestTestException;

    public Set<HttpMethod> option(String uri) throws RestTestException;

    public <T, S> ResponseEntity<T> exchange(String uri, HttpHeaders requestHeader, Class<T> responseType, S data, HttpMethod method) throws RestTestException;
}

package org.fsgroup.filestorage.client.web.vaadin.rest;

import org.fsgroup.filestorage.client.web.vaadin.service.Request;
import org.fsgroup.filestorage.client.web.vaadin.service.RequestResults;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

public class RestRequest<T> extends Request<T> {

    private final HttpMethod method;
    private final String url;
    private final MultiValueMap<String, String> headers;
    private final Map<String, Object> params;
    private Object body;

    public RestRequest(RequestResults<T> requestResults, HttpMethod method, String url) {
        super(requestResults);
        this.method = method;
        this.url = url;
        this.headers = new LinkedMultiValueMap<>();
        this.params = new HashMap<>();
    }

    public void addHeader(String key, String value) {
        headers.add(key, value);
    }

    public void addParameter(String key, Object value) {
        params.put(key, value);
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public MultiValueMap<String, String> getHeaders() {
        return headers;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public Object getBody() {
        return body;
    }
}

package org.fsgroup.filestorage.vaadin.rest;

import org.fsgroup.filestorage.vaadin.service.Request;
import org.fsgroup.filestorage.vaadin.service.RequestResults;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.util.Collections;

public class RestRequest<T> extends Request<T> {

    private final HttpMethod method;
    private final String url;
    private final HttpHeaders headers;
    private Object body;

    public RestRequest(RequestResults<T> requestResults, HttpMethod method, String url) {
        super(requestResults);
        this.method = method;
        this.url = url;
        this.headers = new HttpHeaders();
        addDefaultHeaders();
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

    public HttpHeaders getHeaders() {
        return headers;
    }

    public Object getBody() {
        return body;
    }

    private void addDefaultHeaders() {
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    }
}

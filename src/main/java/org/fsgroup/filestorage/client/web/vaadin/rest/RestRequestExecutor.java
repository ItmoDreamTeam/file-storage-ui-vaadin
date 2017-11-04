package org.fsgroup.filestorage.client.web.vaadin.rest;

import org.fsgroup.filestorage.client.web.vaadin.service.Request;
import org.fsgroup.filestorage.client.web.vaadin.service.RequestExecutor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class RestRequestExecutor implements RequestExecutor {

    @Override
    public <T> void execute(Request<T> request) {
        if (request instanceof RestRequest)
            execute((RestRequest<T>) request);
    }

    private <T> void execute(RestRequest<T> request) {
        RestResponseErrorHandler errorHandler = new RestResponseErrorHandler();
        RestTemplate restTemplate = buildRestTemplate(errorHandler);
        try {
            ResponseEntity<T> response = restTemplate.exchange(request.getUrl(), request.getMethod(),
                    new HttpEntity<>(request.getBody(), request.getHeaders()), request.getResponseType());
            if (errorHandler.errorOccurred()) request.fail(errorHandler.getErrorMessage());
            else request.success(response.getBody());
        } catch (RestClientException e) {
            e.printStackTrace();
            request.fail("Failed to connect to server");
        }
    }

    private RestTemplate buildRestTemplate(ResponseErrorHandler errorHandler) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(errorHandler);
        return restTemplate;
    }
}

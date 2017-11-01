package org.fsgroup.filestorage.client.web.vaadin.rest;

import org.fsgroup.filestorage.client.web.vaadin.service.Request;
import org.fsgroup.filestorage.client.web.vaadin.service.RequestExecutor;
import org.springframework.stereotype.Service;

@Service
public class HttpClientRestRequestExecutor implements RequestExecutor {

    @Override
    public <T> void execute(Request<T> request) {
        if (request instanceof RestRequest)
            execute((RestRequest<T>) request);
    }

    private <T> void execute(RestRequest<T> request) {
    }
}

package org.fsgroup.filestorage.client.web.vaadin.rest;

import org.fsgroup.filestorage.client.web.vaadin.security.UserCredentials;
import org.fsgroup.filestorage.client.web.vaadin.service.FileService;
import org.fsgroup.filestorage.client.web.vaadin.service.RequestExecutor;
import org.fsgroup.filestorage.client.web.vaadin.service.RequestResults;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RestFileService implements FileService {

    @Resource
    private RestApiUrls urls;

    @Resource
    private RestAuthorization auth;

    @Resource
    private RequestExecutor requestExecutor;

    @Override
    public void upload(RequestResults<?> requestResults, UserCredentials userCredentials) {
        RestRequest<?> request = new RestRequest<>(requestResults, HttpMethod.POST, urls.file(userCredentials.getUsername()));
        auth.addAuthHeader(request, userCredentials);
        requestExecutor.execute(request);
    }

    @Override
    public void download(RequestResults<?> requestResults, UserCredentials userCredentials, int fileId) {
        RestRequest<?> request = new RestRequest<>(requestResults, HttpMethod.GET, urls.file(userCredentials.getUsername(), fileId));
        auth.addAuthHeader(request, userCredentials);
        requestExecutor.execute(request);
    }

    @Override
    public void edit(RequestResults<?> requestResults, UserCredentials userCredentials, int fileId, String name) {
        RestRequest<?> request = new RestRequest<>(requestResults, HttpMethod.PUT, urls.file(userCredentials.getUsername(), fileId));
        request.addParameter("name", name);
        auth.addAuthHeader(request, userCredentials);
        requestExecutor.execute(request);
    }

    @Override
    public void delete(RequestResults<?> requestResults, UserCredentials userCredentials, int fileId) {
        RestRequest<?> request = new RestRequest<>(requestResults, HttpMethod.DELETE, urls.file(userCredentials.getUsername(), fileId));
        auth.addAuthHeader(request, userCredentials);
        requestExecutor.execute(request);
    }
}

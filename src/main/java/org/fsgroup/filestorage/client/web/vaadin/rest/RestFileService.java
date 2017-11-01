package org.fsgroup.filestorage.client.web.vaadin.rest;

import org.apache.log4j.Logger;
import org.fsgroup.filestorage.client.web.vaadin.auth.AuthenticationService;
import org.fsgroup.filestorage.client.web.vaadin.auth.AuthorizationService;
import org.fsgroup.filestorage.client.web.vaadin.auth.Credentials;
import org.fsgroup.filestorage.client.web.vaadin.service.FileService;
import org.fsgroup.filestorage.client.web.vaadin.service.RequestExecutor;
import org.fsgroup.filestorage.client.web.vaadin.service.RequestResults;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;
import java.io.File;

@Service
public class RestFileService implements FileService {

    private static final Logger log = Logger.getLogger(RestFileService.class);

    @Resource
    private RestApiUrls urls;

    @Resource
    private AuthenticationService authenticationService;

    @Resource
    private AuthorizationService authorizationService;

    @Resource
    private RequestExecutor requestExecutor;

    @Override
    public void upload(RequestResults<?> requestResults, File file) {
        Credentials credentials = authenticationService.getUserCredentials();
        log.info(String.format("Upload file, name=%s", file.getName()));
        RestRequest<?> request = new RestRequest<>(requestResults, HttpMethod.POST, urls.file(credentials.getUsername()));

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("file", new FileSystemResource(file));
        request.setBody(params);
        request.getHeaders().setContentType(MediaType.MULTIPART_FORM_DATA);

        authorizationService.addAuthHeader(request.getHeaders());
        requestExecutor.execute(request);
        log.info("Upload file done");
    }

    @Override
    public void download(RequestResults<?> requestResults, int fileId) {
        Credentials credentials = authenticationService.getUserCredentials();
        log.info(String.format("Download file: user=%s, fileId=%d", credentials.getUsername(), fileId));
        RestRequest<?> request = new RestRequest<>(requestResults, HttpMethod.GET, urls.file(credentials.getUsername(), fileId));
        authorizationService.addAuthHeader(request.getHeaders());
        requestExecutor.execute(request);
        log.info("Download file done");
    }

    @Override
    public void delete(RequestResults<?> requestResults, int fileId) {
        Credentials credentials = authenticationService.getUserCredentials();
        log.info(String.format("Delete file: user=%s, fileId=%d", credentials.getUsername(), fileId));
        RestRequest<?> request = new RestRequest<>(requestResults, HttpMethod.DELETE, urls.file(credentials.getUsername(), fileId));
        authorizationService.addAuthHeader(request.getHeaders());
        requestExecutor.execute(request);
        log.info("Delete file done");
    }
}

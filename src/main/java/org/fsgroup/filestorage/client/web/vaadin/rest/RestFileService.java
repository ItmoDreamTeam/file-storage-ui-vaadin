package org.fsgroup.filestorage.client.web.vaadin.rest;

import org.apache.log4j.Logger;
import org.fsgroup.filestorage.client.web.vaadin.security.UserCredentials;
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
    private RestAuthorization auth;

    @Resource
    private RequestExecutor requestExecutor;

    @Override
    public void upload(RequestResults<?> requestResults, UserCredentials userCredentials, File file) {
        log.info(String.format("Upload file, name=%s", file.getName()));
        String url = urls.file(userCredentials.getUsername());
        RestRequest<?> request = new RestRequest<>(requestResults, HttpMethod.POST, url);
        addFileToRequest(request, file);
        auth.addAuthHeader(request, userCredentials);
        requestExecutor.execute(request);
        log.info("Upload file done");
    }

    @Override
    public void download(RequestResults<?> requestResults, UserCredentials userCredentials, int fileId) {
        log.info(String.format("Download file: user=%s, fileId=%d", userCredentials.getUsername(), fileId));
        String url = urls.file(userCredentials.getUsername(), fileId);
        RestRequest<?> request = new RestRequest<>(requestResults, HttpMethod.GET, url);
        auth.addAuthHeader(request, userCredentials);
        requestExecutor.execute(request);
        log.info("Download file done");
    }

    @Override
    public void delete(RequestResults<?> requestResults, UserCredentials userCredentials, int fileId) {
        log.info(String.format("Delete file: user=%s, fileId=%d", userCredentials.getUsername(), fileId));
        String url = urls.file(userCredentials.getUsername(), fileId);
        RestRequest<?> request = new RestRequest<>(requestResults, HttpMethod.DELETE, url);
        auth.addAuthHeader(request, userCredentials);
        requestExecutor.execute(request);
        log.info("Delete file done");
    }

    private static void addFileToRequest(RestRequest<?> request, File file) {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("file", new FileSystemResource(file));
        request.setBody(params);
        request.getHeaders().setContentType(MediaType.MULTIPART_FORM_DATA);
    }
}

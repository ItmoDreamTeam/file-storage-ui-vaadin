package org.fsgroup.filestorage.vaadin.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fsgroup.filestorage.vaadin.auth.AuthorizationService;
import org.fsgroup.filestorage.vaadin.service.FileService;
import org.fsgroup.filestorage.vaadin.service.OnRequestFail;
import org.fsgroup.filestorage.vaadin.service.RequestExecutor;
import org.fsgroup.filestorage.vaadin.service.RequestResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class RestFileService implements FileService {

    private static final Logger log = LoggerFactory.getLogger(RestFileService.class);

    @Resource
    private RestApiUrls urls;

    @Resource
    private AuthorizationService authorizationService;

    @Resource
    private RequestExecutor requestExecutor;

    @Override
    public InputStream download(OnRequestFail onRequestFail, int fileId) {
        log.info(String.format("Download file: fileId=%d", fileId));
        try {
            URL url = new URL(urls.file(fileId));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty(HttpHeaders.AUTHORIZATION, authorizationService.authString());

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpStatus.OK.value()) {
                log.info("Download file done successfully");
                return connection.getInputStream();
            } else {
                ObjectMapper objectMapper = new ObjectMapper();
                ErrorMessage errorMessage = objectMapper.readValue(connection.getErrorStream(), ErrorMessage.class);
                onRequestFail.onFail(errorMessage.getMessage());
                log.warn(String.format("Error while downloading file: response code = %d, message = %s",
                        responseCode, errorMessage));
                return null;
            }
        } catch (Exception e) {
            log.warn(String.format("Error while downloading file: %s", e.getMessage()));
            return null;
        }
    }

    @Override
    public void upload(RequestResults<?> requestResults, String filename, InputStream fileStream) {
        log.info(String.format("Upload file, name=%s", filename));
        RestRequest<?> request = new RestRequest<>(requestResults, HttpMethod.POST, urls.file());

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("file", new InMemoryResource(filename, fileStream));
        request.setBody(params);

        authorizationService.addAuthHeader(request.getHeaders());
        requestExecutor.execute(request);
        log.info("Upload file done");
    }

    @Override
    public void delete(RequestResults<?> requestResults, int fileId) {
        log.info(String.format("Delete file: fileId=%d", fileId));
        RestRequest<?> request = new RestRequest<>(requestResults, HttpMethod.DELETE, urls.file(fileId));
        authorizationService.addAuthHeader(request.getHeaders());
        requestExecutor.execute(request);
        log.info("Delete file done");
    }
}

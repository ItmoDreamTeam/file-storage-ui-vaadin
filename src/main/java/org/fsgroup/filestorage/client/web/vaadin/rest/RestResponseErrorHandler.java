package org.fsgroup.filestorage.client.web.vaadin.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class RestResponseErrorHandler extends DefaultResponseErrorHandler {

    private static final Logger log = Logger.getLogger(RestResponseErrorHandler.class);
    private static final String UNKNOWN_ERROR = "Unknown error";

    private boolean errorOccurred = false;
    private String errorMessage;

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        log.info("Rest response informs about an error");
        errorOccurred = true;
        errorMessage = deserializeMessage(response.getBody());
        log.info(String.format("Error message: %s", errorMessage));
    }

    public boolean errorOccurred() {
        return errorOccurred;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    private String deserializeMessage(InputStream inputStream) {
        String content = streamToString(inputStream);
        log.info(String.format("Response: %s", content));
        ObjectMapper om = new ObjectMapper();
        try {
            return om.readTree(content).findValue("message").asText(UNKNOWN_ERROR);
        } catch (Exception e) {
            log.warn("Error wasn't recognized (no json property 'message')");
            return UNKNOWN_ERROR;
        }
    }

    private String streamToString(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        StringBuilder string = new StringBuilder();
        while (scanner.hasNext())
            string.append(scanner.nextLine());
        return string.toString();
    }
}

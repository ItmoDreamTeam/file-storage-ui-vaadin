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
    private static final String SERVER_ERROR = "Server error";
    private static final String UNKNOWN_ERROR = "Unknown error";

    private boolean errorOccurred = false;
    private String errorMessage;

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        log.info("Rest response informs about an error");
        errorOccurred = true;
        if (response.getStatusCode().is4xxClientError()) {
            errorMessage = deserializeMessage(response.getBody());
        } else {
            errorMessage = SERVER_ERROR;
        }
        log.info(String.format("Error message: %s", errorMessage));
    }

    public boolean errorOccurred() {
        return errorOccurred;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    private static String deserializeMessage(InputStream inputStream) {
        String content = streamToString(inputStream);
        log.info(String.format("Response: %s", content));
        ObjectMapper objectMapper = new ObjectMapper();
        ErrorMessage errorMessage;
        try {
            errorMessage = objectMapper.readValue(content, ErrorMessage.class);
        } catch (Exception e) {
            log.warn("Error wasn't recognized (no json property 'message')");
            return UNKNOWN_ERROR;
        }
        return errorMessage.toString();
    }

    private static String streamToString(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        StringBuilder string = new StringBuilder();
        while (scanner.hasNext())
            string.append(scanner.nextLine());
        return string.toString();
    }
}

package org.fsgroup.filestorage.client.web.vaadin.rest;

import org.springframework.core.io.AbstractResource;

import java.io.IOException;
import java.io.InputStream;

public class InMemoryResource extends AbstractResource {

    private String filename;
    private InputStream sendStream;

    public InMemoryResource(String filename, InputStream sendStream) {
        this.filename = filename;
        this.sendStream = sendStream;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return sendStream;
    }

    @Override
    public String getFilename() {
        return filename;
    }

    @Override
    public long contentLength() throws IOException {
        return 0;
    }

    @Override
    public String getDescription() {
        return null;
    }
}

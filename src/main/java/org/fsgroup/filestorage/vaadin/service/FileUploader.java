package org.fsgroup.filestorage.vaadin.service;

import com.vaadin.ui.Upload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class FileUploader implements Upload.Receiver {

    private static final Logger log = LoggerFactory.getLogger(FileUploader.class);

    private String filename;
    private ByteArrayOutputStream receiveStream;

    @Override
    public OutputStream receiveUpload(String filename, String mimeType) {
        this.filename = filename;
        this.receiveStream = new ByteArrayOutputStream();
        return receiveStream;
    }

    public String getFilename() {
        return filename;
    }

    public InputStream getSendStream() {
        if (receiveStream == null)
            throw new RuntimeException("Attempted to get send stream before creating receiving stream");
        return new ByteArrayInputStream(receiveStream.toByteArray());
    }

    public void closeReceiveStream() {
        try {
            if (receiveStream != null)
                receiveStream.close();
        } catch (IOException e) {
            log.warn("Unable to close receive stream", e);
        }
    }
}

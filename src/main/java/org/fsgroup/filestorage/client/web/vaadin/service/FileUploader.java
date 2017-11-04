package org.fsgroup.filestorage.client.web.vaadin.service;

import com.vaadin.ui.Upload;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class FileUploader implements Upload.Receiver {

    private static final Logger log = Logger.getLogger(FileUploader.class);

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

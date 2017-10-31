package org.fsgroup.filestorage.client.web.vaadin.service;

import com.vaadin.ui.Upload;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.UUID;

@Service
public class FileUploader implements Upload.Receiver {

    private static final Logger log = Logger.getLogger(FileUploader.class);

    private final File file;

    public FileUploader() {
        try {
            file = File.createTempFile("" + UUID.randomUUID(), "");
        } catch (Exception e) {
            log.error("TempFile creation failed", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public OutputStream receiveUpload(String filename, String mimeType) {
        try {
            return new FileOutputStream(file);
        } catch (Exception e) {
            log.error("error while receiving upload", e);
            throw new RuntimeException(e);
        }
    }

    public File getFile() {
        return file;
    }
}

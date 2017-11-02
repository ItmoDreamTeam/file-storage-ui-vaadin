package org.fsgroup.filestorage.client.web.vaadin.service;

import com.vaadin.ui.Upload;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.OutputStream;

@Service
public class FileUploader implements Upload.Receiver {

    private static final Logger log = Logger.getLogger(FileUploader.class);

    @Override
    public OutputStream receiveUpload(String filename, String mimeType) {
        return null;
    }

    public File getFile() {
        return null;
    }
}

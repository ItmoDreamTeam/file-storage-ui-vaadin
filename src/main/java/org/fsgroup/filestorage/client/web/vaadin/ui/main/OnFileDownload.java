package org.fsgroup.filestorage.client.web.vaadin.ui.main;

import org.fsgroup.filestorage.client.web.vaadin.model.UploadedFile;

import java.io.InputStream;

public interface OnFileDownload {

    InputStream download(UploadedFile file);
}

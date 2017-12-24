package org.fsgroup.filestorage.vaadin.ui.main;

import org.fsgroup.filestorage.vaadin.model.UploadedFile;

import java.io.InputStream;

public interface OnFileDownload {

    InputStream download(UploadedFile file);
}

package org.fsgroup.filestorage.client.web.vaadin.ui.main;

import org.fsgroup.filestorage.client.web.vaadin.model.UploadedFile;

public interface OnFileDownload {

    void download(UploadedFile file);
}

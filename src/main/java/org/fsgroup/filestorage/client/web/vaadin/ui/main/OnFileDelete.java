package org.fsgroup.filestorage.client.web.vaadin.ui.main;

import com.vaadin.ui.Component;
import org.fsgroup.filestorage.client.web.vaadin.model.UploadedFile;

public interface OnFileDelete {

    void delete(UploadedFile file, Component fileRow);
}

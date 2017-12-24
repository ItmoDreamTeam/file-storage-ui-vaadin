package org.fsgroup.filestorage.vaadin.ui.main;

import com.vaadin.ui.Component;
import org.fsgroup.filestorage.vaadin.model.UploadedFile;

public interface OnFileDelete {

    void delete(UploadedFile file, Component fileRow);
}

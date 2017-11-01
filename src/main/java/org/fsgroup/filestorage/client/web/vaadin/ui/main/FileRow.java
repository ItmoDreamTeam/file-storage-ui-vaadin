package org.fsgroup.filestorage.client.web.vaadin.ui.main;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import org.fsgroup.filestorage.client.web.vaadin.model.UploadedFile;

public class FileRow extends HorizontalLayout {

    public FileRow(UploadedFile file, OnFileDownload onFileDownload, OnFileDelete onFileDelete) {
        Label fileNameLabel = new Label(file.getName());
        Label sizeLabel = new Label("x MB");
        Label uploadedDateLabel = new Label(file.getCreated().toString());
        Button downloadFileButton = new Button("Download",
                clickEvent -> onFileDownload.download(file));
        Button deleteFileButton = new Button("\uD83D\uDDD1",
                clickEvent -> onFileDelete.delete(file, this));
        setSizeFull();
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponents(fileNameLabel, sizeLabel, uploadedDateLabel, downloadFileButton, deleteFileButton);
    }
}

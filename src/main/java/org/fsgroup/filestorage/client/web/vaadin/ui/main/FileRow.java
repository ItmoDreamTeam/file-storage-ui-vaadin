package org.fsgroup.filestorage.client.web.vaadin.ui.main;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import org.fsgroup.filestorage.client.web.vaadin.model.UploadedFile;

public class FileRow extends HorizontalLayout {

    private final Label fileNameLabel;
    private final Label uploadedDateLabel;
    private final Button downloadFileButton;
    private final Button deleteFileButton;

    public FileRow(UploadedFile file, OnFileDownload onFileDownload, OnFileDelete onFileDelete) {
        fileNameLabel = new Label(file.getName());
        uploadedDateLabel = new Label(file.getCreated().toString());
        downloadFileButton = new Button("Download", clickEvent -> onFileDownload.download(file));
        deleteFileButton = new Button("\uD83D\uDDD1", clickEvent -> onFileDelete.delete(file, this));

        setSizeFull();
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponents(fileNameLabel, uploadedDateLabel, downloadFileButton, deleteFileButton);

        downloadFileButton.setEnabled(false);
    }
}

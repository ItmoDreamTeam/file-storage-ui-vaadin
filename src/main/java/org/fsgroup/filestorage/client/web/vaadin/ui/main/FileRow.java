package org.fsgroup.filestorage.client.web.vaadin.ui.main;

import com.vaadin.server.FileDownloader;
import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import org.fsgroup.filestorage.client.web.vaadin.model.UploadedFile;

public class FileRow extends HorizontalLayout {

    private final UploadedFile file;
    private final OnFileDownload onFileDownload;
    private final OnFileDelete onFileDelete;

    private final Label fileNameLabel;
    private final Label sizeLabel;
    private final Label uploadedDateLabel;
    private final Button downloadFileButton;
    private final Button deleteFileButton;

    public FileRow(UploadedFile file, OnFileDownload onFileDownload, OnFileDelete onFileDelete) {
        this.file = file;
        this.onFileDownload = onFileDownload;
        this.onFileDelete = onFileDelete;
        this.fileNameLabel = new Label(file.getName());
        this.sizeLabel = new Label("* MB");
        this.uploadedDateLabel = new Label(file.getCreated().toString());
        this.downloadFileButton = new Button("\u2B73");
        this.deleteFileButton = new Button("\uD83D\uDDD1");
        initLayout();
        initDownloadFile();
        initDeleteFile();
    }

    private void initLayout() {
        setSizeFull();
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponents(fileNameLabel, sizeLabel, uploadedDateLabel, downloadFileButton, deleteFileButton);
    }

    private void initDownloadFile() {
        Resource fileResource = new StreamResource(() -> onFileDownload.download(file), file.getName());
        FileDownloader fileDownloader = new FileDownloader(fileResource);
        fileDownloader.extend(downloadFileButton);
    }

    private void initDeleteFile() {
        deleteFileButton.addClickListener(clickEvent -> onFileDelete.delete(file, this));
    }
}

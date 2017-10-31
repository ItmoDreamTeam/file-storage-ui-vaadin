package org.fsgroup.filestorage.client.web.vaadin.ui.main;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import org.fsgroup.filestorage.client.web.vaadin.model.UploadedFile;
import org.fsgroup.filestorage.client.web.vaadin.model.User;
import org.fsgroup.filestorage.client.web.vaadin.security.UserCredentials;
import org.fsgroup.filestorage.client.web.vaadin.service.FileService;
import org.fsgroup.filestorage.client.web.vaadin.service.RequestResults;

import javax.annotation.Resource;
import java.util.stream.Collectors;

@UIScope
@SpringComponent
public class FilesLayout extends VerticalLayout {

    @Resource
    private FileService fileService;

    public FilesLayout() {
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        setSizeFull();
    }

    public void refresh() {
        removeAllComponents();
    }

    public void refresh(UserCredentials userCredentials, User user) {
        addComponents(
                user.getFiles().stream()
                        .map(file -> new FileRow(file, fileToDownload -> download(userCredentials, fileToDownload),
                                (fileToDelete, fileRow) -> delete(userCredentials, fileToDelete, fileRow)))
                        .collect(Collectors.toList()).toArray(new FileRow[user.getFiles().size()])
        );
    }

    private void download(UserCredentials userCredentials, UploadedFile file) {
    }

    private void delete(UserCredentials userCredentials, UploadedFile file, Component fileRow) {
        RequestResults<?> requestResults = new RequestResults<>();
        requestResults.setOnRequestSuccess(response -> removeComponent(fileRow));
        requestResults.setOnRequestFail(Notification::show);
        fileService.delete(requestResults, userCredentials, file.getId());
    }
}

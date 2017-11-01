package org.fsgroup.filestorage.client.web.vaadin.ui.main;

import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.fsgroup.filestorage.client.web.vaadin.model.UploadedFile;
import org.fsgroup.filestorage.client.web.vaadin.model.User;
import org.fsgroup.filestorage.client.web.vaadin.service.FileService;
import org.fsgroup.filestorage.client.web.vaadin.service.RequestResults;
import org.fsgroup.filestorage.client.web.vaadin.service.UserService;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.stream.Collectors;

@UIScope
@SpringComponent
public class FilesLayout extends VerticalLayout {

    private static final Logger log = Logger.getLogger(FilesLayout.class);

    @Resource
    private UserService userService;

    @Resource
    private FileService fileService;

    public FilesLayout() {
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        setSizeFull();
    }

    public void clear() {
        removeAllComponents();
    }

    public void setData(User user) {
        clear();
        addComponents(user.getFiles().stream()
                .map(file -> new FileRow(file, this::download, this::delete))
                .collect(Collectors.toList()).toArray(new FileRow[user.getFiles().size()])
        );
    }

    public void refresh() {
        RequestResults<User> requestResults = new RequestResults<>(User.class);
        requestResults.setOnRequestSuccess(this::setData);
        requestResults.setOnRequestFail(Notification::show);
        userService.get(requestResults);
    }

    private void download(UploadedFile file) {
        File tmpFile;
        try {
            tmpFile = File.createTempFile("coocoo", "");
        } catch (Exception e) {
            log.error("Error: ", e);
            throw new RuntimeException(e);
        }

        RequestResults<String> requestResults = new RequestResults<>(String.class);
        requestResults.setOnRequestSuccess(response -> {
            try {
                FileWriter fileWriter = new FileWriter(tmpFile);
                fileWriter.write(response);
                fileWriter.close();
            } catch (Exception e) {
                log.error("Error: ", e);
            }
        });
        requestResults.setOnRequestFail(Notification::show);
        fileService.download(requestResults, file.getId());

        FileDownloader fileDownloader = new FileDownloader(new StreamResource(() -> {
            try {
                return new FileInputStream(tmpFile);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, file.getName()));

        Button button = new Button("DOWNLOAD");
        fileDownloader.extend(button);
        addComponent(button);
    }

    private void delete(UploadedFile file, Component fileRow) {
        RequestResults<?> requestResults = new RequestResults<>();
        requestResults.setOnRequestSuccess(response -> removeComponent(fileRow));
        requestResults.setOnRequestFail(Notification::show);
        fileService.delete(requestResults, file.getId());
    }
}

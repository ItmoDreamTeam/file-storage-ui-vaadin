package org.fsgroup.filestorage.client.web.vaadin.ui.main;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import org.apache.log4j.Logger;
import org.fsgroup.filestorage.client.web.vaadin.model.UploadedFile;
import org.fsgroup.filestorage.client.web.vaadin.model.User;
import org.fsgroup.filestorage.client.web.vaadin.service.FileService;
import org.fsgroup.filestorage.client.web.vaadin.service.RequestResults;
import org.fsgroup.filestorage.client.web.vaadin.service.UserService;

import javax.annotation.Resource;
import java.io.InputStream;
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

    //TODO
    private InputStream download(UploadedFile file) {
        RequestResults<String> requestResults = new RequestResults<>(String.class);
        requestResults.setOnRequestFail(Notification::show);
        return fileService.download(requestResults, file.getId());
    }

    private void delete(UploadedFile file, Component fileRow) {
        RequestResults<?> requestResults = new RequestResults<>();
        requestResults.setOnRequestSuccess(response -> removeComponent(fileRow));
        requestResults.setOnRequestFail(Notification::show);
        fileService.delete(requestResults, file.getId());
    }
}

package org.fsgroup.filestorage.client.web.vaadin.ui.main;

import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.fsgroup.filestorage.client.web.vaadin.model.User;
import org.fsgroup.filestorage.client.web.vaadin.security.UserCredentials;
import org.fsgroup.filestorage.client.web.vaadin.service.FileService;
import org.fsgroup.filestorage.client.web.vaadin.service.FileUploader;
import org.fsgroup.filestorage.client.web.vaadin.service.RequestResults;
import org.fsgroup.filestorage.client.web.vaadin.ui.Views;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@UIScope
@SpringComponent
public class MainPageHeader extends HorizontalLayout {

    @Resource
    private FileService fileService;

    @Resource
    private FileUploader fileUploader;

    private UserCredentials userCredentials;

    private Label usernameLabel;
    private Upload upload;
    private Button settingsButton;
    private Button signOutButton;
    private HorizontalLayout rightPanel;

    public MainPageHeader() {
        usernameLabel = new Label("User", ContentMode.HTML);
        upload = new Upload();
        settingsButton = new Button("Settings",
                clickEvent -> UI.getCurrent().getNavigator().navigateTo(Views.SETTINGS));
        signOutButton = new Button("Sign Out",
                clickEvent -> signOut());
        rightPanel = new HorizontalLayout(settingsButton, signOutButton);
    }

    @PostConstruct
    public void init() {
        addComponents(usernameLabel, upload, rightPanel);
        setWidth(100, Unit.PERCENTAGE);
        setComponentAlignment(usernameLabel, Alignment.MIDDLE_LEFT);
        setComponentAlignment(upload, Alignment.MIDDLE_CENTER);
        setComponentAlignment(rightPanel, Alignment.MIDDLE_RIGHT);
        upload.setReceiver(fileUploader);
        upload.addSucceededListener(succeededEvent -> uploadFile());
        upload.addFailedListener(failedEvent ->
                Notification.show(String.format("Unable to upload file: %s", failedEvent.getReason().getMessage())));
    }

    public void refresh() {
        usernameLabel.setVisible(false);
    }

    public void refresh(UserCredentials userCredentials, User user) {
        this.userCredentials = userCredentials;
        usernameLabel.setVisible(true);
        usernameLabel.setValue(String.format("<h3>User: <b>%s</b></h3>", user.getUsername()));
    }

    private void uploadFile() {
        RequestResults<?> requestResults = new RequestResults<>();
        requestResults.setOnRequestSuccess(response -> Notification.show("File uploaded to server"));
        requestResults.setOnRequestFail(Notification::show);
        fileService.upload(requestResults, userCredentials, fileUploader.getFile());
    }

    private void signOut() {
        VaadinSession.getCurrent().setAttribute(UserCredentials.class, null);
        UI.getCurrent().getNavigator().navigateTo(Views.ROOT);
    }
}

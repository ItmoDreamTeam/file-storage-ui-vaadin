package org.fsgroup.filestorage.client.web.vaadin.ui.main;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.fsgroup.filestorage.client.web.vaadin.auth.AuthenticationService;
import org.fsgroup.filestorage.client.web.vaadin.model.User;
import org.fsgroup.filestorage.client.web.vaadin.service.FileService;
import org.fsgroup.filestorage.client.web.vaadin.service.FileUploader;
import org.fsgroup.filestorage.client.web.vaadin.service.RequestResults;
import org.fsgroup.filestorage.client.web.vaadin.ui.Views;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@UIScope
@SpringComponent
public class MainPageHeader extends HorizontalLayout {

    private static final Logger log = Logger.getLogger(MainPageHeader.class);

    @Resource
    private AuthenticationService authenticationService;

    @Resource
    private FileService fileService;

    @Resource
    private FileUploader fileUploader;

    @Resource
    private FilesLayout filesLayout;

    private Label usernameLabel;
    private Upload upload;
    private Button settingsButton;
    private Button signOutButton;
    private HorizontalLayout rightPanel;

    public MainPageHeader() {
        usernameLabel = new Label();
        usernameLabel.setContentMode(ContentMode.HTML);
        upload = new Upload();
        upload.setButtonCaption("Upload File");
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

    public void clear() {
        usernameLabel.setValue("...");
    }

    public void setData(User user) {
        usernameLabel.setValue(String.format("<h3>User: <b>%s</b></h3>", user.getUsername()));
    }

    private void signOut() {
        authenticationService.clear();
        UI.getCurrent().getNavigator().navigateTo(Views.ROOT);
    }

    //TODO
    private void uploadFile() {
        RequestResults<?> requestResults = new RequestResults<>();
        requestResults.setOnRequestSuccess(response -> filesLayout.refresh());
        requestResults.setOnRequestFail(Notification::show);
        fileService.upload(requestResults, fileUploader.getFile());
    }
}

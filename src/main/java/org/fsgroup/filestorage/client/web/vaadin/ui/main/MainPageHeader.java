package org.fsgroup.filestorage.client.web.vaadin.ui.main;

import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.fsgroup.filestorage.client.web.vaadin.model.User;
import org.fsgroup.filestorage.client.web.vaadin.security.UserCredentials;
import org.fsgroup.filestorage.client.web.vaadin.service.FileService;
import org.fsgroup.filestorage.client.web.vaadin.ui.Views;

import javax.annotation.Resource;

@UIScope
@SpringComponent
public class MainPageHeader extends HorizontalLayout {

    @Resource
    private FileService fileService;

    private Label usernameLabel;
    private Button uploadButton;
    private Button settingsButton;
    private Button signOutButton;
    private HorizontalLayout rightHeaderLayout;

    public MainPageHeader() {
        usernameLabel = new Label("User", ContentMode.HTML);
        uploadButton = new Button("Upload File", clickEvent -> uploadFile());
        settingsButton = new Button("Settings");
        signOutButton = new Button("Sign Out", clickEvent -> signOut());
        rightHeaderLayout = new HorizontalLayout(settingsButton, signOutButton);
        addComponents(usernameLabel, uploadButton, rightHeaderLayout);
        setWidth(100, Unit.PERCENTAGE);
        setComponentAlignment(usernameLabel, Alignment.MIDDLE_LEFT);
        setComponentAlignment(uploadButton, Alignment.MIDDLE_CENTER);
        setComponentAlignment(rightHeaderLayout, Alignment.MIDDLE_RIGHT);

        uploadButton.setEnabled(false);
        settingsButton.setEnabled(false);
    }

    public void refresh() {
        usernameLabel.setVisible(false);
    }

    public void refresh(User user) {
        usernameLabel.setVisible(true);
        usernameLabel.setValue(String.format("<h3>User: <b>%s</b></h3>", user.getUsername()));
    }

    private void uploadFile() {
    }

    private void signOut() {
        VaadinSession.getCurrent().setAttribute(UserCredentials.class, null);
        UI.getCurrent().getNavigator().navigateTo(Views.ROOT);
    }
}

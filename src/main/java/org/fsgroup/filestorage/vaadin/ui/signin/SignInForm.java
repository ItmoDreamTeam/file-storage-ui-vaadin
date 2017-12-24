package org.fsgroup.filestorage.vaadin.ui.signin;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.fsgroup.filestorage.vaadin.auth.AuthenticationService;
import org.fsgroup.filestorage.vaadin.auth.Credentials;
import org.fsgroup.filestorage.vaadin.model.User;
import org.fsgroup.filestorage.vaadin.service.RequestResults;
import org.fsgroup.filestorage.vaadin.service.UserService;
import org.fsgroup.filestorage.vaadin.ui.Views;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@UIScope
@SpringComponent
public class SignInForm extends VerticalLayout {

    @Resource
    private AuthenticationService authenticationService;

    @Resource
    private UserService userService;

    private TextField usernameField;
    private PasswordField passwordField;
    private Button signInButton;

    public SignInForm() {
        usernameField = new TextField("Username:");
        passwordField = new PasswordField("Password:");
        signInButton = new Button("Sign In", clickEvent -> signIn());
    }

    @PostConstruct
    public void init() {
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponents(usernameField, passwordField, signInButton);
    }

    public void refresh() {
        usernameField.clear();
        passwordField.clear();
    }

    private void signIn() {
        Credentials credentials = new Credentials(usernameField.getValue(), passwordField.getValue());
        authenticationService.authenticate(credentials);
        RequestResults<User> requestResults = new RequestResults<>(User.class);
        requestResults.setOnRequestSuccess(this::signInSuccess);
        requestResults.setOnRequestFail(this::signInFail);
        userService.get(requestResults);
    }

    private void signInSuccess(User user) {
        UI.getCurrent().getNavigator().navigateTo(Views.ROOT);
        Notification.show(String.format("Signed in as %s", user.getUsername()), Notification.Type.TRAY_NOTIFICATION);
    }

    private void signInFail(String errorMessage) {
        authenticationService.clear();
        Notification.show(errorMessage, Notification.Type.WARNING_MESSAGE);
    }
}

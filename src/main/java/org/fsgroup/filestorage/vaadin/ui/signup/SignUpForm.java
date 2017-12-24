package org.fsgroup.filestorage.vaadin.ui.signup;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.fsgroup.filestorage.vaadin.auth.AuthenticationService;
import org.fsgroup.filestorage.vaadin.auth.Credentials;
import org.fsgroup.filestorage.vaadin.service.RequestResults;
import org.fsgroup.filestorage.vaadin.service.UserService;
import org.fsgroup.filestorage.vaadin.ui.Views;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@UIScope
@SpringComponent
public class SignUpForm extends VerticalLayout {

    @Resource
    private AuthenticationService authenticationService;

    @Resource
    private UserService userService;

    private TextField usernameField;
    private PasswordField passwordField;
    private PasswordField repeatPasswordField;
    private Button signUpButton;

    public SignUpForm() {
        usernameField = new TextField("Username:");
        passwordField = new PasswordField("Password:");
        repeatPasswordField = new PasswordField("Repeat password:");
        signUpButton = new Button("Sign Up", clickEvent -> signUp());
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
    }

    @PostConstruct
    public void init() {
        addComponents(usernameField, passwordField, repeatPasswordField, signUpButton);
    }

    public void refresh() {
        usernameField.clear();
        passwordField.clear();
        repeatPasswordField.clear();
    }

    private void signUp() {
        String username = usernameField.getValue();
        String password = passwordField.getValue();
        String repeatedPassword = repeatPasswordField.getValue();
        if (!password.equals(repeatedPassword)) {
            passwordField.clear();
            repeatPasswordField.clear();
            Notification.show("Passwords don't match", Notification.Type.WARNING_MESSAGE);
        } else {
            RequestResults<?> requestResults = new RequestResults<>();
            requestResults.setOnRequestSuccess(response -> signUpSuccess(username, password));
            requestResults.setOnRequestFail(this::signUpFail);
            userService.signUp(requestResults, username, password);
        }
    }

    private void signUpSuccess(String username, String password) {
        Credentials credentials = new Credentials(username, password);
        authenticationService.authenticate(credentials);
        UI.getCurrent().getNavigator().navigateTo(Views.ROOT);
        Notification.show(String.format("Signed in as %s", username), Notification.Type.TRAY_NOTIFICATION);
    }

    private void signUpFail(String errorMessage) {
        Notification.show(errorMessage, Notification.Type.WARNING_MESSAGE);
    }
}

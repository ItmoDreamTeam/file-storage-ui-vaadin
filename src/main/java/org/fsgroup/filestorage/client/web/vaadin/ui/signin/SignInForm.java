package org.fsgroup.filestorage.client.web.vaadin.ui.signin;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.fsgroup.filestorage.client.web.vaadin.auth.AuthenticationService;
import org.fsgroup.filestorage.client.web.vaadin.auth.Credentials;
import org.fsgroup.filestorage.client.web.vaadin.model.User;
import org.fsgroup.filestorage.client.web.vaadin.service.RequestResults;
import org.fsgroup.filestorage.client.web.vaadin.service.UserService;
import org.fsgroup.filestorage.client.web.vaadin.ui.Views;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@UIScope
@SpringComponent
public class SignInForm extends VerticalLayout {

    private static final Logger log = Logger.getLogger(SignInForm.class);

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
        requestResults.setOnRequestSuccess(response -> UI.getCurrent().getNavigator().navigateTo(Views.ROOT));
        requestResults.setOnRequestFail(this::signInFail);
        userService.get(requestResults);
    }

    private void signInFail(String errorMessage) {
        log.info(String.format("Sign in failed: %s", errorMessage));
        authenticationService.clear();
        Notification.show(errorMessage);
    }
}

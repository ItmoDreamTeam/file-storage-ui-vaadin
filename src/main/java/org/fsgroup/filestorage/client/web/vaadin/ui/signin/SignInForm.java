package org.fsgroup.filestorage.client.web.vaadin.ui.signin;

import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.fsgroup.filestorage.client.web.vaadin.model.User;
import org.fsgroup.filestorage.client.web.vaadin.security.UserCredentials;
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
        String username = usernameField.getValue();
        String password = passwordField.getValue();
        UserCredentials userCredentials = new UserCredentials(username, password);
        RequestResults<User> requestResults = new RequestResults<>(User.class);
        requestResults.setOnRequestSuccess(response -> signInSuccess(userCredentials));
        requestResults.setOnRequestFail(this::signInFail);
        userService.get(requestResults, userCredentials);
    }

    private void signInSuccess(UserCredentials userCredentials) {
        log.info(String.format("Sign in successful for user %s", userCredentials.getUsername()));
        VaadinSession.getCurrent().setAttribute(UserCredentials.class, userCredentials);
        UI.getCurrent().getNavigator().navigateTo(Views.ROOT);
    }

    private void signInFail(String errorMessage) {
        log.info(String.format("Sign in failed: %s", errorMessage));
        Notification.show(errorMessage);
    }
}

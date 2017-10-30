package org.fsgroup.filestorage.client.web.vaadin.ui.signup;

import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.fsgroup.filestorage.client.web.vaadin.security.UserCredentials;
import org.fsgroup.filestorage.client.web.vaadin.service.RequestResults;
import org.fsgroup.filestorage.client.web.vaadin.service.UserService;
import org.fsgroup.filestorage.client.web.vaadin.ui.Views;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@UIScope
@SpringComponent
public class SignUpForm extends VerticalLayout {

    @Resource
    private UserService userService;

    private TextField usernameField;
    private PasswordField passwordField;
    private PasswordField repeatPasswordField;
    private Label errorLabel;
    private Button signUpButton;

    public SignUpForm() {
        usernameField = new TextField("Username:");
        passwordField = new PasswordField("Password:");
        repeatPasswordField = new PasswordField("Repeat password:");
        errorLabel = new Label();
        signUpButton = new Button("Sign Up", clickEvent -> signUp());
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
    }

    @PostConstruct
    public void init() {
        addComponents(usernameField, passwordField, repeatPasswordField, errorLabel, signUpButton);
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
            repeatPasswordField.clear();
            Notification.show("Passwords don't match");
            return;
        }
        RequestResults<?> requestResults = new RequestResults<>();
        requestResults.setOnRequestSuccess(response -> signIn(username, password));
        requestResults.setOnRequestFail(errorLabel::setValue);
        userService.signUp(requestResults, username, password);
    }

    private void signIn(String username, String password) {
        UserCredentials userCredentials = new UserCredentials(username, password);
        VaadinSession.getCurrent().setAttribute(UserCredentials.class, userCredentials);
        UI.getCurrent().getNavigator().navigateTo(Views.ROOT);
    }
}

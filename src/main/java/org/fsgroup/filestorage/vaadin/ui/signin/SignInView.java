package org.fsgroup.filestorage.vaadin.ui.signin;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.fsgroup.filestorage.vaadin.auth.AuthenticationService;
import org.fsgroup.filestorage.vaadin.ui.Views;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@SpringView(name = Views.SIGN_IN)
public class SignInView extends VerticalLayout implements View {

    @Resource
    private AuthenticationService authenticationService;

    @Resource
    private SignInForm signInForm;

    private Button signUpButton;

    public SignInView() {
        signUpButton = new Button("Don't have an account yet?");
        signUpButton.addClickListener(clickEvent -> UI.getCurrent().getNavigator().navigateTo(Views.SIGN_UP));
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        setSizeFull();
    }

    @PostConstruct
    public void init() {
        addComponents(signInForm, signUpButton);
        setComponentAlignment(signUpButton, Alignment.BOTTOM_CENTER);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        if (authenticationService.isUserAuthenticated()) {
            UI.getCurrent().getNavigator().navigateTo(Views.ROOT);
        } else {
            signInForm.refresh();
        }
    }
}

package org.fsgroup.filestorage.client.web.vaadin.ui.signup;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.fsgroup.filestorage.client.web.vaadin.security.UserCredentials;
import org.fsgroup.filestorage.client.web.vaadin.ui.Views;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@SpringView(name = Views.SIGN_UP)
public class SignUpView extends VerticalLayout implements View {

    @Resource
    private SignUpForm signUpForm;

    public SignUpView() {
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        setSizeFull();
    }

    @PostConstruct
    public void init() {
        addComponents(signUpForm);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        if (VaadinSession.getCurrent().getAttribute(UserCredentials.class) != null) {
            UI.getCurrent().getNavigator().navigateTo(Views.ROOT);
        } else {
            signUpForm.refresh();
        }
    }
}

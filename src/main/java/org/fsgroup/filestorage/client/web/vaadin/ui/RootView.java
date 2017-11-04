package org.fsgroup.filestorage.client.web.vaadin.ui;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import org.fsgroup.filestorage.client.web.vaadin.auth.AuthenticationService;

import javax.annotation.Resource;

@SpringView(name = Views.ROOT)
public class RootView extends Panel implements View {

    @Resource
    private AuthenticationService authenticationService;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        UI.getCurrent().getNavigator().navigateTo(
                authenticationService.isUserAuthenticated() ? Views.FILES : Views.SIGN_IN
        );
    }
}

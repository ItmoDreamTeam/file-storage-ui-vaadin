package org.fsgroup.filestorage.client.web.vaadin.ui.settings;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.fsgroup.filestorage.client.web.vaadin.auth.AuthenticationService;
import org.fsgroup.filestorage.client.web.vaadin.ui.Views;

import javax.annotation.Resource;

@SpringView(name = Views.SETTINGS)
public class SettingsView extends VerticalLayout implements View {

    @Resource
    private AuthenticationService authenticationService;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        if (!authenticationService.isUserAuthenticated()) {
            UI.getCurrent().getNavigator().navigateTo(Views.ROOT);
        }
    }
}

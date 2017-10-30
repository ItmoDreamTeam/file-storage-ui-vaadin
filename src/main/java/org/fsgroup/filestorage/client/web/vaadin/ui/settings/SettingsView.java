package org.fsgroup.filestorage.client.web.vaadin.ui.settings;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.fsgroup.filestorage.client.web.vaadin.security.UserCredentials;
import org.fsgroup.filestorage.client.web.vaadin.ui.Views;

@SpringView(name = Views.SETTINGS)
public class SettingsView extends VerticalLayout implements View {

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        if (VaadinSession.getCurrent().getAttribute(UserCredentials.class) == null) {
            UI.getCurrent().getNavigator().navigateTo(Views.ROOT);
        }
    }
}

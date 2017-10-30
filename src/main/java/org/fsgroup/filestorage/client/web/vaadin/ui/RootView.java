package org.fsgroup.filestorage.client.web.vaadin.ui;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import org.fsgroup.filestorage.client.web.vaadin.security.UserCredentials;

@SpringView(name = Views.ROOT)
public class RootView extends Panel implements View {

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        UI.getCurrent().getNavigator().navigateTo(
                isAuthorized() ? Views.FILES : Views.SIGN_IN
        );
    }

    private boolean isAuthorized() {
        return VaadinSession.getCurrent().getAttribute(UserCredentials.class) != null;
    }
}

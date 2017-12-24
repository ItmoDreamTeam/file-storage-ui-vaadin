package org.fsgroup.filestorage.vaadin.ui;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;

@UIScope
@SpringView
public class ErrorView extends Panel implements View {

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        UI.getCurrent().getNavigator().navigateTo(Views.ROOT);
    }
}

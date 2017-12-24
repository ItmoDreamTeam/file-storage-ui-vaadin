package org.fsgroup.filestorage.vaadin.ui.main;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.fsgroup.filestorage.vaadin.auth.AuthenticationService;
import org.fsgroup.filestorage.vaadin.model.User;
import org.fsgroup.filestorage.vaadin.service.RequestResults;
import org.fsgroup.filestorage.vaadin.service.UserService;
import org.fsgroup.filestorage.vaadin.ui.Views;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@SpringView(name = Views.FILES)
public class MainView extends VerticalLayout implements View {

    @Resource
    private AuthenticationService authenticationService;

    @Resource
    private UserService userService;

    @Resource
    private MainPageHeader mainPageHeader;

    @Resource
    private FilesLayout filesLayout;

    @PostConstruct
    public void init() {
        addComponents(mainPageHeader, filesLayout);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        if (authenticationService.isUserAuthenticated()) {
            refresh();
        } else {
            UI.getCurrent().getNavigator().navigateTo(Views.ROOT);
        }
    }

    public void refresh() {
        mainPageHeader.clear();
        filesLayout.clear();
        RequestResults<User> requestResults = new RequestResults<>(User.class);
        requestResults.setOnRequestSuccess(this::setData);
        requestResults.setOnRequestFail(this::failToRetrieveData);
        userService.get(requestResults);
    }

    private void setData(User user) {
        mainPageHeader.setData(user);
        filesLayout.setData(user);
    }

    private void failToRetrieveData(String errorMessage) {
        authenticationService.clear();
        UI.getCurrent().getNavigator().navigateTo(Views.ROOT);
        Notification.show(errorMessage, Notification.Type.WARNING_MESSAGE);
    }
}

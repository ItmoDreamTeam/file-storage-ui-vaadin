package org.fsgroup.filestorage.client.web.vaadin.ui.main;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.fsgroup.filestorage.client.web.vaadin.model.User;
import org.fsgroup.filestorage.client.web.vaadin.security.UserCredentials;
import org.fsgroup.filestorage.client.web.vaadin.service.RequestResults;
import org.fsgroup.filestorage.client.web.vaadin.service.UserService;
import org.fsgroup.filestorage.client.web.vaadin.ui.Views;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@SpringView(name = Views.FILES)
public class MainView extends VerticalLayout implements View {

    @Resource
    private UserService userService;

    @Resource
    private MainPageHeader mainPageHeader;

    @Resource
    private FilesLayout filesLayout;

    private UserCredentials userCredentials;

    @PostConstruct
    public void init() {
        addComponents(mainPageHeader, filesLayout);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        UserCredentials userCredentials = getSession().getAttribute(UserCredentials.class);
        if (userCredentials == null) {
            UI.getCurrent().getNavigator().navigateTo(Views.ROOT);
        } else {
            this.userCredentials = userCredentials;
            mainPageHeader.refresh();
            filesLayout.refresh();
            retrieveUserData();
        }
    }

    private void retrieveUserData() {
        RequestResults<User> requestResults = new RequestResults<>(User.class);
        requestResults.setOnRequestSuccess(response -> refresh(userCredentials, response));
        requestResults.setOnRequestFail(this::failToRetrieveData);
        userService.get(requestResults, userCredentials);
    }

    private void refresh(UserCredentials userCredentials, User user) {
        mainPageHeader.refresh(userCredentials, user);
        filesLayout.refresh(userCredentials, user);
    }

    private void failToRetrieveData(String errorMessage) {
        Notification.show(errorMessage);
    }
}

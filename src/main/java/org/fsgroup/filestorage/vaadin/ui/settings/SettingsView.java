package org.fsgroup.filestorage.vaadin.ui.settings;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.fsgroup.filestorage.vaadin.auth.AuthenticationService;
import org.fsgroup.filestorage.vaadin.service.RequestResults;
import org.fsgroup.filestorage.vaadin.service.UserService;
import org.fsgroup.filestorage.vaadin.ui.Views;
import org.fsgroup.filestorage.vaadin.ui.dialog.confirm.ConfirmDialog;

import javax.annotation.Resource;

@SpringView(name = Views.SETTINGS)
public class SettingsView extends VerticalLayout implements View {

    @Resource
    private AuthenticationService authenticationService;

    @Resource
    private UserService userService;

    private Button deleteUserButton;

    public SettingsView() {
        deleteUserButton = new Button("Delete User", clickEvent -> deleteUserConfirm());
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        setSizeFull();
        addComponents(deleteUserButton);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        if (!authenticationService.isUserAuthenticated()) {
            UI.getCurrent().getNavigator().navigateTo(Views.ROOT);
        }
    }

    private void deleteUserConfirm() {
        ConfirmDialog confirmDialog = new ConfirmDialog("Delete User", "Are you sure you want to delete user?",
                "Delete", clickEvent -> deleteUser());
        confirmDialog.show();
    }

    private void deleteUser() {
        RequestResults<?> requestResults = new RequestResults<>();
        requestResults.setOnRequestSuccess(response -> {
            authenticationService.clear();
            UI.getCurrent().getNavigator().navigateTo(Views.ROOT);
        });
        requestResults.setOnRequestFail(errorMessage -> Notification.show(errorMessage, Notification.Type.WARNING_MESSAGE));
        userService.delete(requestResults);
    }
}

package org.fsgroup.filestorage.vaadin.ui.dialog.confirm;

import com.vaadin.ui.*;

public class ConfirmDialogLayout extends VerticalLayout {

    private Label messageLabel;
    private HorizontalLayout buttonsLayout;
    private Button confirmButton;
    private Button cancelButton;

    public ConfirmDialogLayout(String message, String confirmButtonCaption, String cancelButtonCaption,
                               Button.ClickListener confirmButtonListener, Button.ClickListener cancelButtonListener) {
        messageLabel = new Label(message);
        confirmButton = new Button(confirmButtonCaption, confirmButtonListener);
        cancelButton = new Button(cancelButtonCaption, cancelButtonListener);
        buttonsLayout = new HorizontalLayout(confirmButton, cancelButton);
        setSizeFull();
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponents(messageLabel, buttonsLayout);
    }
}

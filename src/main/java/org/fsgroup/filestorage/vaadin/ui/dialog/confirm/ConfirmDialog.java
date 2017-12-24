package org.fsgroup.filestorage.vaadin.ui.dialog.confirm;

import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

public class ConfirmDialog extends Window {

    private String message;

    private ConfirmDialogLayout confirmDialogLayout;
    private Button.ClickListener confirmButtonListener;

    public ConfirmDialog(String title, String message, String confirmButtonCaption, Button.ClickListener confirmButtonListener) {
        super(title);
        this.message = message;
        this.confirmButtonListener = confirmButtonListener;
        confirmDialogLayout = new ConfirmDialogLayout(message, confirmButtonCaption, "Cancel",
                this::onConfirmButtonClick, this::onCancelButtonClick);
        setContent(confirmDialogLayout);
        center();
        setWidth(50, Unit.PERCENTAGE);
        setHeight(50, Unit.PERCENTAGE);
    }

    public void show() {
        if (UI.getCurrent().getWindows().stream().noneMatch(this::equals))
            UI.getCurrent().addWindow(this);
    }

    private void onConfirmButtonClick(Button.ClickEvent clickEvent) {
        confirmButtonListener.buttonClick(clickEvent);
        close();
    }

    private void onCancelButtonClick(Button.ClickEvent clickEvent) {
        close();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfirmDialog that = (ConfirmDialog) o;
        return message.equals(that.message);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + message.hashCode();
        return result;
    }
}

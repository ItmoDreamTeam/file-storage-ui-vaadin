package org.fsgroup.filestorage.client.web.vaadin.ui.main;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import org.fsgroup.filestorage.client.web.vaadin.model.UploadedFile;
import org.fsgroup.filestorage.client.web.vaadin.model.User;

@UIScope
@SpringComponent
public class FilesGrid extends VerticalLayout {

    private Grid<UploadedFile> grid;

    public FilesGrid() {
        grid = new Grid<>();
        grid.addColumn(UploadedFile::getId).setCaption("ID");
        grid.addColumn(UploadedFile::getName).setCaption("File Name");
        grid.addColumn(UploadedFile::getCreated).setCaption("Date");
        grid.addColumn(uploadedFile -> "⇓").setCaption("Download");
        grid.addColumn(uploadedFile -> "✖").setCaption("Delete");
        grid.addItemClickListener(this::onItemClick);
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponent(grid);
        grid.setWidth(70, Unit.PERCENTAGE);
    }

    public void refresh(User user) {
        grid.setItems(user.getFiles());
    }

    private void onItemClick(Grid.ItemClick<UploadedFile> itemClick) {
        if (!itemClick.getMouseEventDetails().isDoubleClick()) return;
        UploadedFile uploadedFile = itemClick.getItem();
        Notification.show(uploadedFile.getName());
    }
}

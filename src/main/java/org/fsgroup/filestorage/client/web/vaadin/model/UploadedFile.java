package org.fsgroup.filestorage.client.web.vaadin.model;

public class UploadedFile extends BaseEntity {

    private String name;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "UploadedFile{" +
                "id=" + getId() + ", " +
                "name='" + name + '\'' +
                '}';
    }
}

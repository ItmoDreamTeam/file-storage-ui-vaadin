package org.fsgroup.filestorage.vaadin.model;

public class UploadedFile extends BaseEntity {

    private String name;
    private String size;

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "UploadedFile{" +
                "id=" + getId() + ", " +
                "name='" + name + '\'' +
                '}';
    }
}

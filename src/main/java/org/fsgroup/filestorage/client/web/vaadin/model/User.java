package org.fsgroup.filestorage.client.web.vaadin.model;

import java.util.List;

public class User extends BaseEntity {

    private String username;
    private List<UploadedFile> files;

    public String getUsername() {
        return username;
    }

    public List<UploadedFile> getFiles() {
        return files;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() + ", " +
                "username='" + username + '\'' +
                '}';
    }
}

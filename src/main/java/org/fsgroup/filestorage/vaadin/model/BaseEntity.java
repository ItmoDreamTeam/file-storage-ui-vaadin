package org.fsgroup.filestorage.vaadin.model;

import java.util.Date;

public abstract class BaseEntity {

    private int id;
    private Date created;
    private Date updated;

    public final int getId() {
        return id;
    }

    public final Date getCreated() {
        return created;
    }

    public final Date getUpdated() {
        return updated;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return id == ((BaseEntity) o).id;
    }

    @Override
    public final int hashCode() {
        return id;
    }
}

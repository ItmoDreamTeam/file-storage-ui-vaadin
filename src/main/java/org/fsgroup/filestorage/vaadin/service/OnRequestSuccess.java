package org.fsgroup.filestorage.vaadin.service;

public interface OnRequestSuccess<T> {

    void onSuccess(T response);
}

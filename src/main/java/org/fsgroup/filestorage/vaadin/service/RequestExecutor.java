package org.fsgroup.filestorage.vaadin.service;

public interface RequestExecutor {

    <T> void execute(Request<T> request);
}

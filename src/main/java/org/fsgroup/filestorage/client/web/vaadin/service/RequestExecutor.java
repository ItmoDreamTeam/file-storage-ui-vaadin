package org.fsgroup.filestorage.client.web.vaadin.service;

import org.springframework.stereotype.Service;

@Service
public interface RequestExecutor {

    <T> void execute(Request<T> request);
}

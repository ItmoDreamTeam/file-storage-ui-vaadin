package org.fsgroup.filestorage.client.web.vaadin.ui;

import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.UI;

import javax.annotation.Resource;

@SpringUI(path = "/*")
@Title("File Storage")
public class AppUI extends UI {

    @Resource
    private SpringNavigator navigator;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        navigator.init(this, this);
        navigator.setErrorView(ErrorView.class);
    }
}

package org.fsgroup.filestorage.vaadin.service;

public class RequestResults<T> {

    private final Class<T> responseType;
    private OnRequestSuccess<T> onRequestSuccess;
    private OnRequestFail onRequestFail;

    public RequestResults() {
        this(null);
    }

    public RequestResults(Class<T> responseType) {
        this.responseType = responseType;
    }

    public void setOnRequestSuccess(OnRequestSuccess<T> onRequestSuccess) {
        this.onRequestSuccess = onRequestSuccess;
    }

    public void setOnRequestFail(OnRequestFail onRequestFail) {
        this.onRequestFail = onRequestFail;
    }

    public Class<T> getResponseType() {
        return responseType;
    }

    public OnRequestSuccess<T> getOnRequestSuccess() {
        return onRequestSuccess;
    }

    public OnRequestFail getOnRequestFail() {
        return onRequestFail;
    }
}

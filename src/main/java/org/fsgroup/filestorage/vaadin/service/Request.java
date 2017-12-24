package org.fsgroup.filestorage.vaadin.service;

public abstract class Request<T> {

    private RequestResults<T> requestResults;

    public Request(RequestResults<T> requestResults) {
        this.requestResults = requestResults;
    }

    public void success(T response) {
        if (requestResults.getOnRequestSuccess() != null)
            requestResults.getOnRequestSuccess().onSuccess(response);
    }

    public void fail(String errorMessage) {
        if (requestResults.getOnRequestFail() != null)
            requestResults.getOnRequestFail().onFail(errorMessage);
    }

    public Class<T> getResponseType() {
        return requestResults.getResponseType();
    }
}

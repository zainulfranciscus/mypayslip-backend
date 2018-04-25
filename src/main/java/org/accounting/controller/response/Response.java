package org.accounting.controller.response;

public abstract class Response <T> {

    protected int status;
    protected String message;

    public Response(){}
    public Response(int s, String m) {
        status = s;
        message = m;
    }


    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public abstract T getResponseObject();
}

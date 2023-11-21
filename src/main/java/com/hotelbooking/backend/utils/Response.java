package com.hotelbooking.backend.utils;

public class Response<T>{

    public Response(T response, boolean ok, String reason) {
        this.reason = reason;
        this.response = response;
        this.ok = ok;
    }

    public T response;
    public boolean ok;
    public String reason;
}

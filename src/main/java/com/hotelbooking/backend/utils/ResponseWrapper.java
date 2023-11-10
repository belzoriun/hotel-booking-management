package com.hotelbooking.backend.utils;

import java.util.Optional;

public class ResponseWrapper {
    public static<T> Response<T> Wrap(Optional<T> data) {
        return data.map(t -> new Response<>(t, true, null))
                .orElseGet(() -> new Response<>(null, false, "Value was null"));
    }

    public static<T> Response<T> Wrap(T data) {
        return new Response<>(data, true, null);
    }

    public static<T> Response<T> WrapError(String reason) {
        return new Response<>(null, false, reason);
    }


    public static<T> Response<T> WrapSuccess() {
        return new Response<>(null, true, null);
    }
}

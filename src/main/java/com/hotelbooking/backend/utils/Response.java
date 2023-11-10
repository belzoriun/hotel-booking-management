package com.hotelbooking.backend.utils;

public record Response<T>(T response, boolean ok, String reason) {
}

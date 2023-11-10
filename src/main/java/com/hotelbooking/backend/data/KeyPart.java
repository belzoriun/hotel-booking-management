package com.hotelbooking.backend.data;

import java.util.Map;

public interface KeyPart<T> {
    public Map<String, Object> ExtractKey(T entry);
}

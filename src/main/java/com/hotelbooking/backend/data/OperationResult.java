package com.hotelbooking.backend.data;

public enum OperationResult {

    DONE(null), NULL_INPUT("Input data was null"), DUPLICATE("Value already exists"), NOT_FOUND("Value not found");

    private final String explanation;

    private OperationResult(String explanation) {
        this.explanation = explanation;
    }

    public String GetExplanation() {
        return explanation;
    }
}

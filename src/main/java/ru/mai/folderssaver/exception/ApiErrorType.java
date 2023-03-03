package ru.mai.folderssaver.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ApiErrorType {

    @JsonProperty("validation")
    VALIDATION,
    @JsonProperty("business")
    BUSINESS,
    @JsonProperty("system")
    SYSTEM;

    private ApiErrorType() {
    }


}

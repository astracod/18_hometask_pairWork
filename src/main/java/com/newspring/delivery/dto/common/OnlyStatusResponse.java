package com.newspring.delivery.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OnlyStatusResponse {
    private Status status;
    private String message;
    public enum Status {
        OK, FAIL
    }
}

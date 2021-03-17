package com.newspring.delivery.dto.common;

import lombok.Data;

@Data
public class OnlyStatusResponse {
    private Status status;
    private String message;
    public enum Status {
        OK, FAIL
    }
}

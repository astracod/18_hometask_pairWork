package com.newspring.delivery.dto.options.users;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenResponse {
    private String status;
    private String messages;
    private String token;
}

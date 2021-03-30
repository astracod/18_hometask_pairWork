package com.newspring.delivery.dto.optionsDto.usersDto;

import lombok.Data;

import java.util.List;

@Data
public class LoginForTokenResponse {
    private List<LoginForTokenDto> name;
    private String messages;
    private String token;
}

package com.newspring.delivery.domains;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRoles {
    ADMIN(3), CUSTOMER(1), EXECUTOR(2);
    private final long id;
}

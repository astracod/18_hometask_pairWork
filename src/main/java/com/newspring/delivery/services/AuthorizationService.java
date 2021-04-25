package com.newspring.delivery.services;

import com.newspring.delivery.dao.interfaceDao.UsersDao;
import com.newspring.delivery.entities.user.User;
import com.newspring.delivery.exceptions.InvalidCredentialsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorizationService {

    private final UsersDao usersService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String login(String login, String pass) throws InvalidCredentialsException {
        User u = usersService.findByLogin(login);
        if (u == null) {
                throw new InvalidCredentialsException();
        }
        if (!passwordEncoder.matches(pass, u.getPassword())) {
                throw new InvalidCredentialsException();
        }

        return jwtService.getToken(u);
    }
}

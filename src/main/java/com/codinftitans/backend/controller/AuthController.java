package com.codinftitans.backend.controller;

import com.codinftitans.backend.dto.request.GoogleUserDTO;
import com.codinftitans.backend.model.LoginRequest;
import com.codinftitans.backend.model.User;
import com.codinftitans.backend.service.GoogleAuthService;
import com.codinftitans.backend.service.TokenService;
import com.codinftitans.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
public class AuthController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final GoogleAuthService googleAuthService;
    private final UserService userService;

    public AuthController(TokenService tokenService, AuthenticationManager authenticationManager, GoogleAuthService googleAuthService, UserService userService) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.googleAuthService = googleAuthService;
        this.userService = userService;
    }

    @PostMapping("/token")
    public String token(@RequestBody LoginRequest userLogin) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.username(), userLogin.password()));
        return tokenService.generateToken(authentication);
    }
    @PostMapping("/google")
    public ResponseEntity<String> googleAuth(@RequestBody Map<String, String> request) throws Exception {
        String idToken = request.get("idToken");

        GoogleUserDTO googleUser = googleAuthService.verifyIdToken(idToken);

        User user = userService.findOrCreateGoogleUser(googleUser);

        String jwt = tokenService.generateToken((Authentication) user);

        return ResponseEntity.ok(jwt);
    }

}

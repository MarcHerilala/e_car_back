package com.codinftitans.backend.controller;

import com.codinftitans.backend.Enum.Role;
import com.codinftitans.backend.dto.request.GoogleUserDTO;
import com.codinftitans.backend.dto.request.UserRequestDTO;
import com.codinftitans.backend.model.GoogleLoginRequest;
import com.codinftitans.backend.model.LoginRequest;
import com.codinftitans.backend.model.User;
import com.codinftitans.backend.service.GoogleAuthService;
import com.codinftitans.backend.service.TokenService;
import com.codinftitans.backend.service.UserDetailsServiceImpl;
import com.codinftitans.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final UserDetailsServiceImpl userDetailsService;

    public AuthController(TokenService tokenService, AuthenticationManager authenticationManager, GoogleAuthService googleAuthService, UserService userService, UserDetailsServiceImpl userDetailsService) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.googleAuthService = googleAuthService;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/token")
    public String token(@RequestBody LoginRequest userLogin) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.username(), userLogin.password()));
        return tokenService.generateToken(authentication);
    }

    @PostMapping("/register")
    public User register(@RequestBody UserRequestDTO user){
        return userService.newUser(user, Role.CLIENT);
    }
    @PostMapping("/google")
    public ResponseEntity<String> googleAuth(@RequestBody GoogleLoginRequest request) throws Exception {
        if (request == null || request.idToken() == null) {
            return ResponseEntity.badRequest().body("Invalid request: ID Token is missing");
        }
        String idToken = request.idToken();
        System.out.println("Received ID Token: " + idToken);

        GoogleUserDTO googleUser = googleAuthService.verifyIdToken(idToken);
        User user = userService.findOrCreateGoogleUser(googleUser);

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        String jwt = tokenService.generateToken(authentication);
        return ResponseEntity.ok(jwt);
    }

}

package com.codinftitans.backend.service;

import com.codinftitans.backend.dto.request.GoogleUserDTO;
import com.codinftitans.backend.model.User;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
@Service
public class GoogleAuthService {
    private final String GOOGLE_CLIENT_ID=System.getenv("GOOGLE_CLIENT_ID");
    public GoogleUserDTO verifyIdToken(String idToken) throws GeneralSecurityException, IOException {

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(), GsonFactory.getDefaultInstance()
        ).setAudience(Collections.singletonList(GOOGLE_CLIENT_ID)).build();

        GoogleIdToken verifiedIdToken = verifier.verify(idToken);
        if (verifiedIdToken == null) {
            throw new IllegalArgumentException("Invalid ID Token");
        }
        GoogleIdToken.Payload payload = verifiedIdToken.getPayload();
        return new GoogleUserDTO(
                payload.getEmail(),
                (String) payload.get("name"),
                (String) payload.get("picture")
        );
    }

}

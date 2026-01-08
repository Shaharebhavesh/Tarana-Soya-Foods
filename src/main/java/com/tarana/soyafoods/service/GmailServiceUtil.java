package com.tarana.soyafoods.service;

import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.Gmail;

public class GmailServiceUtil {

    private static final String APPLICATION_NAME = "Tarana Soya Foods";
    private static final GsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    private static final String TOKEN_SERVER_URL =
            "https://oauth2.googleapis.com/token";

    public static Gmail getGmailService() throws Exception {

        Credential credential = new Credential.Builder(
                BearerToken.authorizationHeaderAccessMethod()
        )
                .setTransport(GoogleNetHttpTransport.newTrustedTransport())
                .setJsonFactory(JSON_FACTORY)
                .setTokenServerEncodedUrl(TOKEN_SERVER_URL)
                .setClientAuthentication(
                        new ClientParametersAuthentication(
                                System.getenv("GMAIL_CLIENT_ID"),
                                System.getenv("GMAIL_CLIENT_SECRET")
                        )
                )
                .build();

        credential.setRefreshToken(System.getenv("GMAIL_REFRESH_TOKEN"));

        return new Gmail.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                credential
        )
                .setApplicationName(APPLICATION_NAME)
                .build();

//
//
//
    }
}

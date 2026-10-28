package com.tarana.soyafoods.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.Gmail;

public class GmailServiceUtil {

    private static final String APPLICATION_NAME = "Tarana Soya Foods";
    private static final GsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    public static Gmail getGmailService() throws Exception {

        Credential credential = new GoogleCredential.Builder()
                .setTransport(GoogleNetHttpTransport.newTrustedTransport())
                .setJsonFactory(JSON_FACTORY)
                .setClientSecrets(
                        System.getenv("GMAIL_CLIENT_ID"),
                        System.getenv("GMAIL_CLIENT_SECRET")
                )
                .build()
                .setRefreshToken(System.getenv("GMAIL_REFRESH_TOKEN"));

        credential.refreshToken(); // headless auth

        return new Gmail.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                credential
        )
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}

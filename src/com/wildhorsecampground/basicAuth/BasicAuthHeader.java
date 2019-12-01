package com.wildhorsecampground.basicAuth;

import java.util.Base64;

public class BasicAuthHeader {

    private String EncodeHeader() {

        String id = "ck_a3b097ed51398bdd6f4841411d6cb034e06d964a";
        String password = "cs_f7955113c61244f630e83a63f51f5ed640d5b2a8";

        StringBuilder stringBuilder = new StringBuilder();
        String beforeEncodeString = id + ":" + password;

        // Encode Header in Base 64
        String encodedAuthHeader = Base64.getEncoder().encodeToString(beforeEncodeString.getBytes());

        if (!encodedAuthHeader.isEmpty()) {
            stringBuilder.append("Basic ");
            stringBuilder.append(encodedAuthHeader);
            return stringBuilder.toString();
        } else {
            return "Something went wrong with header auth encoding";
        }
    }

    public String GetAuthHeader() {
        return EncodeHeader();
    }
}

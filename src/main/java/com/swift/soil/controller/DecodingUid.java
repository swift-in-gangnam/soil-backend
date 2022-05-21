package com.swift.soil.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.swift.soil.exception.CustomException;
import com.swift.soil.exception.ExceptionCode;

public class DecodingUid {

    public String tokenDecoding(String auth) {
        FirebaseToken decodedToken;
        try {
            decodedToken = FirebaseAuth.getInstance().verifyIdToken(auth);
        } catch (FirebaseAuthException e) {
            throw new CustomException(ExceptionCode.TOKEN_EXPIRED);
        }
        return decodedToken.getUid();
    }
}

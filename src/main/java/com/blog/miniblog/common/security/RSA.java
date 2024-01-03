package com.blog.miniblog.common.security;

import lombok.Getter;

import java.util.Map;

@Getter
public class RSA {
    private static final String publicKey;
    private static final String privateKey;
    static {
        Map<String, String> rsaKeys = RSAUtils.createRSAKeys();
        publicKey = rsaKeys.get(RSAUtils.PUBLIC_KEY_NAME);
        privateKey = rsaKeys.get(RSAUtils.PRIVATE_KEY_NAME);
    }
    public static String getPublicKey() {
        return publicKey;
    }
    public static String getPrivateKey() {
        return privateKey;
    }
}

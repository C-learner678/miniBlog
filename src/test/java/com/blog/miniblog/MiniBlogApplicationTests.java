package com.blog.miniblog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import com.blog.miniblog.common.security.RSAUtils;

@SpringBootTest
class MiniBlogApplicationTests {
    @Test
    void contextLoads() {
        Map<String, String> rsaKeys = RSAUtils.createRSAKeys();
        String publicKey = rsaKeys.get(RSAUtils.PUBLIC_KEY_NAME);
        System.out.println("publicKey:"+ publicKey);
        String privateKey = rsaKeys.get(RSAUtils.PRIVATE_KEY_NAME);
        System.out.println("privateKey:"+ privateKey);
    }
}

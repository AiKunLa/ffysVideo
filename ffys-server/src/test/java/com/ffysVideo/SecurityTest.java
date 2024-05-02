package com.ffysVideo;

import com.ffysVideo.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

@SpringBootTest
public class SecurityTest {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private JwtUtil jwtUtil;

    @Test
    public void testSecurity() {
        String encode = passwordEncoder.encode("123456");
        System.out.println(encode);
    }


    @Test
    public void testJwtUtils() {
        System.out.println(jwtUtil.ttlMillis);
    }
}

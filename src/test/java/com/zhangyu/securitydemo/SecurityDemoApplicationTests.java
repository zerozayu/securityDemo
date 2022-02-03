package com.zhangyu.securitydemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class SecurityDemoApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void test1(){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("123");
        System.out.println(password);

        boolean matchs = passwordEncoder.matches("123", password);
        System.out.println(matchs);
    }

}

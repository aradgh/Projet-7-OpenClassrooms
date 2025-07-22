package com.nnk.springboot;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Khang Nguyen.
 * Email: khang.nguyen@banvien.com
 * Date: 09/03/2019
 * Time: 11:26 AM
 */
@SpringBootTest
@ActiveProfiles("test")
public class PasswordEncodeTest {
    @Test
    public void testPassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "123456";
        String encodedPassword = encoder.encode(rawPassword);
        assertTrue(encoder.matches(rawPassword, encodedPassword));
    }
}

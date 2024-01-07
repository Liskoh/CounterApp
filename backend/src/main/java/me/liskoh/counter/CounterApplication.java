package me.liskoh.counter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.codec.Hex;

import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

@SpringBootApplication
public class CounterApplication {

    private static void printKey() {
        try {
            SecretKey key = Jwts.SIG.HS512.key().build();
            String hex = new String(Hex.encode(key.getEncoded()));
            System.out.println("Key: " + hex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        printKey();
        SpringApplication.run(CounterApplication.class, args);
    }

}

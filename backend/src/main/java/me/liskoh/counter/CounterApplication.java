package me.liskoh.counter;

import io.jsonwebtoken.Jwts;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.codec.Hex;

import javax.crypto.SecretKey;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class CounterApplication {

    private static void printKey() {
        try {
            SecretKey key = Jwts.SIG.HS512.key().build();
            String hex = new String(Hex.encode(key.getEncoded()));
            System.out.println("Key: " + hex);
            System.out.println("time: " + TimeUnit.DAYS.toMillis(1L));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        printKey();
        SpringApplication.run(CounterApplication.class, args);
    }

}

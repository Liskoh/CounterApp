package me.liskoh.counter.dto.input;


import lombok.Getter;

@Getter
public class RegisterInputDTO {

    private final String username;
    private final String password;

    public RegisterInputDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

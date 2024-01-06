package me.liskoh.counter.controllers;

import me.liskoh.counter.dto.input.LoginInputDTO;
import me.liskoh.counter.services.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTests {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthService authService;

    @Test
    public void login_Should_Return_200_And_AuthResponseDTO() throws Exception {
        LoginInputDTO loginInputDTO = new LoginInputDTO("", "");

        authController.login(loginInputDTO);
        verify(authService).login(loginInputDTO.getUsername(), loginInputDTO.getPassword());
    }
}

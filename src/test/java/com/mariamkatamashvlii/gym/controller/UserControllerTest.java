package com.mariamkatamashvlii.gym.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mariamkatamashvlii.gym.dto.userDto.LoginDTO;
import com.mariamkatamashvlii.gym.dto.userDto.NewPasswordDTO;
import com.mariamkatamashvlii.gym.service.implementation.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserServiceImpl userServiceImpl;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void login_Successful() throws Exception {
        LoginDTO loginDTO = new LoginDTO("user", "password");
        when(userServiceImpl.login(loginDTO)).thenReturn(true);

        mockMvc.perform(get("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Logged in successfully"));

        verify(userServiceImpl).login(loginDTO);
    }

    @Test
    void login_Unauthorized() throws Exception {
        LoginDTO loginDTO = new LoginDTO("user", "wrongPassword");
        when(userServiceImpl.login(loginDTO)).thenReturn(false);

        mockMvc.perform(get("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid username or password"));

        verify(userServiceImpl).login(loginDTO);
    }

    @Test
    void passChange_AuthorizedAndChanged() throws Exception {
        NewPasswordDTO newPasswordDTO = new NewPasswordDTO("user", "currentPass", "newPass");
        LoginDTO loginDTO = LoginDTO.builder().username("user").password("currentPass").build();
        when(userServiceImpl.login(loginDTO)).thenReturn(true);

        mockMvc.perform(put("/users/updatePassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newPasswordDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Password Changed Successfully"));

        verify(userServiceImpl).changePassword(newPasswordDTO);
    }

    @Test
    void passChange_Unauthorized() throws Exception {
        NewPasswordDTO newPasswordDTO = new NewPasswordDTO("user", "wrongPass", "newPass");
        LoginDTO loginDTO = LoginDTO.builder().username("user").password("wrongPass").build();
        when(userServiceImpl.login(loginDTO)).thenReturn(false);

        mockMvc.perform(put("/users/updatePassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newPasswordDTO)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid username or password"));

        verify(userServiceImpl, never()).changePassword(newPasswordDTO);
    }
}

package com.mariamkatamashvlii.gym.controller;

import com.mariamkatamashvlii.gym.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;


    @Test
    void testLoginSuccess() throws Exception {
        when(userService.login("user", "pass")).thenReturn(true);

        mockMvc.perform(get("/users/login")
                        .param("username", "user")
                        .param("password", "pass"))
                .andExpect(status().isOk())
                .andExpect(content().string("Logged in successfully"));

        verify(userService, times(1)).login("user", "pass");
    }

    @Test
    void testLoginFailure() throws Exception {
        when(userService.login("user", "wrongpass")).thenReturn(false);

        mockMvc.perform(get("/users/login")
                        .param("username", "user")
                        .param("password", "wrongpass"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid username or password"));

        verify(userService, times(1)).login("user", "wrongpass");
    }

    @Test
    void testPassChangeSuccess() throws Exception {
        when(userService.login("user", "oldpass")).thenReturn(true);

        mockMvc.perform(put("/users/passChange")
                        .param("username", "user")
                        .param("currPassword", "oldpass")
                        .param("newPassword", "newpass"))
                .andExpect(status().isOk())
                .andExpect(content().string("Password Changed Successfully"));

        verify(userService, times(1)).login("user", "oldpass");
        verify(userService, times(1)).passChange("user", "oldpass", "newpass");
    }

    @Test
    void testPassChangeFailure() throws Exception {
        when(userService.login("user", "oldpass")).thenReturn(false);

        mockMvc.perform(put("/users/passChange")
                        .param("username", "user")
                        .param("currPassword", "oldpass")
                        .param("newPassword", "newpass"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid username or password"));

        verify(userService, times(1)).login("user", "oldpass");
        verify(userService, never()).passChange(anyString(), anyString(), anyString());
    }


}

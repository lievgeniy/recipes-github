package recipes.github.recipesproject.controller;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import recipes.github.recipesproject.presentation.dto.UserDTO;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RegistrationControllerTest {

    private final String BASE_URL = "/api/register";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateUserBadRequest() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("");
        userDTO.setPassword("");

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(userDTO)))
                .andExpect(status().isBadRequest());

        userDTO.setEmail("");
        userDTO.setPassword("password");

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(userDTO)))
                .andExpect(status().isBadRequest());

        userDTO.setEmail("email@test.fr");
        userDTO.setPassword("");

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(userDTO)))
                .andExpect(status().isBadRequest());

        userDTO.setEmail("email@test");
        userDTO.setPassword("password");

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(userDTO)))
                .andExpect(status().isBadRequest());


        userDTO.setEmail("email@test.fr");
        userDTO.setPassword("pass");

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(userDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateExistingUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("email@test.fr");
        userDTO.setPassword("password");

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(userDTO)))
                .andExpect(status().isOk());


        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(userDTO)))
                .andExpect(status().isBadRequest());
    }
}

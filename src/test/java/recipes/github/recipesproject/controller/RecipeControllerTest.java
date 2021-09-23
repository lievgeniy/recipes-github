package recipes.github.recipesproject.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.junit.jupiter.api.Assertions.assertEquals;
import recipes.github.recipesproject.business.constants.Constants;
import recipes.github.recipesproject.presentation.dto.RecipeDTO;
import recipes.github.recipesproject.presentation.dto.UserDTO;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RecipeControllerTest {
    private final String BASE_URL = "/api/recipe";
    private final String ID = "/{id}";
    private final String CREATE = "/new";
    private final String SEARCH = "/search";
    private static final String USER = "user@test.fr";
    private static final String USER2 = "user2@test.fr";
    private static final String PASSWORD = "password";

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public static void setup(@Autowired MockMvc mockMvc) throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(USER);
        userDTO.setPassword(PASSWORD);

        mockMvc.perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(userDTO)))
                .andExpect(status().isOk());

        userDTO.setEmail(USER2);
        mockMvc.perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(userDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @Order(1)
    public void testUnauthorizedAccessToAPI() throws Exception {
        mockMvc.perform(get(BASE_URL + ID, 1))
                .andExpect(status().isUnauthorized());

        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setName("Name");
        recipeDTO.setDescription("Category");
        recipeDTO.setDescription("Description");
        recipeDTO.setIngredients(Arrays.asList("ingredient1","ingredient2"));
        recipeDTO.setDirections(Arrays.asList("direction1","direction2"));
        String recipeDTOJson = new Gson().toJson(recipeDTO);

        mockMvc.perform(post(BASE_URL + CREATE)
                        .content(recipeDTOJson))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(delete(BASE_URL + ID, 1))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(put(BASE_URL + ID, 1)
                        .content(recipeDTOJson))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get(BASE_URL + SEARCH)
                        .param(Constants.SEARCH_PARAM_NAME, "tea"))
                .andExpect(status().isUnauthorized());
    }

    @WithAnonymousUser
    @Test
    @Order(2)
    public void testUnauthorizedAccessToAPIWithUser() throws Exception {
        mockMvc.perform(get(BASE_URL + ID, 1))
                .andExpect(status().isUnauthorized());

        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setName("Name");
        recipeDTO.setDescription("Category");
        recipeDTO.setDescription("Description");
        recipeDTO.setIngredients(Arrays.asList("ingredient1","ingredient2"));
        recipeDTO.setDirections(Arrays.asList("direction1","direction2"));
        String recipeDTOJson = new Gson().toJson(recipeDTO);

        mockMvc.perform(post(BASE_URL + CREATE)
                        .content(recipeDTOJson))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(delete(BASE_URL + ID, 1))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(put(BASE_URL + ID, 1)
                        .content(recipeDTOJson))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get(BASE_URL + SEARCH)
                        .param(Constants.SEARCH_PARAM_NAME, "tea"))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser
    @Test
    @Order(3)
    public void testGetRecipeBadRequest() throws Exception {
        mockMvc.perform(get(BASE_URL + ID, "test"))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    @Order(4)
    public void testRecipeNotFound() throws Exception {
        mockMvc.perform(get(BASE_URL + ID, 99999))
                .andExpect(status().isNotFound());

        mockMvc.perform(delete(BASE_URL + ID, 99999))
                .andExpect(status().isNotFound());

        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setName("Name");
        recipeDTO.setCategory("Category");
        recipeDTO.setDescription("Description");
        recipeDTO.setIngredients(Arrays.asList("ingredient1","ingredient2"));
        recipeDTO.setDirections(Arrays.asList("direction1","direction2"));
        String recipeDTOJson = new Gson().toJson(recipeDTO);

        mockMvc.perform(put(BASE_URL + ID, 99999)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(recipeDTOJson))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(USER)
    @Test
    @Order(5)
    public void testCreateRecipe() throws Exception {
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setName("Name");
        recipeDTO.setCategory("Category");
        recipeDTO.setDescription("Description");
        recipeDTO.setIngredients(Arrays.asList("ingredient1","ingredient2"));
        recipeDTO.setDirections(Arrays.asList("direction1","direction2"));
        String recipeDTOJson = new Gson().toJson(recipeDTO);

        mockMvc.perform(post(BASE_URL + CREATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(recipeDTOJson))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists());
    }

    @WithMockUser(USER)
    @Test
    @Order(6)
    public void testCreateAndUpdateRecipe() throws Exception {
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setName("Name");
        recipeDTO.setCategory("Category");
        recipeDTO.setDescription("Description");
        recipeDTO.setIngredients(Arrays.asList("ingredient1","ingredient2"));
        recipeDTO.setDirections(Arrays.asList("direction1","direction2"));
        String recipeDTOJson = new Gson().toJson(recipeDTO);

        MvcResult result = mockMvc.perform(post(BASE_URL + CREATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(recipeDTOJson))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andReturn();

        MvcResult searchResult = mockMvc.perform(get(BASE_URL + SEARCH)
                        .param(Constants.SEARCH_PARAM_NAME, "name"))
                .andExpect(status().isOk())
                .andReturn();

        Type listRecipeDTO = new TypeToken<ArrayList<RecipeDTO>>(){}.getType();
        List<RecipeDTO> recipeDTOList = new Gson().fromJson(searchResult.getResponse().getContentAsString(), listRecipeDTO);
        assertEquals(2, recipeDTOList.size());

        Map<String, Double> map = new Gson().fromJson(result.getResponse().getContentAsString(), Map.class);
        recipeDTO.setName("NewName");
        mockMvc.perform(put(BASE_URL + "/" + map.get("id").intValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(recipeDTOJson))
                .andExpect(status().isNoContent());

    }

    @WithMockUser(USER)
    @Test
    @Order(7)
    public void testCreateAndDeleteRecipe() throws Exception {
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setName("Name");
        recipeDTO.setCategory("Category");
        recipeDTO.setDescription("Description");
        recipeDTO.setIngredients(Arrays.asList("ingredient1","ingredient2"));
        recipeDTO.setDirections(Arrays.asList("direction1","direction2"));
        String recipeDTOJson = new Gson().toJson(recipeDTO);

        MvcResult result = mockMvc.perform(post(BASE_URL + CREATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(recipeDTOJson))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andReturn();

        MvcResult searchResult = mockMvc.perform(get(BASE_URL + SEARCH)
                        .param(Constants.SEARCH_PARAM_NAME, "name"))
                .andExpect(status().isOk())
                .andReturn();

        Type listRecipeDTO = new TypeToken<ArrayList<RecipeDTO>>(){}.getType();
        List<RecipeDTO> recipeDTOList = new Gson().fromJson(searchResult.getResponse().getContentAsString(), listRecipeDTO);
        assertEquals(3, recipeDTOList.size());

        Map<String, Double> map = new Gson().fromJson(result.getResponse().getContentAsString(), Map.class);
        mockMvc.perform(delete(BASE_URL + "/" + map.get("id").intValue()))
                .andExpect(status().isNoContent());

        searchResult = mockMvc.perform(get(BASE_URL + SEARCH)
                        .param(Constants.SEARCH_PARAM_NAME, "name"))
                .andExpect(status().isOk())
                .andReturn();
        recipeDTOList = new Gson().fromJson(searchResult.getResponse().getContentAsString(), listRecipeDTO);
        assertEquals(2, recipeDTOList.size());
    }

    @Test
    @Order(8)
    public void testCreateRecipeUser1AndDeleteUpdateRecipeUser2() throws Exception {
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setName("Name");
        recipeDTO.setCategory("Category");
        recipeDTO.setDescription("Description");
        recipeDTO.setIngredients(Arrays.asList("ingredient1","ingredient2"));
        recipeDTO.setDirections(Arrays.asList("direction1","direction2"));
        String recipeDTOJson = new Gson().toJson(recipeDTO);

        MvcResult result = mockMvc.perform(post(BASE_URL + CREATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(recipeDTOJson)
                        .with(user(USER).password(PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andReturn();

        MvcResult searchResult = mockMvc.perform(get(BASE_URL + SEARCH)
                        .param(Constants.SEARCH_PARAM_NAME, "name")
                        .with(user(USER2).password(PASSWORD)))
                .andExpect(status().isOk())
                .andReturn();

        Type listRecipeDTO = new TypeToken<ArrayList<RecipeDTO>>(){}.getType();
        List<RecipeDTO> recipeDTOList = new Gson().fromJson(searchResult.getResponse().getContentAsString(), listRecipeDTO);
        assertEquals(3, recipeDTOList.size());

        Map<String, Double> map = new Gson()
                .fromJson(result.getResponse().getContentAsString(), Map.class);
        int createdRecipeId = map.get("id").intValue();
        mockMvc.perform(delete(BASE_URL + "/" + createdRecipeId)
                        .with(user(USER2).password(PASSWORD)))
                .andExpect(status().isForbidden());

        recipeDTO.setName("NewName");
        mockMvc.perform(put(BASE_URL + "/" + createdRecipeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(recipeDTOJson)
                        .with(user(USER2).password(PASSWORD)))
                .andExpect(status().isForbidden());

        searchResult = mockMvc.perform(get(BASE_URL + SEARCH)
                        .param(Constants.SEARCH_PARAM_NAME, "name")
                        .with(user(USER).password(PASSWORD)))
                .andExpect(status().isOk())
                .andReturn();
        recipeDTOList = new Gson().fromJson(searchResult.getResponse().getContentAsString(), listRecipeDTO);
        assertEquals(3, recipeDTOList.size());
    }
}

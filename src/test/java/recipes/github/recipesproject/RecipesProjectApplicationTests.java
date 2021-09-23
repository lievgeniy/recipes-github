package recipes.github.recipesproject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import recipes.github.recipesproject.presentation.controller.RecipeController;
import recipes.github.recipesproject.presentation.controller.RegistrationController;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RecipesProjectApplicationTests {

	@Autowired
	RecipeController recipeController;

	@Autowired
	RegistrationController registrationController;

	@Test
	void contextLoads() throws Exception {
		assertThat(recipeController).isNotNull();
		assertThat(registrationController).isNotNull();
	}

}

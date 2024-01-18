package api.rest.variant2.integrationTest;

import api.rest.variant2.steps.PetsStep;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.tan.data.dto.pet.PetDTO;

@Epic("Epic Post Contorller")
@Feature("Feature Post Contorller")
@Story("Post Contorller")
@DisplayName("Тестирование запроса с Post методом")
public class PostRequestTest {

    private PetsStep petsStep = new PetsStep();

    @Test
    @Tags(value = {@Tag("RegressAPI"), @Tag("RegressALL"), @Tag("PostPet")})
    @DisplayName("Тестирование тестового запроса Post с проверкой status code = 200")
    public void postRequestCheckStatusCode() {
        PetDTO petDTO = petsStep.preparePetDto();
        petsStep.createPet(petDTO);
    }
}


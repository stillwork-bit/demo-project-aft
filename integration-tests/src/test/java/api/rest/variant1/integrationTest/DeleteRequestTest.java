package api.rest.variant1.integrationTest;

import api.rest.variant1.steps.PetsStep;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.tan.data.dto.pet.PetDTO;

@Epic("Epic Delete Contorller")
@Feature("Feature Delete Contorller")
@Story("Delete Contorller")
@DisplayName("Тестирование запроса с Delete методом")
public class DeleteRequestTest {

    private PetsStep petsStep = new PetsStep();

    @Test
    @Tags(value = {@Tag("RegressAPI"), @Tag("RegressALL"), @Tag("DeletePet")})
    @DisplayName("Тестирование запроса Delete c удалением Pet")
    public void deleteRequestCheckStatusCode() {
        PetDTO petDTO = petsStep.preparePetDto();
        int id = petsStep.createPet(petDTO);
        petsStep.deletePet(id);
    }
}


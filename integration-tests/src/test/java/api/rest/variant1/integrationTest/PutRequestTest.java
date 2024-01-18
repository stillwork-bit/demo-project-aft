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

@Epic("Epic Put Contorller")
@Feature("Feature Put Contorller")
@Story("Put Contorller")
@DisplayName("Тестирование запроса с Put методом")
public class PutRequestTest {

    private PetsStep petsStep = new PetsStep();

    //тест падает т.к. не совпадает ожидаемый SC для построения отчета SwaggerCoderage
    @Test
    @Tags(value = {@Tag("RegressAPI"), @Tag("RegressALL"), @Tag("PutPet")})
    @DisplayName("Тестирование тестового запроса Put c обновлением данных Pet по полю job")
    public void putRequestCheckStatusCodeAndJsonBody() {
        PetDTO oldPetDTO = petsStep.preparePetDto();
        PetDTO newPetDTO = oldPetDTO;
        newPetDTO.setStatus("newStatus");

        int id = petsStep.createPet(oldPetDTO);
        petsStep.updatePet(id, newPetDTO);
    }
}


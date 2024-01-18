package api.rest.variant1.integrationTest;

import api.rest.variant1.steps.PetsStep;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import java.time.Duration;

@Epic("Epic Get Contorller")
@Feature("Feature Get Contorller")
@Story("Get Contorller")
@DisplayName("Тестирование запроса с GET методом")
public class GetRequestTest {

    private PetsStep petsStep = new PetsStep();

    @Test
    @Tags(value = {@Tag("RegressAPI"), @Tag("RegressALL"), @Tag("GetPet")})
    @DisplayName("Тестирование запроса Get c проверкой status code = 200")
    public void getRequestCheckStatusCode() {
        petsStep.getPetsCheckStatusCode(2);
    }

    @Test
    @Tags(value = {@Tag("RegressAPI"), @Tag("RegressALL"), @Tag("GetPet")})
    @DisplayName("Тестирование запроса Get c проверкой key/value по полям id, category.id, category.name")
    public void getRequestCheckResponseJsonBody() {
        petsStep.getPetsCheckReponseBody(2);
    }

    @Test
    @Tags(value = {@Tag("RegressAPI"), @Tag("RegressALL"), @Tag("GetPet")})
    @DisplayName("Тестирование запроса Get c валидацией ответа по json схеме")
    public void getRequestCheckResponseWithJsonSchema() {
        petsStep.getPetCheckReponseBodyWithJsonChema(2);
    }

    @Test
    @Tags(value = {@Tag("RegressAPI"), @Tag("RegressALL"), @Tag("GetPet")})
    @DisplayName("Пример умного ожидания с проверкой согласно настроенному правилу WAIT")
    public void exampleCheckUntilAsserted() {
        petsStep.untilAsserted(Duration.ofSeconds(10).getSeconds());
    }
}


package api.rest.variant2.steps;

import api.rest.variant2.steps.UtilStep;
import api.rest.variant2.ctrl.HttpService;
import io.qameta.allure.Step;
import org.awaitility.Duration;
import org.awaitility.core.ConditionFactory;
import org.hamcrest.Matchers;
import org.tan.data.dto.pet.PetDTO;

import static api.rest.variant2.specification.Specifications.responseSpecificationScOk;
import static api.rest.variant2.ctrl.Method.*;
import static helper.Endpoints.PET_ENDPOINT;
import static helper.Endpoints.PET_ID_ENDPOINT;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

public class PetsStep {

    private final ConditionFactory WAIT = await()
            .atMost(Duration.FIVE_MINUTES)
            .pollInterval(Duration.ONE_SECOND)
            .pollDelay(Duration.FIVE_HUNDRED_MILLISECONDS);

    HttpService<PetDTO> httpService = new HttpService();

    @Step("Умное ожидания с проверкой согласно настроенному правилу WAIT")
    public void untilAsserted(long time) {
        WAIT.untilAsserted(() -> {
            Thread.sleep(time);
            assertThat(10).isEqualTo(10);
        });
    }

    @Step("Создание Pet с взвращением id")
    public int createPet(PetDTO petDTO) {
        return httpService.doRequest(POST, PET_ENDPOINT, petDTO, "")
                .then()
                .spec(responseSpecificationScOk())//---> Указание ResponseSpecification
                .extract()
                .response()
                .body()
                .path("id");
    }


    @Step("Получение pet с id = {id} с проверкой статус кода")
    public void getPetsCheckStatusCode(int id) {
        httpService.doRequest(GET_ID, PET_ID_ENDPOINT, null, String.valueOf(id))
                .then()
                .spec(responseSpecificationScOk());//---> Указание ResponseSpecification
    }

    @Step("Получение pet с id = {id} с проверкой body")
    public void getPetsCheckReponseBody(int id) {
        httpService.doRequest(GET_ID, PET_ID_ENDPOINT, null, String.valueOf(id))
                .then()
                .spec(responseSpecificationScOk())//---> Указание ResponseSpecification
                .assertThat()
                .body("id", Matchers.is(22))//---> Проверка Body по key и value в json
                .body("category.id", Matchers.is(3))//---> Проверка Body по key и value в json
                .body("category.name", Matchers.is("lar"));//---> Проверка Body по key и value в json

    }

    @Step("Получение pet с id = {id} с проверкой body с jsonSchema")
    public void getPetCheckReponseBodyWithJsonChema(int id) {
        httpService.doRequest(GET_ID, PET_ID_ENDPOINT, null, String.valueOf(id))
                .then()
                .spec(responseSpecificationScOk())//---> Указание ResponseSpecification
                .assertThat()
                .body(matchesJsonSchemaInClasspath("SchemaPetsById.json"));//---> Валидация Response json по Json Schema. Сгенерировать Json Schema можно https://www.liquid-technologies.com/online-json-to-schema-converter и далее создать файл SchemaPetsById.json в каталоге src/test/resources. !!!Внимание по умолчанию вычитывается из папки resources помеченной как ресурсы тестов в проекте
    }

    @Step("Подготовка body с сериалиализацией body с Pet")
    public PetDTO preparePetDto() {
        return new UtilStep<PetDTO>().readFileAndSetObject(PetDTO.class);
    }

}

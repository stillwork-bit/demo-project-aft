package api.rest.variant1.steps;

import api.rest.variant1.ctrl.DeletePetsCtrl;
import api.rest.variant1.ctrl.GetPetsCtrl;
import api.rest.variant1.ctrl.PostPetsCtrl;
import api.rest.variant1.ctrl.PutPetsCtrl;
import io.qameta.allure.Step;
import org.awaitility.Duration;
import org.awaitility.core.ConditionFactory;
import org.hamcrest.Matchers;
import org.tan.data.dto.pet.PetDTO;

import static api.rest.variant1.specification.Specifications.responseSpecificationScOk;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

public class PetsStep {

    private final ConditionFactory WAIT = await()
            .atMost(Duration.FIVE_MINUTES)
            .pollInterval(Duration.ONE_SECOND)
            .pollDelay(Duration.FIVE_HUNDRED_MILLISECONDS);
    private GetPetsCtrl getPetsCtrl = new GetPetsCtrl();
    private PostPetsCtrl postPetsCtrl = new PostPetsCtrl();
    private DeletePetsCtrl deletePetsCtrl = new DeletePetsCtrl();
    private PutPetsCtrl putPetsCtrl = new PutPetsCtrl();

    @Step("Умное ожидания с проверкой согласно настроенному правилу WAIT")
    public void untilAsserted(long time){
        WAIT.untilAsserted(() -> {
            Thread.sleep(time);
            assertThat(10).isEqualTo(10);
        });
    }

    @Step("Создание Pet с взвращением id")
    public int createPet(PetDTO petDTODTO) {
        return postPetsCtrl.createPet(petDTODTO)
                .then()
                .spec(responseSpecificationScOk())//---> Указание ResponseSpecification
                .extract()
                .response()
                .body()
                .path("id");
    }

    @Step("Создание Pet с взвращением id и проверкой ответа по полям")
    public int createPetCheckResponseBody(PetDTO petDTO) {
        return postPetsCtrl.createPet(petDTO)
                .then()
                .spec(responseSpecificationScOk())//---> Указание ResponseSpecification
                .assertThat()
                .body("id", Matchers.is(10))//---> Проверка Body по key и value в json
                .body("category.id", Matchers.is(1))//---> Проверка Body по key и value в json
                .body("category.name", Matchers.is("Dogs"))//---> Проверка Body по key и value в json
                .extract()
                .response()
                .body()
                .path("id");
    }

    @Step("Получение pet с id = {id} с проверкой статус кода")
    public void getPetsCheckStatusCode(int id) {
        getPetsCtrl.getPet(id)
                .then()
                .spec(responseSpecificationScOk());//---> Указание ResponseSpecification
    }

    @Step("Получение pet с id = {id} с проверкой body")
    public void getPetsCheckReponseBody(int id) {
        getPetsCtrl.getPet(id).then()
                .spec(responseSpecificationScOk())//---> Указание ResponseSpecification
                .assertThat()
                .body("id", Matchers.is(2))//---> Проверка Body по key и value в json
                .body("category.id", Matchers.is(1))//---> Проверка Body по key и value в json
                .body("category.name", Matchers.is("Dogs"));//---> Проверка Body по key и value в json

    }

    @Step("Получение pet с id = {id} с проверкой body с jsonSchema")
    public void getPetCheckReponseBodyWithJsonChema(int id) {
        getPetsCtrl.getPet(id).then()
                .spec(responseSpecificationScOk())//---> Указание ResponseSpecification
                .assertThat()
                .body(matchesJsonSchemaInClasspath("SchemaPetsById.json"));//---> Валидация Response json по Json Schema. Сгенерировать Json Schema можно https://www.liquid-technologies.com/online-json-to-schema-converter и далее создать файл SchemaPetsById.json в каталоге src/test/resources. !!!Внимание по умолчанию вычитывается из папки resources помеченной как ресурсы тестов в проекте
    }

    @Step("Удаление pet с id {id}")
    public void deletePet(int id) {
        deletePetsCtrl.deletePet(id)
                .then()
                .spec(responseSpecificationScOk());//---> Указание ResponseSpecification
    }

    @Step("Обновление Pet с id {id}")
    public void updatePet(int id, PetDTO petNew) {
        putPetsCtrl.updatePet(id, petNew).then()
                .spec(responseSpecificationScOk())//---> Указание ResponseSpecification
                .assertThat()
                .body("status", Matchers.is("newStatus"));
    }

    @Step("Подготовка body с сериалиализацией body с Pet")
    public PetDTO preparePetDto(){
        return new UtilStep<PetDTO>().readFileAndSetObject(PetDTO.class);
    }
}

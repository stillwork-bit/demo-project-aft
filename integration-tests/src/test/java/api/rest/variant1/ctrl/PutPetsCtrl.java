package api.rest.variant1.ctrl;

import io.restassured.response.Response;
import org.tan.data.dto.pet.PetDTO;

import static api.rest.variant1.specification.Specifications.requestSpecification;
import static helper.Endpoints.PET_ENDPOINT;
import static helper.Endpoints.PET_ID_ENDPOINT;
import static io.restassured.RestAssured.given;

public class PutPetsCtrl {

    public Response updatePet(int id, PetDTO petNew) {
        return given().spec(requestSpecification())//---> Указание RequestSpecification для формирования request
                .pathParam("id", id)
                .body(petNew)//---> Body для запроса
                .put(PET_ID_ENDPOINT);//---> Endpoint для выполнения запроса PUT
    }
}

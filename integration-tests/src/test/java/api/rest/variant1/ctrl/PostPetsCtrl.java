package api.rest.variant1.ctrl;

import io.restassured.response.Response;
import org.tan.data.dto.pet.PetDTO;

import static api.rest.variant1.specification.Specifications.requestSpecification;
import static helper.Endpoints.PET_ENDPOINT;
import static io.restassured.RestAssured.given;

public class PostPetsCtrl {

    public Response createPet(PetDTO petDTO) {
        return given().spec(requestSpecification()) //---> Указание RequestSpecification для формирования request
                .body(petDTO)//---> Body для запроса
                .post(PET_ENDPOINT);//---> Endpoint для выполнения запроса POST
    }
}

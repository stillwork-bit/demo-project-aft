package api.rest.variant1.ctrl;

import io.restassured.response.Response;

import static api.rest.variant1.specification.Specifications.requestSpecification;
import static helper.Endpoints.PET_ENDPOINT;
import static helper.Endpoints.PET_ID_ENDPOINT;
import static io.restassured.RestAssured.given;

public class GetPetsCtrl {

    public Response getPet(int id) {
        return given().spec(requestSpecification())//---> Указание RequestSpecification для формирования request
                .pathParam("id", id)
                .get(PET_ID_ENDPOINT);//---> Endpoint для выполнения запроса GET
    }
}

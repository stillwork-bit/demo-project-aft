package api.rest.variant2.ctrl;

import io.restassured.response.Response;

import static api.rest.variant2.specification.Specifications.requestSpecification;
import static io.restassured.RestAssured.given;

public class HttpService<T> {

    /**
    * Пример создания общего контроллера для контроллеров API
    */

    public Response doRequest(Method method, String endpoint, T body, String idOrPathParam) {

        Response response = null;
        switch (method){
            case POST:
                response = given().spec(requestSpecification())
                        .body(body)
                        .post(endpoint);
                break;

            case DELETE:
                response = given().spec(requestSpecification())
                        .pathParam("id", idOrPathParam)
                        .delete(endpoint + idOrPathParam);
                break;

            case GET_ID:
                response = given().spec(requestSpecification())
                        .pathParam("id", idOrPathParam)
                        .get(endpoint + idOrPathParam);
                break;

            case GET_PATH_PARAM:
                response = given().spec(requestSpecification())
                        .pathParams("id", idOrPathParam)
                        .get(endpoint);
                break;

            case PUT:
                response = given().spec(requestSpecification())
                        .pathParam("id", idOrPathParam)
                        .body(body)
                        .put(endpoint + idOrPathParam);
                break;
        }
        return response;
    }
}

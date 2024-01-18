package api.rest.variant2.specification;

import com.github.viclovsky.swagger.coverage.SwaggerCoverageRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;

import static helper.Constants.BASE_URL;
import static io.restassured.http.ContentType.JSON;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.Matchers.lessThanOrEqualTo;


public class Specifications {

    public static RequestSpecification requestSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)//---> Cтартовая URL
//                .setRelaxedHTTPSValidation()//---> Пропуск валидации сертификатов
                .setContentType(JSON)//---> Установка Content Type
                .setAccept(JSON)//---> Установка Accept
                .setRelaxedHTTPSValidation()
                .addFilter(new SwaggerCoverageRestAssured())//---> Установка фильтра для SwaggerCoverageRestAssured
                .build();
    }

    public static ResponseSpecification responseSpecificationScOk() {
        return new ResponseSpecBuilder()
                .log(LogDetail.STATUS)//---> Уровень логирования
                .expectContentType(JSON)//---> Ожидаемый Content Type
                .expectStatusCode(HttpStatus.SC_OK)//---> Ожидаемый Status Code
                .expectResponseTime(lessThanOrEqualTo(3L), SECONDS)//---> Ожидаемое время ответа максимум 3 секунды
                .build();
    }
}

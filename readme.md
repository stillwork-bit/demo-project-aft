

# Описание тестового фреймворка для написания api & ui автотестов

### 1. Описание модулей проекта
#### В проекте есть 3 модуля:
- dev-app - пример вспогательного приложения для автоматизации тестирования для чтения/записи в topic kafka, чтение/обновления/вставки записи в БД 
- integration-tests - пример написания api/ui автотестов  
- common - модуль с DTO для модулей dev-app и integration-tests

### 2. Запуск dev-app
- 2.1.Для запуска подготовьте подключение к БД и kafka
  - 2.1.1 Подготовка БД:
    - Создайте БД test_db
    - Создайте схему test_schema
    - Создайте таблицу test_msg из скрипта .\demo-project-aft\dev-app\src\main\resources\sql\scrypt.sql
    - Настройте подключение к БД в .\demo-project-aft\dev-app\src\main\resources\application.yml
  - 2.1.2 Подготовка kafka:
    - Проверьте подключение к kafka через kafka-tool
    - Настройте подключение к kafka с указанием точки подключения с zookeeper & bootstrap-servers в .\demo-project-aft\dev-app\src\main\resources\application.yml
    
- 2.2 Настройте конфигурацию запуска в IDE для запуска dev-app
  - 2.2.1 установите путь к application.yml для запуска через VM Options или Program arguments. Пример для Program arguments:  --spring.config.location=file:C:\[path to application.yml]\demo-project-aft\dev-app\src\main\resources\application.yml

### 3. Описание integration-tests
- 3.1.api тесты описаны в каталоге api
  - rest
    - variant1 - Пример реализации написания api тестов с описанием и переисопльзованием step. ctrl - описывается для каждого контроллера api
    - variant2 - Пример реализации написания api тестов с описанием и переисопльзованием step. ctrl - описывается в HttpServiсe
    ![allureReportExample.png](integration-tests%2Fsrc%2Ftest%2Fresources%2Futility%2FallureReportExample.png)
  - kafka - пример реализации взаимодействия с kafka для чтения/записи в topic
- 3.2.ui тесты описаны в каталоге UI
- 3.3.вспомогательные классы в каталоге helper
  - Constants - переменные для заполнения в контуре тестирования LOCAL/DEV/IFT/PreProd
  - Endpoints - эндпоинты для api
  - PropertiesFileReader - пример для подключения по ssh на сервер
  - ReadSystemVariables - чтение переменных для заполняния constants из системной переменной ENV_CONFIG_FILE 
  -  TrippleDes - класс с примерои шифрования и дешифрования. Как правило шифрование реализовано в проекте devops в play-book

### 4. Запуск автотестов
Для запуска автотестов конфигурации записка требуется передать системную переменную с конфигурационных файлом для запуска: ENV_CONFIG_FILE=application-local.conf

### 5. Описание подключения swagger-coverage в тестовый проект
5.1. Добавьте зависимость в pom   
```
<dependency>
<groupId>com.github.viclovsky.swagger.coverage</groupId>
<artifactId>swagger-coverage-rest-assured</artifactId>
<version>1.3.5</version>
</dependency>
```
5.2 Подключите в RequestSpecification RestAssured listener .addFilter(new SwaggerCoverageRestAssured())от swagger-coverage-rest-assured
```
Подключение new SwaggerCoverageRestAssured()
public static RequestSpecification requestSpecification() {
    return new RequestSpecBuilder().setBaseUri(BASE_URL)//---> Cтартовая URL
                                   .setRelaxedHTTPSValidation()//---> Пропуск валидации сертификатов
                                   .setContentType(JSON)//---> Установка Content Type
                                   .setAccept(JSON)//---> Установка Accept
                                   .setRelaxedHTTPSValidation()
                                   .addFilter(new SwaggerCoverageRestAssured())//---> Установка фильтра для SwaggerCoverageRestAssured
                                   .build();
}

Использование requestSpecification() в запросе
public Response deletePet(int id) {
        return given().spec(requestSpecification())
                .pathParam("id", id)
                .delete(PET_ID_ENDPOINT);
    }
    
См. пример подключения в модуле integration-tests  
```
5.3 После выполнения автотестов будут собираться meta файлы с результатом прохождения в каталог .\demo-project-aft\integration-tests\swagger-coverage-output 

5.4 Выполнение расчета покрытие автотестов:
- Распакуйте из архива бинарный файл .\demo-project-aft\integration-tests\src\test\resources\utility\swagger-coverage-commandline-1.5.0.zip
- Подготовьте swagger specigication из рабочего проекта
- Переложите swagger specigication (Например openapi.json) в каталог с swagger-coverage-commandline
- Переложите мета файла после прогона автотестов swagger-coverage-output  в каталог с swagger-coverage-commandline: ```./swaggerCoverage/swagger-coverage-commandline-1.5.0/bin```
- Выполните запуск swagger-coverage-commandline из каталога след. командой: 
```./swaggerCoverage/swagger-coverage-commandline-1.5.0/bin/swagger-coverage-commandline -s openapi.json -i swagger-coverage-output```
- Будет сформирован swagger-coverage-report.html с описанием покрытия api тестов по swagger specification. Пример:
![swaggerCoverageExample.JPG](integration-tests%2Fsrc%2Ftest%2Fresources%2Futility%2FswaggerCoverageExample.JPG)

### 6. Запуск автотестов в docker
Пример по запуску api тестов реализован в docker-compose & dockerfile
Примечание: Передача системных переменных для запуска автотестов выполняется либо через args либо через .env на усмотрение devops


package ui.configuration;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static com.codeborne.selenide.Selenide.open;
import static helper.Constants.BASE_URL_UI;
import static helper.Constants.SELENIUM_SERVER;

//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ConfigurationSelenide {

    public static void configurationRemote() {
        /**
         * Настройка конфигурации запуска
         */
        Configuration.remote = SELENIUM_SERVER; //example "http://kubernetes.docker.internal:4444/wd/hub"
        Configuration.baseUrl = BASE_URL_UI;
        Configuration.browser = "chrome";
        /*Configuration.browserVersion = "95"; ---> Указание определенной версии для тестов */
        Configuration.pageLoadStrategy = "eager";
        Configuration.holdBrowserOpen = true; //---> параметр для браузера чтобы оставить его открытым после выполнения всех тестов
        Configuration.reportsFolder = "test-result/reports";
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true)
                .savePageSource(true));
        System.setProperty("chromeoptions.args", "--no-sandbox");
        System.setProperty("chromeoptions.args", "--ignore-certificate-errors");
        Configuration.browserSize = "1920x1080";

        /**
         * Включение vnc
         */
        //Создаём объект класса DesiredCapabilities, используется как настройка  вашей конфигурации с помощью пары ключ-значение
//        DesiredCapabilities capabilities = new DesiredCapabilities();
//        //Включить поддержку отображения экрана браузера во время выполнения теста
//        capabilities.setCapability("enableVNC",true);
//        //Включение записи видео в процессе выполнения тестов
//        capabilities.setCapability("enableVideo",true);
//        //Переопределяем Browser capabilities
//        Configuration.browserCapabilities = capabilities;
        open("/");
    }

    @BeforeAll
    public static void setUp() {
        configurationRemote();
    }

    @AfterAll
    public static void tearDown() {
        Selenide.closeWebDriver();
    }
}

package ui.uiTest;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import ui.configuration.ConfigurationSelenide;
import ui.widgets.XpathLocators;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.open;
import static helper.Constants.BASE_URL_UI;

@Epic("Epic XPATH ui test")
@Feature("Feature XPATH ui test")
@Story("Get XPATH ui test")
@DisplayName("Тестирование XPATH ui test")
public class XpathTest extends ConfigurationSelenide {
    private XpathLocators xpath = new XpathLocators();
    private static String fullHeaderButton = "Naughty or Nice List";

    @BeforeEach
    public void prepareForTest() {
        open(BASE_URL_UI);
    }

    @Test
    @Tags(value = {@Tag("RegressUI"), @Tag("RegressALL"), @Tag("XpathTest")})
    @DisplayName("Переход на главную страницу c локатором по имени элемента и атрибуту класса")
    public void fullHeaderHome() {
        xpath.approveCookieWindow();
        xpath.homePage.shouldBe(visible, Duration.ofSeconds(10));
    }

    @Test
    @Tags(value = {@Tag("RegressUI"), @Tag("RegressALL"), @Tag("XpathTest")})
    @DisplayName("Поиск по абсолютному совпадению заголовка 'Naughty or Nice List' кнопки Naughty or Nice List")
    public void fullHeaderButton() {
        xpath.fullHeaderButton.shouldBe(visible, Duration.ofSeconds(10))
                              .shouldHave(ownText(fullHeaderButton));
    }

    @Test
    @Tags(value = {@Tag("RegressUI"), @Tag("RegressALL"), @Tag("XpathTest")})
    @DisplayName("Поиск по частичному совпадению заголовка 'ghty or Nice' кнопки Naughty or Nice List")
    public void partialHeaderButton() {
        xpath.partialHeaderButton.shouldBe(visible, Duration.ofSeconds(10))
                                 .shouldHave(ownText(fullHeaderButton));
    }

    @Test
    @Tags(value = {@Tag("RegressUI"), @Tag("RegressALL"), @Tag("XpathTest")})
    @DisplayName("Поиск кнопки по атрибуту href с проверкой перехода на страницу Naughty Or NiceList")
    public void openNaughtyOrNiceListByClassAndHrefButton() {
        xpath.openNaughtyOrNiceListByClassAndHrefButton.shouldBe(visible, Duration.ofSeconds(10))
                                                       .click();
        xpath.headerNaughtyOrNiceListPage.shouldBe(visible, Duration.ofSeconds(10));
    }

    @Test
    @Tags(value = {@Tag("RegressUI"), @Tag("RegressALL"), @Tag("XpathTest")})
    @DisplayName("Поиск по частичному совпадению значения атрибута")
    public void tableQuestionsByPartialIdField() {
        xpath.openNaughtyOrNiceListByClassAndHrefButton.shouldBe(visible, Duration.ofSeconds(10))
                                                       .click();
        xpath.tableQuestionsByPartialIdField.shouldBe(visible, Duration.ofSeconds(10))
                                            .click();
    }

    @Test
    @Tags(value = {@Tag("RegressUI"), @Tag("RegressALL"), @Tag("XpathTest")})
    @DisplayName("Поиск по id элемента")
    public void searchHeaderTopByIdField() {
        xpath.openNaughtyOrNiceListByClassAndHrefButton.shouldBe(visible, Duration.ofSeconds(10))
                                                       .click();
        xpath.headerTopByIdField.shouldBe(visible, Duration.ofSeconds(10));
    }

    @Test
    @Tags(value = {@Tag("RegressUI"), @Tag("RegressALL"), @Tag("XpathTest")})
    @DisplayName("Поиск по индексу в таблице, фильтр элемента по букве 'i', далее взять элемент по совпадению с индексом 0 и найти элемент input и кликнуть его")
    public void searchTextAndGoParentRadioButton() {
        xpath.openNaughtyOrNiceListByClassAndHrefButton.shouldBe(visible, Duration.ofSeconds(10))
                                                       .click();
        xpath.searchElementAndFilterAndClickRadioButton.filterBy(text("i"))
                                                       .get(0)
                                                       .find(By.xpath("./input"))
                                                       .click();
    }

    @Test
    @Tags(value = {@Tag("RegressUI"), @Tag("RegressALL"), @Tag("XpathTest")})
    @DisplayName("Поиск элемента по id, далее переход к его родителю и сравнение ожидаемого результата")
    public void searchElementAndGoToParent() {
        xpath.openNaughtyOrNiceListByClassAndHrefButton.shouldBe(visible, Duration.ofSeconds(10))
                                                       .click();
//        xpath.searchElementAndGoToParent.getId().contains("naughtynice");
    }
}

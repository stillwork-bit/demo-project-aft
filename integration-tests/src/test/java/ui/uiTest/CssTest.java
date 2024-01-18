package ui.uiTest;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import ui.configuration.ConfigurationSelenide;
import ui.widgets.CssLocators;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.open;
import static helper.Constants.BASE_URL_UI;

@Epic("Epic CSS ui test")
@Feature("Feature CSS ui test")
@Story("Get CSS ui test")
@DisplayName("Тестирование CSS ui test")
public class CssTest extends ConfigurationSelenide {
    private CssLocators cssLocators = new CssLocators();
    private String fullHeaderButton = "Naughty or Nice List";

    @BeforeEach
    public void prepareForTest() {
        open(BASE_URL_UI);
    }

    /**
     * Примеры операций описана действием без абстракции step
     * При написании промышленных тестов требуется оформлять операциии в step для их переиспользования
     */

    @Test
    @Tags(value = {@Tag("RegressUI"), @Tag("RegressALL"), @Tag("CssTest")})
    @DisplayName("Переход на главную страницу c локатором по имени элемента и атрибуту класса")
    public void fullHeaderHome() {
        cssLocators.approveCookieWindow();
        cssLocators.homePage.shouldBe(visible, Duration.ofSeconds(10));
    }

    @Test
    @Tags(value = {@Tag("RegressUI"), @Tag("RegressALL"), @Tag("CssTest")})
    @DisplayName("Поиск по абсолютному совпадению заголовка 'Naughty or Nice List' кнопки Naughty or Nice List")
    public void fullHeaderButton() {
        cssLocators.fullHeaderButton.shouldBe(Condition.visible, Duration.ofSeconds(10))
                                    .shouldHave(ownText(fullHeaderButton));
    }

    @Test
    @Tags(value = {@Tag("RegressUI"), @Tag("RegressALL"), @Tag("CssTest")})
    @DisplayName("Поиск по частичному совпадению заголовка 'ghty or Nice' кнопки Naughty or Nice List")
    public void partialHeaderButton() {
        cssLocators.partialHeaderButton.shouldBe(visible, Duration.ofSeconds(10))
                                       .shouldHave(ownText(fullHeaderButton));
    }

    @Test
    @Tags(value = {@Tag("RegressUI"), @Tag("RegressALL"), @Tag("CssTest")})
    @DisplayName("Поиск кнопки по атрибуту href с проверкой перехода на страницу Naughty Or NiceList")
    public void openNaughtyOrNiceListByClassAndHrefButton() {
        cssLocators.openNaughtyOrNiceListByClassAndHrefButton.shouldBe(visible, Duration.ofSeconds(10))
                                                             .click();
        cssLocators.headerNaughtyOrNiceListPage.shouldBe(visible, Duration.ofSeconds(10));
    }

    @Test
    @Tags(value = {@Tag("RegressUI"), @Tag("RegressALL"), @Tag("CssTest")})
    @DisplayName("Поиск по частичному совпадению значения атрибута")
    public void tableQuestionsByPartialIdField() {
        cssLocators.openNaughtyOrNiceListByClassAndHrefButton.shouldBe(visible, Duration.ofSeconds(10))
                                                             .click();
        cssLocators.tableQuestionsByPartialIdField.shouldBe(visible, Duration.ofSeconds(10))
                                                  .click();
    }

    @Test
    @Tags(value = {@Tag("RegressUI"), @Tag("RegressALL"), @Tag("CssTest")})
    @DisplayName("Поиск по id элемента")
    public void searchHeaderTopByIdField() {
        cssLocators.openNaughtyOrNiceListByClassAndHrefButton.shouldBe(visible, Duration.ofSeconds(10))
                                                             .click();
        cssLocators.headerTopByIdField.shouldBe(visible, Duration.ofSeconds(10));
    }

    @Test
    @Tags(value = {@Tag("RegressUI"), @Tag("RegressALL"), @Tag("CssTest")})
    @DisplayName("Поиск по индексу в таблице, фильтр элемента по букве 'i', далее взять элемент по совпадению с индексом 0 и найти элемент input и кликнуть его")
    public void searchTextAndGoParentRadioButton() {
        cssLocators.openNaughtyOrNiceListByClassAndHrefButton.shouldBe(visible, Duration.ofSeconds(10))
                                                             .click();
        cssLocators.searchElementAndFilterAndClickRadioButton.filterBy(text("i"))
                                                             .get(0)
                                                             .find(By.xpath("./input"))
                                                             .click();
    }
}

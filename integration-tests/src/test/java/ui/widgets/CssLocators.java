package ui.widgets;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CssLocators {

    public SelenideElement homePage = $(By.cssSelector("img[class='untilmerry']"));
    public SelenideElement fullHeaderButton = $(By.cssSelector("a[href='/Den/list.asp'][class='home-link']"));
    public SelenideElement partialHeaderButton = $(By.cssSelector("a[href='/Den/list.asp'][class*='-link']"));
    public SelenideElement openNaughtyOrNiceListByClassAndHrefButton = $(By.cssSelector("a[href='/Den/list.asp'][class='home-btn']"));
    public SelenideElement tableQuestionsByPartialIdField = $(By.cssSelector("form[id*='uest']"));
    public SelenideElement headerTopByIdField = $(By.cssSelector("#top "));
    public ElementsCollection searchElementAndFilterAndClickRadioButton = $$(By.cssSelector("#questions > ul:nth-child(1) > li"));

    public SelenideElement headerNaughtyOrNiceListPage = $(By.cssSelector("img[src='../images/den/title_list.jpg']"));

    public void approveCookieWindow() {
        SelenideElement cookieButton = $(By.cssSelector("a[onclick='javascript:setnocookiecookie()']"));
        if (cookieButton.shouldBe(visible, Duration.ofSeconds(10)).exists()) {
            cookieButton.click();
        }
    }
}

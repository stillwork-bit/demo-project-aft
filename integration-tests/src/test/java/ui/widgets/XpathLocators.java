package ui.widgets;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class XpathLocators {
    public SelenideElement homePage = $(By.xpath("//img[@class='untilmerry']"));
    public SelenideElement fullHeaderButton = $(By.xpath("//a[text()='Naughty or Nice List']"));
    public SelenideElement partialHeaderButton = $(By.xpath("//a[contains(text(),'ghty or Nice')]"));
    public SelenideElement openNaughtyOrNiceListByClassAndHrefButton = $(By.xpath("//a[@href='/Den/list.asp'] [@class='home-btn']"));
    public SelenideElement tableQuestionsByPartialIdField = $(By.xpath("//form[contains(@id,'uest')]"));
    public SelenideElement headerTopByIdField = $(By.xpath("//div[@id='top']"));
    public ElementsCollection searchElementAndFilterAndClickRadioButton = $$(By.xpath("//form[@id='questions']/ul[1]//li"));
    public SelenideElement searchElementAndGoToParent = $(By.xpath("//form[@id='questions']/.."));

    public SelenideElement headerNaughtyOrNiceListPage = $(By.xpath("//img[@src='../images/den/title_list.jpg']"));

    public void approveCookieWindow(){
        SelenideElement cookieButton = $(By.xpath("//a[@href='javascript:void(0)']"));
        if (cookieButton.shouldBe(visible, Duration.ofSeconds(10)).exists()){
            cookieButton.click();
        }
    }
}

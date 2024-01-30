package model;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ScooterMainPage {
    //Кнопки "Заказать"
    private static final By[] orderButtons = new By[]{
            By.className("Button_Button__ra12g"),
            By.xpath("//button[@class='Button_Button__ra12g Button_Middle__1CSJM']")};

    //Кнопки раздела "Вопросы о важном"
    private static final By[] importantQuestionsButtons = new By[]{
            By.xpath("//*[@id=\"accordion__heading-0\"]"),
            By.xpath("//*[@id=\"accordion__heading-1\"]"),
            By.xpath("//*[@id=\"accordion__heading-2\"]"),
            By.xpath("//*[@id=\"accordion__heading-3\"]"),
            By.xpath("//*[@id=\"accordion__heading-4\"]"),
            By.xpath("//*[@id=\"accordion__heading-5\"]"),
            By.xpath("//*[@id=\"accordion__heading-6\"]"),
            By.xpath("//*[@id=\"accordion__heading-7\"]")
    };
    //Ответы раздела "Вопросы о важном"
    private static final By[] importantQuestionsAnswers = new By[]{
            By.xpath("//*[@id=\"accordion__panel-0\"]/p"),
            By.xpath("//*[@id=\"accordion__panel-1\"]/p"),
            By.xpath("//*[@id=\"accordion__panel-2\"]/p"),
            By.xpath("//*[@id=\"accordion__panel-3\"]/p"),
            By.xpath("//*[@id=\"accordion__panel-4\"]/p"),
            By.xpath("//*[@id=\"accordion__panel-5\"]/p"),
            By.xpath("//*[@id=\"accordion__panel-6\"]/p"),
            By.xpath("//*[@id=\"accordion__panel-7\"]/p")
    };
    WebDriver driver;

    public ScooterMainPage(WebDriver driver) {
        this.driver = driver;
    }


    //Клик кнопки "Заказать"
    public void orderButtonClick(int orderButtonIndex) {
        By orderButton = orderButtons[orderButtonIndex];
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(orderButton));
        driver.findElement(orderButton).click();
    }


    //Клик кнопки "Вопросы о важном"
    public void questionButtonClick(int index) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(importantQuestionsButtons[index]));
        driver.findElement(importantQuestionsButtons[index]).click();
    }


    //Проверка, текст ответа открыт
    public boolean waitAnswerTextIsVisible(int index) {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOf(driver.findElement(importantQuestionsAnswers[index])));
        return driver.findElement(importantQuestionsAnswers[index]).isDisplayed();
    }

    //Проверка, текст ответа открыт и валиден
    private String getImportantAnswerText(int index) {
        return driver.findElement(importantQuestionsAnswers[index]).getText();
    }


    public String clickImportantQuestionAndGetAnswer(int index) {
        //Клик принять куки
        AcceptCookiesButton acceptCookiesButton = new AcceptCookiesButton(driver);
        acceptCookiesButton.clickCookieButtonIfExist();

        questionButtonClick(index);
        waitAnswerTextIsVisible(index);
        return getImportantAnswerText(index);
    }
}

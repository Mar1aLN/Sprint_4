package model;

import constants.ExpectedText;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ScooterMainPage {
    WebDriver driver;

    public ScooterMainPage(WebDriver driver) {
        this.driver = driver;
    }


    public static By orderButtonTop = By.className("Button_Button__ra12g");
    public static By orderButtonBottom = By.xpath("//*[@id=\"root\"]/div/div/div[4]/div[2]/div[5]/button");

    public By acceptCookiesButton = By.id("rcc-confirm-button");

    //Кнопки раздела "Вопросы о важном"
    public static By[] importantQuestionsButtons = new By[]{
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
    public static By[] importantQuestionsAnswers = new By[]{
            By.xpath("//*[@id=\"accordion__panel-0\"]/p"),
            By.xpath("//*[@id=\"accordion__panel-1\"]/p"),
            By.xpath("//*[@id=\"accordion__panel-2\"]/p"),
            By.xpath("//*[@id=\"accordion__panel-3\"]/p"),
            By.xpath("//*[@id=\"accordion__panel-4\"]/p"),
            By.xpath("//*[@id=\"accordion__panel-5\"]/p"),
            By.xpath("//*[@id=\"accordion__panel-6\"]/p"),
            By.xpath("//*[@id=\"accordion__panel-7\"]/p")
    };

    //Проверка, есть ли кнопка принять куки
    public boolean acceptCookiesButtonExist(){
        return !driver.findElements(acceptCookiesButton).isEmpty();
    }

    //Клик кнопки "Заказать"
    public void orderButtonClick(By orderButton){
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(orderButton));
        driver.findElement(orderButton).click();
    }

    //Клик кнопки принять куки
    public void acceptCookiesButtonClick(){
        driver.findElement(acceptCookiesButton).click();
    }

    public void clickCookieButtonIfExist(){
        if (acceptCookiesButtonExist()){
            acceptCookiesButtonClick();
        }
    }

    //Клик кнопки "Вопросы о важном"
    public void questionButtonClick(int index){
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(importantQuestionsButtons[index]));
        driver.findElement(importantQuestionsButtons[index]).click();
    }

    //Проверка, текст ответа скрыт
    public void assertAnswerTextIsInvisible(int index){
        Assert.assertFalse("Текст в выпадающем элементе отображается до клика стрелочки",driver.findElement(importantQuestionsAnswers[index]).isDisplayed());
    }

    //Проверка, текст ответа открыт
    public void assertAnswerTextIsVisible(int index){
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOf(driver.findElement(importantQuestionsAnswers[index])));
        Assert.assertTrue("Текст в выпадающем элементе не отображается после клика стрелочки",driver.findElement(importantQuestionsAnswers[index]).isDisplayed());
    }

    //Проверка, текст ответа открыт и валиден
    private void assertAnswerTextIsValid(int index){
        Assert.assertEquals("Текст в выпадающем элементе не соответствует эталону", ExpectedText.IMPORTANT_QUESTIONS_ANSWERS[index], driver.findElement(importantQuestionsAnswers[index]).getText());
    }


    public void clickQuestionButtonAndConfirmAnswer(int index){
        assertAnswerTextIsInvisible(index);

        questionButtonClick(index);

        assertAnswerTextIsVisible(index);
        assertAnswerTextIsValid(index);
    }
}

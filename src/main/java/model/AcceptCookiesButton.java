package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AcceptCookiesButton {
    //Кнопка принять куки
    private static final By acceptCookiesButton = By.id("rcc-confirm-button");
    private final WebDriver driver;

    public AcceptCookiesButton(WebDriver driver) {
        this.driver = driver;
    }

    //Проверка, есть ли кнопка принять куки
    public boolean acceptCookiesButtonExist() {
        return !driver.findElements(acceptCookiesButton).isEmpty();
    }

    //Клик кнопки принять куки
    public void acceptCookiesButtonClick() {
        driver.findElement(acceptCookiesButton).click();
    }

    //Клик кнопки принято куки, если она есть
    public void clickCookieButtonIfExist() {
        if (acceptCookiesButtonExist()) {
            acceptCookiesButtonClick();
        }
    }
}

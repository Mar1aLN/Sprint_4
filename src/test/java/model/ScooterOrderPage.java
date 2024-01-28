package model;

import constants.ExpectedText;
import constants.Url;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ScooterOrderPage {
    private final WebDriver driver;

    public ScooterOrderPage(WebDriver driver) {
        this.driver = driver;
    }

    private void assertOrderPageUrlIsLoaded(){
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(Url.QA_SCOOTER_ORDER_URL));
    }

    //Первый раздел страницы заказа
    private class OrderFormPart1 {
        WebDriver driver;

        public OrderFormPart1(WebDriver driver) {
            this.driver = driver;
        }

        //Заголовок первого раздела страницы заказа
        private final By orderPart1Title = By.xpath("//*[@id=\"root\"]/div/div[2]/div[1]");

        //Имя
        private final By nameField = By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div[1]/input");

        //Фамилия
        private final By surnameField = By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div[2]/input");

        //Адрес
        private final By addressField = By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div[3]/input");

        //Станция метро
        private final By metroStationField = By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div[4]/div/div/input");

        //Телефон
        private final By phoneField = By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div[5]/input");

        //Кнопка далее
        private final By nextButton = By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/button");

        //Заполнение поля "Имя"
        private void fillName(String name) {
            driver.findElement(this.nameField).sendKeys(name);
        }

        //Заполнение поля "Фамилия"
        private void fillSurname(String surname) {
            driver.findElement(this.surnameField).sendKeys(surname);
        }

        //Заполнение поля "Адрес"
        private void fillAddress(String address) {
            driver.findElement(this.addressField).sendKeys(address);
        }

        //Заполнение поля "Телефон"
        private void fillPhone(String phone) {
            driver.findElement(this.phoneField).sendKeys(phone);
        }

        //Заполнение поля "Станция метро"
        private void fillMetroStation(String metroStation) {
            driver.findElement(this.metroStationField).sendKeys(metroStation);
            driver.findElement(this.metroStationField).sendKeys(Keys.DOWN);
            driver.findElement(this.metroStationField).sendKeys(Keys.ENTER);
        }

        //Проверка заголовка раздела 1
        private void assertPage1TitleIsValid() {
            new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOf(driver.findElement(orderPart1Title)));
            Assert.assertEquals("Заголовок первой страницы формы заказа не валиден", ExpectedText.ORDER_SCOOTER_PART1_TITLE, driver.findElement(orderPart1Title).getText());
        }

        //Клик кнопки далее, для перехода ко второму разделу
        private void clickNextButton() {
            new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(driver.findElement(nextButton)));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(nextButton));

            driver.findElement(nextButton).click();

        }

        //Заполнение полей первого экрана и клик кнопки далее
        private void orderPart1FillAndClickNext(String name,
                                                String surname,
                                                String address,
                                                String metroStation,
                                                String phone) {
            assertPage1TitleIsValid();
            fillName(name);
            fillSurname(surname);
            fillAddress(address);
            fillMetroStation(metroStation);
            fillPhone(phone);
            clickNextButton();
        }
    }

    //Второй раздел страницы заказа
    private class OrderFormPart2 {
        WebDriver driver;

        public OrderFormPart2(WebDriver driver) {
            this.driver = driver;
        }

        //Заголовок второго раздела формы заказа
        private final By orderPart2Title = By.xpath("//*[@id=\"root\"]/div/div[2]/div[1]");

        //Когда привезти самокат
        private final By deliveryTimeField = By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div[1]/div[1]/div/input");

        //Срок аренды
        private final By rentDurationField = By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div[2]/div/div[1]");

        //Выпадающий список сроков аренды
        private final By rentDurationList = By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div[2]/div[2]");

        //Цвет самоката
        private final By[] colorFields = new By[]{By.xpath("//*[@id=\"black\"]"), By.xpath("//*[@id=\"grey\"]")};

        //Комментарий для курьера
        private final By commentField = By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div[4]/input");

        //Кнопка подтверждения формы заказа
        private final By orderButton = By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/button[2]");

        //Заполнение поля "Когда привезти заказ"
        private void fillDeliveryTime(String deliveryTime) {
            driver.findElement(deliveryTimeField).sendKeys(deliveryTime);
            driver.findElement(deliveryTimeField).sendKeys(Keys.ENTER);
        }

        //Заполнение поля "Срок аренды"
        private void fillRentDuration(String rentDuration) {
            driver.findElement(rentDurationField).click();
            WebElement durationDiv = driver.findElement(rentDurationList).findElement(By.xpath("//div[.=\"" + rentDuration + "\"]"));
            new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(durationDiv));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", durationDiv);
            durationDiv.click();
        }

        //Заполнение поля "Цвет самоката"
        private void fillColorField(int colorIndex) {
            driver.findElement(colorFields[colorIndex]).click();
        }

        //Заполнение поля "Комментарий"
        private void fillCommentField(String comment) {
            driver.findElement(commentField).sendKeys(comment);
        }

        //Проверка заголовка раздела 2
        private void assertPage2TitleIsValid() {
            new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.textToBePresentInElement(driver.findElement(orderPart2Title), ExpectedText.ORDER_SCOOTER_PART2_TITLE));
            Assert.assertEquals("Текст второй страницы формы заказа не отобразился", ExpectedText.ORDER_SCOOTER_PART2_TITLE, driver.findElement(orderPart2Title).getText());
        }

        //Клик по кнопке "Заказать"
        private void clickOrderButton() {
            driver.findElement(orderButton).click();
        }

        //Заполнение полей раздела 2 и клик по кнопке заказа
        public void orderPart2FillAndClickOrder(String deliveryTime,
                                                 String rentDuration,
                                                 int colorIndex,
                                                 String comment) {
            assertPage2TitleIsValid();
            fillDeliveryTime(deliveryTime);
            fillRentDuration(rentDuration);
            fillColorField(colorIndex);
            fillCommentField(comment);
            clickOrderButton();
        }
    }


    //Форма подтверждения заказа
    private class OrderConfirmForm {
        WebDriver driver;

        public OrderConfirmForm(WebDriver driver) {
            this.driver = driver;
        }

        //Диалог подтверждения заказа
        private final By orderConfirmFrame = By.xpath("//*[@id=\"root\"]/div/div[2]/div[5]");

        //Кнопка "Да" формы "Хотите оформить заказ"
        private final By orderConfirmYesButton = By.xpath("//*[@id=\"root\"]/div/div[2]/div[5]/div[2]/button[2]");

        //Проверка отображения диалога
        private void affirmFormIsVisible() {
            new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOf(driver.findElement(orderConfirmFrame)));
            Assert.assertTrue("Форма подтверждения заказа не отобразилась", driver.findElement(orderConfirmFrame).isDisplayed());
        }

        //Клик кнопки "Да" формы "Хотите оформить заказ"
        private void orderConfirmYesButtonClick() {
            driver.findElement(orderConfirmYesButton).click();
        }

        //Проверка отображения формы "Хотите оформить заказ", клик кнопки "Да"
        public void confirmOrder() {
            affirmFormIsVisible();
            orderConfirmYesButtonClick();
        }
    }

    //Раздел заказ оформлен
    //Заголовок раздела заказ оформлен
    private final By orderSuccessText = By.xpath("//*[@id=\"root\"]/div/div[2]/div[5]/div[1]");

    //Проверка наличия текста "Заказ оформлен" в начале текста, отображаемого на форме
    private void assertOrderSuccessTextIsValid(){
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.textToBePresentInElement(driver.findElement(orderSuccessText), ExpectedText.ORDER_SUCCESS_TITLE_STARTS_WITH));
        Assert.assertTrue("Текст \"" +  ExpectedText.ORDER_SUCCESS_TITLE_STARTS_WITH + "\" не был отображен после оформления заказа", driver.findElement(orderSuccessText).getText().startsWith(ExpectedText.ORDER_SUCCESS_TITLE_STARTS_WITH));
    }

    //Заполнение полей и подтверждение заказа
    public void orderScooter(String name,
                             String surname,
                             String address,
                             String metroStation,
                             String phone,
                             String deliveryTime,
                             String rentDuration,
                             int colorIndex,
                             String comment){
        //Проверка url страницы заказа
        assertOrderPageUrlIsLoaded();

        //Заполнение первой части странциы заказа
        OrderFormPart1 orderFormPart1 = new OrderFormPart1(driver);
        orderFormPart1.orderPart1FillAndClickNext(name, surname, address, metroStation, phone);

        //Заполнение второй части страницы заказа
        OrderFormPart2 orderFormPart2 = new OrderFormPart2(driver);
        orderFormPart2.orderPart2FillAndClickOrder(deliveryTime, rentDuration, colorIndex, comment);

        //Подтверждение заказа
        OrderConfirmForm orderConfirmForm = new OrderConfirmForm(driver);
        orderConfirmForm.confirmOrder();

        //Заказ успешно оформлен
        assertOrderSuccessTextIsValid();
    }
}
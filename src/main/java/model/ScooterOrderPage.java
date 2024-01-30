package model;

import constants.ExpectedText;
import constants.Url;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ScooterOrderPage {
    private final WebDriver driver;
    //Раздел заказ оформлен
    //Заголовок раздела заказ оформлен
    private final By orderSuccessText = By.className("Order_ModalHeader__3FDaJ");

    public ScooterOrderPage(WebDriver driver) {
        this.driver = driver;
    }

    private void assertOrderPageUrlIsLoaded() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(Url.QA_SCOOTER_ORDER_URL));
    }

    //Проверка наличия текста "Заказ оформлен" в начале текста, отображаемого на форме
    private void waitOrderSuccessTextIsValid() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.textToBePresentInElement(driver.findElement(orderSuccessText), ExpectedText.ORDER_SUCCESS_TITLE_STARTS_WITH));
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
                             String comment) {
        //Проверка url страницы заказа
        assertOrderPageUrlIsLoaded();

        //Клик принять куки
        AcceptCookiesButton acceptCookiesButton = new AcceptCookiesButton(driver);
        acceptCookiesButton.clickCookieButtonIfExist();

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
        waitOrderSuccessTextIsValid();
    }

    //Первый раздел страницы заказа
    private class OrderFormPart1 {
        //Заголовок первого раздела страницы заказа
        private final By orderPart1Title = By.className("Order_Header__BZXOb");
        //Имя
        private final By nameField = By.xpath("//input[@placeholder='* Имя']");
        //Фамилия
        private final By surnameField = By.xpath("//input[@placeholder='* Фамилия']");
        //Адрес
        private final By addressField = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
        //Станция метро
        private final By metroStationField = By.xpath("//input[@placeholder='* Станция метро']");
        //Телефон
        private final By phoneField = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
        //Кнопка далее
        private final By nextButton = By.className("Button_Middle__1CSJM");

        WebDriver driver;

        public OrderFormPart1(WebDriver driver) {
            this.driver = driver;
        }

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
        private void waitPage1TitleIsValid() {
            new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOf(driver.findElement(orderPart1Title)));
            new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.textToBePresentInElement(driver.findElement(orderPart1Title), ExpectedText.ORDER_SCOOTER_PART1_TITLE));
//            Assert.assertEquals("Заголовок первой страницы формы заказа не валиден", ExpectedText.ORDER_SCOOTER_PART1_TITLE, driver.findElement(orderPart1Title).getText());
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
            waitPage1TitleIsValid();
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

        //Заголовок второго раздела формы заказа
        private final By orderPart2Title = By.className("Order_Header__BZXOb");
        //Когда привезти самокат
        private final By deliveryTimeField = By.xpath("//input[@placeholder='* Когда привезти самокат']");
        //Срок аренды
        private final By rentDurationField = By.className("Dropdown-control");
        //Выпадающий список сроков аренды
        private final By rentDurationList = By.className("Dropdown-menu");
        //Цвет самоката
        private final By[] colorFields = new By[]{By.xpath("//*[@id=\"black\"]"), By.xpath("//*[@id=\"grey\"]")};
        //Комментарий для курьера
        private final By commentField = By.xpath("//input[@placeholder='Комментарий для курьера']");
        //Кнопка подтверждения формы заказа
        private final By orderButton = By.xpath("//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");
        WebDriver driver;

        public OrderFormPart2(WebDriver driver) {
            this.driver = driver;
        }

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
            new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.textToBePresentInElement(driver.findElement(orderPart2Title), ExpectedText.ORDER_SCOOTER_PART2_TITLE));
//            Assert.assertEquals("Текст второй страницы формы заказа не отобразился", ExpectedText.ORDER_SCOOTER_PART2_TITLE, driver.findElement(orderPart2Title).getText());
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
        //Диалог подтверждения заказа
        private final By orderConfirmFrame = By.className("Order_ModalHeader__3FDaJ");
        //Кнопка "Да" формы "Хотите оформить заказ"
        private final By orderConfirmYesButton = By.xpath("//button[contains(text(),'Да')]");
        WebDriver driver;

        public OrderConfirmForm(WebDriver driver) {
            this.driver = driver;
        }

        //Проверка отображения диалога
        private void waitFormIsVisible() {
            new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOf(driver.findElement(orderConfirmFrame)));
//            Assert.assertTrue("Форма подтверждения заказа не отобразилась", driver.findElement(orderConfirmFrame).isDisplayed());
        }

        //Клик кнопки "Да" формы "Хотите оформить заказ"
        private void orderConfirmYesButtonClick() {
            driver.findElement(orderConfirmYesButton).click();
        }

        //Проверка отображения формы "Хотите оформить заказ", клик кнопки "Да"
        public void confirmOrder() {
            waitFormIsVisible();
            orderConfirmYesButtonClick();
        }
    }
}
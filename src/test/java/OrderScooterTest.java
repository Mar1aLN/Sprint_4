import constants.Url;
import model.ScooterMainPage;
import model.ScooterOrderPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.*;
import service.WebDriverHelper;

import java.util.Arrays;
import java.util.Collection;

//Тест позитивного сценарий заказа самоката
@RunWith(Parameterized.class)
public class OrderScooterTest {
    WebDriver driver;

    //Индекс кнопки "Заказать"
    private final By orderButton;

    //Имя
    private final String name;

    //Фамилия
    private final String surname;

    //Адрес
    private final String address;

    //Станция метро
    private final String metroStation;

    //Телефон
    private final String phone;

    //Когда привезти самокат
    private final String deliveryTime;

    //Срок аренды
    private final String rentDuration;

    //Цвет самоката
    private final int colorIndex;

    //Комментарий для курьера
    private final String comment;

    public OrderScooterTest(By orderButtonIndex, String name, String surname, String address, String metroStation, String phone, String deliveryTime, String rentDuration, int colorIndex, String comment) {
        this.orderButton = orderButtonIndex;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.deliveryTime = deliveryTime;
        this.rentDuration = rentDuration;
        this.colorIndex = colorIndex;
        this.comment = comment;
    }

    @Before
    public void before(){
        driver = WebDriverHelper.getChromeDriver();
        driver.get(Url.QA_SCOOTER_URL);

    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
            {   ScooterMainPage.orderButtonTop,
                "Иван",
                "Иванов",
                "ул. строителя д. 2",
                "Сокольники",
                "+79091231212",
                "01.02.2024",
                "семеро суток",
                1,
                "Мой комментарий"
            }, { ScooterMainPage.orderButtonBottom,
                "Петр",
                "Петров",
                "ул. Ленина 11",
                "Черкизовская",
                "89504564545",
                "24.02.2024",
                "сутки",
                0,
                ""
            }
        });
    }


    @Test
    public void test(){
        ScooterMainPage scooterMainPage = new ScooterMainPage(driver);
        scooterMainPage.acceptCookiesButtonClick();

        scooterMainPage.orderButtonClick(orderButton);
        ScooterOrderPage scooterOrderPage = new ScooterOrderPage(driver);
        scooterOrderPage.orderScooter(name, surname, address, metroStation, phone, deliveryTime, rentDuration, colorIndex, comment);
    }

    @After
    public void teardown(){
        driver.quit();
    }
}

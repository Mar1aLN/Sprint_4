import constants.Url;
import model.AcceptCookiesButton;
import model.ScooterMainPage;
import model.ScooterOrderPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import service.WebDriverHelper;

import java.util.Arrays;
import java.util.Collection;

//Тест позитивного сценарий заказа самоката
@RunWith(Parameterized.class)
public class OrderScooterTest {
    //Индекс кнопки "Заказать"
    private final int orderButtonIndex;
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
    WebDriver driver;

    public OrderScooterTest(int orderButtonIndex, String name, String surname, String address, String metroStation, String phone, String deliveryTime, String rentDuration, int colorIndex, String comment) {
        this.orderButtonIndex = orderButtonIndex;
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

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {0,
                        "Иван",
                        "Иванов",
                        "ул. строителя д. 2",
                        "Сокольники",
                        "+79091231212",
                        "01.02.2024",
                        "семеро суток",
                        1,
                        "Мой комментарий"
                }, {1,
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

    @Before
    public void before() {
        driver = WebDriverHelper.getChromeDriver();
        driver.get(Url.QA_SCOOTER_URL);

    }

    @Test
    public void test() {
        ScooterMainPage scooterMainPage = new ScooterMainPage(driver);

        AcceptCookiesButton acceptCookiesButton = new AcceptCookiesButton(driver);
        acceptCookiesButton.clickCookieButtonIfExist();

        scooterMainPage.orderButtonClick(orderButtonIndex);
        ScooterOrderPage scooterOrderPage = new ScooterOrderPage(driver);
        scooterOrderPage.orderScooter(name, surname, address, metroStation, phone, deliveryTime, rentDuration, colorIndex, comment);
    }

    @After
    public void teardown() {
        driver.quit();
    }
}

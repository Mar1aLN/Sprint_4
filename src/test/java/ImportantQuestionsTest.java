import constants.Url;
import model.ScooterMainPage;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import service.WebDriverHelper;

import java.util.ArrayList;
import java.util.Collection;

//Тест функционала раздела "Вопросы о важном"
@RunWith(Parameterized.class)
public class ImportantQuestionsTest {
    WebDriver driver;

    private final int questionIndex;

    public ImportantQuestionsTest(int questionIndex) {
        this.questionIndex = questionIndex;

    }

    @Before
    public void before(){
        driver = WebDriverHelper.getChromeDriver();
        driver.get(Url.QA_SCOOTER_URL);
    }

    //В качестве параметра используется индекс вопроса о важном
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Collection<Object[]> result = new ArrayList<>();
        for(int i = 0; i < ScooterMainPage.importantQuestionsButtons.length; i++){
            result.add(new Object[]{i});
        }
        return result;
    }


    @Test
    public void test(){
        ScooterMainPage scooterMainPage = new ScooterMainPage(driver);
        scooterMainPage.acceptCookiesButtonClick();
        scooterMainPage.clickQuestionButtonAndConfirmAnswer(questionIndex);
    }

    @After
    public void teardown(){
        driver.quit();
    }
}

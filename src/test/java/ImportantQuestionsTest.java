import constants.ExpectedText;
import constants.Url;
import model.ScooterMainPage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import service.WebDriverHelper;

import java.util.ArrayList;
import java.util.Collection;

//Тест функционала раздела "Вопросы о важном"
@RunWith(Parameterized.class)
public class ImportantQuestionsTest {
    private final int questionIndex;
    WebDriver driver;

    public ImportantQuestionsTest(int questionIndex) {
        this.questionIndex = questionIndex;

    }

    //В качестве параметра используется индекс вопроса о важном
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Collection<Object[]> result = new ArrayList<>();
        for (int i = 0; i < ExpectedText.IMPORTANT_QUESTIONS_ANSWERS.length; i++) {
            result.add(new Object[]{i});
        }
        return result;
    }

    @Before
    public void before() {
        driver = WebDriverHelper.getChromeDriver();
        driver.get(Url.QA_SCOOTER_URL);
    }

    @Test
    public void test() {
        ScooterMainPage scooterMainPage = new ScooterMainPage(driver);
        Assert.assertEquals("", ExpectedText.IMPORTANT_QUESTIONS_ANSWERS[questionIndex], scooterMainPage.clickImportantQuestionAndGetAnswer(questionIndex));
    }

    @After
    public void teardown() {
        driver.quit();
    }
}

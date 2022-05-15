import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


// Singelton object to initialize selenium chrome driver

public class ChromeDriverSingelton {

    public static ChromeDriver driver;

    public  static ChromeDriver getdriverInstance(){
        if (driver == null){
            System.setProperty("webdriver.chrome.driver", Constants.CHROMEDRIVER_PATH);
            driver = new ChromeDriver();
        }
        return driver;
    }
}

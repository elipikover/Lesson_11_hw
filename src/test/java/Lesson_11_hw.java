import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;




public class Lesson_11_hw {
    public static ChromeDriver driver = ChromeDriverSingelton.getdriverInstance();
    private static final ExtentReports extent= new ExtentReports();
    private static final ExtentTest test = extent.createTest("Homework Report", "This is a test report for the homework assignment lesson 11");
    public String timeNow = String.valueOf(System.currentTimeMillis());


    @BeforeClass
    public void runBeforeTests(){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        ExtentSparkReporter htmlReporter = new ExtentSparkReporter("extent.html");
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Company", "Eli's Company");
        test.log(Status.PASS, "@BeforeClass");


    }

// Test of intro & registration flow

    @Test(priority = 1)
    public void TestiFrame(){
        driver.get("https://dgotlieb.github.io/Navigation/Navigation.html");
        driver.switchTo().frame("my-frame");
        System.out.println(driver.findElement(By.id("iframe_container")).getText());
        test.log(Status.PASS, "iFrame test Passed");
    }

    @Test(priority = 2)
    public void GoogleTranslateTest(){
        driver.get("https://translate.google.co.il/?hl=iw");
        test.info("pic", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot(timeNow)).build());
        driver.findElement(By.cssSelector("textarea[aria-label='טקסט מקור']"));
        test.log(Status.PASS,"Google translate clicked");

    }
    @Test(priority = 3)
    public void XMLTest() throws Exception {driver.get(getData("URL"));
        test.log(Status.PASS,"XML got URL from XML passed");


    }

    private static String getData (String keyName) throws Exception{
        File fXmlFile = new File("/Users/epikover/IdeaProjects/Java/QA_Experts/Lesson 11/Lesson_11_hw/src/main/resources/data.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        doc.getDocumentElement().normalize();
        return doc.getElementsByTagName(keyName).item(0).getTextContent();
    }
    @Test(priority = 4)
    public void JSONTest() throws Exception {
        Reader reader = Files.newBufferedReader(Paths.get(Constants.JSON_PATH));
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode parser = objectMapper.readTree(reader);
        driver.get(parser.path("URL").asText());
    }


    @AfterClass
    public void runAfterTests() {
        test.log(Status.INFO, "Testing Completed");
        driver.quit();
        extent.flush();
    }


    private static String takeScreenShot(String ImagesPath) {
        TakesScreenshot takesScreenshot = driver;
        File screenShotFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        File destinationFile = new File(ImagesPath + ".png");
        try {
            FileUtils.copyFile(screenShotFile, destinationFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return ImagesPath + ".png";
    }
}

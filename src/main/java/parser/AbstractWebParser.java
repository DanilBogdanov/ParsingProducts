package parser;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public abstract class AbstractWebParser implements Parser{
    private int timeout = 0;
    private WebDriver webDriver;

    void setTimeout(int timeout) {
        this.timeout = timeout;
        getWebDriver().manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);//ожидание n секунд до проброса exception
    }

    int getTimeout() {
        return timeout;
    }

    WebDriver getWebDriver() {
        if (webDriver == null) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-notifications");
            //options.addArguments("window-size=1600x1080");todo
            //options.addArguments("--start-maximized");todo
            webDriver = new ChromeDriver(options);

            webDriver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);//ожидание n секунд до проброса exception
        }
        return webDriver;
    }

    void close() {
        webDriver.close();
    }

    boolean containsClass(WebElement webElement, String webClass) {
        // возвращает список без ошибок, если класс присутствует результат больше 0
        return webElement.findElements(By.className(webClass)).size() > 0;
    }

    boolean containsClass(String webClass) {
        return webDriver.findElements(By.className(webClass)).size() > 0;
    }

    boolean containsXPath(String xPath) {
        return webDriver.findElements(By.xpath(xPath)).size() > 0;
    }

    boolean containsXPath(WebElement webElement, String xPath) {
        return webElement.findElements(By.xpath(xPath)).size() > 0;
    }


}

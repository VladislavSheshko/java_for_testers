package tests;

import manager.ApplicationManager;
import model.ContactData;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestBase {

    protected static ApplicationManager app;


    @BeforeEach
    public void setUp() {
        if (app == null) {
            app = new ApplicationManager();
            app.init();
        }
    }


}

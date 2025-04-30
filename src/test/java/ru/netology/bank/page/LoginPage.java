package ru.netology.bank.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.bank.data.DataHelper;

import java.util.Objects;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.text;

public class LoginPage {
    private SelenideElement loginField = $("[data-test-id=login] input");
    private SelenideElement passwordField = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");
    private SelenideElement errorNotification = $("[data-test-id=error-notification]");

    public void verifyErrorNotificationVisibility() {
        errorNotification.shouldBe(visible).shouldHave(text("Ошибка"));
    }

    public void verifyPageVisible() {
        loginField.shouldBe(visible);
    }

    public AuthorizationCodePage validLogin(DataHelper.AuthInfo authInfo) {
        loginField.setValue(authInfo.getLogin());
        passwordField.setValue(authInfo.getPassword());
        loginButton.click();
        return new AuthorizationCodePage();
    }

    public void login(DataHelper.AuthInfo authInfo) {
        loginField.setValue(authInfo.getLogin());
        passwordField.setValue(authInfo.getPassword());
        loginButton.click();
        errorNotification.shouldBe(visible).shouldHave(text("Ошибка"));
    }
    
    public void clearForm() {
        loginField.click();
        while(!Objects.equals(loginField.getValue(), "")){
            loginField.sendKeys(Keys.BACK_SPACE);
        }
        loginField.sendKeys(Keys.BACK_SPACE);
        passwordField.click();
        while(!Objects.equals(passwordField.getValue(), "")){
            passwordField.sendKeys(Keys.BACK_SPACE);
        }
    }
    
    public void clearFormPassword() {
        passwordField.click();
        passwordField.sendKeys(Keys.SHIFT, Keys.HOME, Keys.BACK_SPACE);// можно удалить данные из поля таким способом
//        while(!Objects.equals(passwordField.getValue(), "")){  // или таким, с помощью цикла while
//            passwordField.sendKeys(Keys.BACK_SPACE);
//        }
    }
}

package ru.netology.bank.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.text;

public class PersonalAccountPage {

    public PersonalAccountPage() {
        SelenideElement heading = $("[data-test-id=dashboard]");
        heading.shouldBe(visible).shouldHave(text("Личный кабинет"));
    }
}

package ru.netology;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.pages.CreditPage;
import ru.netology.pages.PaymentPage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.netology.data.SQLHelper.cleanDatabase;

public class TravelTest {
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @AfterAll
    static void teardown() {
        cleanDatabase();
    }

    @Test
    public void positiveCardPayment() {

        var paymentPage = open("http://localhost:8080", PaymentPage.class);
        var approvedInfo = DataHelper.getApprovedCardData();
        var randomInfo = DataHelper.generateRandomInfo(1, 1);
        paymentPage.paymentFieldClick();
        paymentPage.cardNumberFieldValue(approvedInfo.getNumber());
        paymentPage.monthFieldValue(randomInfo.getMonth());
        paymentPage.yearFieldValue(randomInfo.getYear());
        paymentPage.nameFieldValue(randomInfo.getName());
        paymentPage.cvvFieldValue(randomInfo.getCvv());
        paymentPage.sendFieldClick();
        paymentPage.verifyNotificationOkVisibility();
        assertEquals("APPROVED", SQLHelper.getPaymentStatus());


    }

    @Test
    public void positiveCreditPayment() {

        var creditPage = open("http://localhost:8080", CreditPage.class);
        var approvedInfo = DataHelper.getApprovedCardData();
        var randomInfo = DataHelper.generateRandomInfo(1, 1);
        creditPage.creditFieldClick();
        creditPage.cardNumberFieldValue(approvedInfo.getNumber());
        creditPage.monthFieldValue(randomInfo.getMonth());
        creditPage.yearFieldValue(randomInfo.getYear());
        creditPage.nameFieldValue(randomInfo.getName());
        creditPage.cvvFieldValue(randomInfo.getCvv());
        creditPage.sendFieldClick();
        creditPage.verifyNotificationOkVisibility();
        assertEquals("APPROVED", SQLHelper.getCreditStatus());

    }

    @Test
    public void negativeCardPayment() {

        var paymentPage = open("http://localhost:8080", PaymentPage.class);
        var declinedInfo = DataHelper.getDeclinedCardData();
        var randomInfo = DataHelper.generateRandomInfo(1, 1);
        paymentPage.paymentFieldClick();
        paymentPage.cardNumberFieldValue(declinedInfo.getNumber());
        paymentPage.monthFieldValue(randomInfo.getMonth());
        paymentPage.yearFieldValue(randomInfo.getYear());
        paymentPage.nameFieldValue(randomInfo.getName());
        paymentPage.cvvFieldValue(randomInfo.getCvv());
        paymentPage.sendFieldClick();
        paymentPage.verifyErrorNotificationVisibility();
        assertEquals("DECLINED", SQLHelper.getPaymentStatus());

    }

    @Test
    public void negativeCreditPayment() {

        var creditPage = open("http://localhost:8080", CreditPage.class);
        var declinedInfo = DataHelper.getDeclinedCardData();
        var randomInfo = DataHelper.generateRandomInfo(1, 1);
        creditPage.creditFieldClick();
        creditPage.cardNumberFieldValue(declinedInfo.getNumber());
        creditPage.monthFieldValue(randomInfo.getMonth());
        creditPage.yearFieldValue(randomInfo.getYear());
        creditPage.nameFieldValue(randomInfo.getName());
        creditPage.cvvFieldValue(randomInfo.getCvv());
        creditPage.sendFieldClick();
        creditPage.verifyErrorNotificationVisibility();
        assertEquals("DECLINED", SQLHelper.getCreditStatus());

    }

    @Test
    public void NotExistentCardPayment() {

        var paymentPage = open("http://localhost:8080", PaymentPage.class);
        var randomInfo = DataHelper.generateRandomInfo(1, 1);
        paymentPage.paymentFieldClick();
        paymentPage.cardNumberFieldValue(randomInfo.getNumber());
        paymentPage.monthFieldValue(randomInfo.getMonth());
        paymentPage.yearFieldValue(randomInfo.getYear());
        paymentPage.nameFieldValue(randomInfo.getName());
        paymentPage.cvvFieldValue(randomInfo.getCvv());
        paymentPage.sendFieldClick();
        paymentPage.verifyErrorNotificationVisibility();
        assertNull(SQLHelper.getPaymentStatus());

    }

    @Test
    public void paymentByCardNumberWhichConsistsOf15Numbers() {

        var paymentPage = open("http://localhost:8080", PaymentPage.class);
        var randomInfo = DataHelper.generateRandomInfo(1, 1);
        paymentPage.paymentFieldClick();
        paymentPage.cardNumberFieldValue(DataHelper.generateRandomNumberCard());
        paymentPage.monthFieldValue(randomInfo.getMonth());
        paymentPage.yearFieldValue(randomInfo.getYear());
        paymentPage.nameFieldValue(randomInfo.getName());
        paymentPage.cvvFieldValue(randomInfo.getCvv());
        paymentPage.sendFieldClick();
        paymentPage.wrongFormat();
    }

    @Test
    public void paymentByCardNumberWhichConsistsOf1Number() {

        var paymentPage = open("http://localhost:8080", PaymentPage.class);
        var randomInfo = DataHelper.generateRandomInfo(1, 1);
        paymentPage.paymentFieldClick();
        paymentPage.cardNumberFieldValue(DataHelper.generateRandomNumberMin());
        paymentPage.monthFieldValue(randomInfo.getMonth());
        paymentPage.yearFieldValue(randomInfo.getYear());
        paymentPage.nameFieldValue(randomInfo.getName());
        paymentPage.cvvFieldValue(randomInfo.getCvv());
        paymentPage.sendFieldClick();
        paymentPage.wrongFormat();
    }

    @Test
    public void paymentByCardNumberWhichEmpty() {

        var paymentPage = open("http://localhost:8080", PaymentPage.class);
        var randomInfo = DataHelper.generateRandomInfo(1, 1);
        paymentPage.paymentFieldClick();
        paymentPage.monthFieldValue(randomInfo.getMonth());
        paymentPage.yearFieldValue(randomInfo.getYear());
        paymentPage.nameFieldValue(randomInfo.getName());
        paymentPage.cvvFieldValue(randomInfo.getCvv());
        paymentPage.sendFieldClick();
        paymentPage.wrongFormat();
    }

    @Test
    public void paymentByCardNumberWhichConsistsOf0() {

        var paymentPage = open("http://localhost:8080", PaymentPage.class);
        var randomInfo = DataHelper.generateRandomInfo(1, 1);
        paymentPage.paymentFieldClick();
        paymentPage.cardNumberFieldValue("0000 0000 0000 0000");
        paymentPage.monthFieldValue(randomInfo.getMonth());
        paymentPage.yearFieldValue(randomInfo.getYear());
        paymentPage.nameFieldValue(randomInfo.getName());
        paymentPage.cvvFieldValue(randomInfo.getCvv());
        paymentPage.sendFieldClick();
        paymentPage.wrongFormat();
    }

    @Test
    public void emptyMonthPaymentCard() {

        var paymentPage = open("http://localhost:8080", PaymentPage.class);
        var randomInfo = DataHelper.generateRandomInfo(1, 1);
        paymentPage.paymentFieldClick();
        paymentPage.cardNumberFieldValue(randomInfo.getNumber());
        paymentPage.yearFieldValue(randomInfo.getYear());
        paymentPage.nameFieldValue(randomInfo.getName());
        paymentPage.cvvFieldValue(randomInfo.getCvv());
        paymentPage.sendFieldClick();
        paymentPage.wrongFormat();
    }

    @Test
    public void lastMonthPaymentCard() {

        var paymentPage = open("http://localhost:8080", PaymentPage.class);
        var randomInfo = DataHelper.generateRandomInfo(-1, 0);
        paymentPage.paymentFieldClick();
        paymentPage.cardNumberFieldValue(randomInfo.getNumber());
        paymentPage.monthFieldValue(randomInfo.getMonth());
        paymentPage.yearFieldValue(randomInfo.getYear());
        paymentPage.nameFieldValue(randomInfo.getName());
        paymentPage.cvvFieldValue(randomInfo.getCvv());
        paymentPage.sendFieldClick();
        paymentPage.wrongValidity();
    }

    @Test
    public void emptyYearPaymentCard() {

        var paymentPage = open("http://localhost:8080", PaymentPage.class);
        var randomInfo = DataHelper.generateRandomInfo(1, 1);
        paymentPage.paymentFieldClick();
        paymentPage.cardNumberFieldValue(randomInfo.getNumber());
        paymentPage.monthFieldValue(randomInfo.getMonth());
        paymentPage.nameFieldValue(randomInfo.getName());
        paymentPage.cvvFieldValue(randomInfo.getCvv());
        paymentPage.sendFieldClick();
        paymentPage.wrongFormat();
    }

    @Test
    public void lastYearPaymentCard() {

        var paymentPage = open("http://localhost:8080", PaymentPage.class);
        var randomInfo = DataHelper.generateRandomInfo(1, -1);
        paymentPage.paymentFieldClick();
        paymentPage.cardNumberFieldValue(randomInfo.getNumber());
        paymentPage.monthFieldValue(randomInfo.getMonth());
        paymentPage.yearFieldValue(randomInfo.getYear());
        paymentPage.nameFieldValue(randomInfo.getName());
        paymentPage.cvvFieldValue(randomInfo.getCvv());
        paymentPage.sendFieldClick();
        paymentPage.endOfValidity();
    }

    @Test
    public void numbersInNamePaymentCard() {

        var paymentPage = open("http://localhost:8080", PaymentPage.class);
        var randomInfo = DataHelper.generateRandomInfo(1, 1);
        paymentPage.paymentFieldClick();
        paymentPage.cardNumberFieldValue(randomInfo.getNumber());
        paymentPage.monthFieldValue(randomInfo.getMonth());
        paymentPage.yearFieldValue(randomInfo.getYear());
        paymentPage.nameFieldValue(DataHelper.generateRandomInt());
        paymentPage.cvvFieldValue(randomInfo.getCvv());
        paymentPage.sendFieldClick();
        paymentPage.wrongFormat();
    }

    @Test
    public void oneLetterInNamePaymentCard() {

        var paymentPage = open("http://localhost:8080", PaymentPage.class);
        var randomInfo = DataHelper.generateRandomInfo(1, 1);
        paymentPage.paymentFieldClick();
        paymentPage.cardNumberFieldValue(randomInfo.getNumber());
        paymentPage.monthFieldValue(randomInfo.getMonth());
        paymentPage.yearFieldValue(randomInfo.getYear());
        paymentPage.nameFieldValue(DataHelper.generateRandomLetter());
        paymentPage.cvvFieldValue(randomInfo.getCvv());
        paymentPage.sendFieldClick();
        paymentPage.wrongFormat();
    }

    @Test
    public void emptyNamePaymentCard() {

        var paymentPage = open("http://localhost:8080", PaymentPage.class);
        var randomInfo = DataHelper.generateRandomInfo(1, 1);
        paymentPage.paymentFieldClick();
        paymentPage.cardNumberFieldValue(randomInfo.getNumber());
        paymentPage.monthFieldValue(randomInfo.getMonth());
        paymentPage.yearFieldValue(randomInfo.getYear());
        paymentPage.cvvFieldValue(randomInfo.getCvv());
        paymentPage.sendFieldClick();
        paymentPage.necessarilyField();
    }

    @Test
    public void TwoNumbersInCvcPaymentCard() {

        var paymentPage = open("http://localhost:8080", PaymentPage.class);
        var randomInfo = DataHelper.generateRandomInfo(1, 1);
        paymentPage.paymentFieldClick();
        paymentPage.cardNumberFieldValue(randomInfo.getNumber());
        paymentPage.monthFieldValue(randomInfo.getMonth());
        paymentPage.yearFieldValue(randomInfo.getYear());
        paymentPage.nameFieldValue(randomInfo.getName());
        paymentPage.cvvFieldValue(DataHelper.generateRandomInt());
        paymentPage.sendFieldClick();
        paymentPage.wrongFormat();
    }

    @Test
    public void emptyCvvPaymentCard() {

        var paymentPage = open("http://localhost:8080", PaymentPage.class);
        var randomInfo = DataHelper.generateRandomInfo(1, 1);
        paymentPage.paymentFieldClick();
        paymentPage.cardNumberFieldValue(randomInfo.getNumber());
        paymentPage.monthFieldValue(randomInfo.getMonth());
        paymentPage.yearFieldValue(randomInfo.getYear());
        paymentPage.nameFieldValue(randomInfo.getName());
        paymentPage.sendFieldClick();
        paymentPage.wrongFormat();
    }

    @Test
    public void zerosInCvcPaymentCard() {

        var paymentPage = open("http://localhost:8080", PaymentPage.class);
        var randomInfo = DataHelper.generateRandomInfo(1, 1);
        paymentPage.paymentFieldClick();
        paymentPage.cardNumberFieldValue(randomInfo.getNumber());
        paymentPage.monthFieldValue(randomInfo.getMonth());
        paymentPage.yearFieldValue(randomInfo.getYear());
        paymentPage.nameFieldValue(randomInfo.getName());
        paymentPage.cvvFieldValue("000");
        paymentPage.sendFieldClick();
        paymentPage.wrongFormat();
    }

    @Test
    public void NotExistentCardCreditPayment() {

        var creditPage = open("http://localhost:8080", CreditPage.class);
        var randomInfo = DataHelper.generateRandomInfo(1, 1);
        creditPage.creditFieldClick();
        creditPage.cardNumberFieldValue(randomInfo.getNumber());
        creditPage.monthFieldValue(randomInfo.getMonth());
        creditPage.yearFieldValue(randomInfo.getYear());
        creditPage.nameFieldValue(randomInfo.getName());
        creditPage.cvvFieldValue(randomInfo.getCvv());
        creditPage.sendFieldClick();
        creditPage.verifyErrorNotificationVisibility();
        assertNull(SQLHelper.getCreditStatus());

    }

    @Test
    public void paymentInCreditByCardNumberWhichConsistsOf15Numbers() {

        var creditPage = open("http://localhost:8080", CreditPage.class);
        var randomInfo = DataHelper.generateRandomInfo(1, 1);
        creditPage.creditFieldClick();
        creditPage.cardNumberFieldValue(DataHelper.generateRandomNumberCard());
        creditPage.monthFieldValue(randomInfo.getMonth());
        creditPage.yearFieldValue(randomInfo.getYear());
        creditPage.nameFieldValue(randomInfo.getName());
        creditPage.cvvFieldValue(randomInfo.getCvv());
        creditPage.sendFieldClick();
        creditPage.wrongFormat();
    }

    @Test
    public void paymentInCreditByCardNumberWhichConsistsOf1Number() {

        var creditPage = open("http://localhost:8080", CreditPage.class);
        var randomInfo = DataHelper.generateRandomInfo(1, 1);
        creditPage.creditFieldClick();
        creditPage.cardNumberFieldValue(DataHelper.generateRandomNumberMin());
        creditPage.monthFieldValue(randomInfo.getMonth());
        creditPage.yearFieldValue(randomInfo.getYear());
        creditPage.nameFieldValue(randomInfo.getName());
        creditPage.cvvFieldValue(randomInfo.getCvv());
        creditPage.sendFieldClick();
        creditPage.wrongFormat();
    }

    @Test
    public void paymentInCreditByCardNumberWhichEmpty() {

        var creditPage = open("http://localhost:8080", CreditPage.class);
        var randomInfo = DataHelper.generateRandomInfo(1, 1);
        creditPage.creditFieldClick();
        creditPage.monthFieldValue(randomInfo.getMonth());
        creditPage.yearFieldValue(randomInfo.getYear());
        creditPage.nameFieldValue(randomInfo.getName());
        creditPage.cvvFieldValue(randomInfo.getCvv());
        creditPage.sendFieldClick();
        creditPage.wrongFormat();
    }

    @Test
    public void paymentInCreditByCardNumberWhichConsistsOf0() {

        var creditPage = open("http://localhost:8080", CreditPage.class);
        var randomInfo = DataHelper.generateRandomInfo(1, 1);
        creditPage.creditFieldClick();
        creditPage.cardNumberFieldValue("0000 0000 0000 0000");
        creditPage.monthFieldValue(randomInfo.getMonth());
        creditPage.yearFieldValue(randomInfo.getYear());
        creditPage.nameFieldValue(randomInfo.getName());
        creditPage.cvvFieldValue(randomInfo.getCvv());
        creditPage.sendFieldClick();
        creditPage.wrongFormat();
    }

    @Test
    public void emptyMonthCreditPayment() {

        var creditPage = open("http://localhost:8080", CreditPage.class);
        var randomInfo = DataHelper.generateRandomInfo(1, 1);
        creditPage.creditFieldClick();
        creditPage.cardNumberFieldValue(randomInfo.getNumber());
        creditPage.yearFieldValue(randomInfo.getYear());
        creditPage.nameFieldValue(randomInfo.getName());
        creditPage.cvvFieldValue(randomInfo.getCvv());
        creditPage.sendFieldClick();
        creditPage.wrongFormat();
    }

    @Test
    public void lastMonthCreditPayment() {

        var creditPage = open("http://localhost:8080", CreditPage.class);
        var randomInfo = DataHelper.generateRandomInfo(-1, 0);
        creditPage.creditFieldClick();
        creditPage.cardNumberFieldValue(randomInfo.getNumber());
        creditPage.monthFieldValue(randomInfo.getMonth());
        creditPage.yearFieldValue(randomInfo.getYear());
        creditPage.nameFieldValue(randomInfo.getName());
        creditPage.cvvFieldValue(randomInfo.getCvv());
        creditPage.sendFieldClick();
        creditPage.wrongValidity();
    }

    @Test
    public void emptyYearCreditPayment() {

        var creditPage = open("http://localhost:8080", CreditPage.class);
        var randomInfo = DataHelper.generateRandomInfo(1, 1);
        creditPage.creditFieldClick();
        creditPage.cardNumberFieldValue(randomInfo.getNumber());
        creditPage.monthFieldValue(randomInfo.getMonth());
        creditPage.nameFieldValue(randomInfo.getName());
        creditPage.cvvFieldValue(randomInfo.getCvv());
        creditPage.sendFieldClick();
        creditPage.wrongFormat();
    }

    @Test
    public void lastYearCreditPayment() {

        var creditPage = open("http://localhost:8080", CreditPage.class);
        var randomInfo = DataHelper.generateRandomInfo(1, -1);
        creditPage.creditFieldClick();
        creditPage.cardNumberFieldValue(randomInfo.getNumber());
        creditPage.monthFieldValue(randomInfo.getMonth());
        creditPage.yearFieldValue(randomInfo.getYear());
        creditPage.nameFieldValue(randomInfo.getName());
        creditPage.cvvFieldValue(randomInfo.getCvv());
        creditPage.sendFieldClick();
        creditPage.endOfValidity();
    }

    @Test
    public void numbersInNameCreditPayment() {

        var creditPage = open("http://localhost:8080", CreditPage.class);
        var randomInfo = DataHelper.generateRandomInfo(1, 1);
        creditPage.creditFieldClick();
        creditPage.cardNumberFieldValue(randomInfo.getNumber());
        creditPage.monthFieldValue(randomInfo.getMonth());
        creditPage.yearFieldValue(randomInfo.getYear());
        creditPage.nameFieldValue(DataHelper.generateRandomInt());
        creditPage.cvvFieldValue(randomInfo.getCvv());
        creditPage.sendFieldClick();
        creditPage.wrongFormat();
    }

    @Test
    public void oneLetterInNameCreditPayment() {

        var creditPage = open("http://localhost:8080", CreditPage.class);
        var randomInfo = DataHelper.generateRandomInfo(1, 1);
        creditPage.creditFieldClick();
        creditPage.cardNumberFieldValue(randomInfo.getNumber());
        creditPage.monthFieldValue(randomInfo.getMonth());
        creditPage.yearFieldValue(randomInfo.getYear());
        creditPage.nameFieldValue(DataHelper.generateRandomLetter());
        creditPage.cvvFieldValue(randomInfo.getCvv());
        creditPage.sendFieldClick();
        creditPage.wrongFormat();
    }

    @Test
    public void emptyNameCreditPayment() {

        var creditPage = open("http://localhost:8080", CreditPage.class);
        var randomInfo = DataHelper.generateRandomInfo(1, 1);
        creditPage.creditFieldClick();
        creditPage.cardNumberFieldValue(randomInfo.getNumber());
        creditPage.monthFieldValue(randomInfo.getMonth());
        creditPage.yearFieldValue(randomInfo.getYear());
        creditPage.cvvFieldValue(randomInfo.getCvv());
        creditPage.sendFieldClick();
        creditPage.necessarilyField();
    }

    @Test
    public void TwoNumbersInCvcCreditPayment() {

        var creditPage = open("http://localhost:8080", CreditPage.class);
        var randomInfo = DataHelper.generateRandomInfo(1, 1);
        creditPage.creditFieldClick();
        creditPage.cardNumberFieldValue(randomInfo.getNumber());
        creditPage.monthFieldValue(randomInfo.getMonth());
        creditPage.yearFieldValue(randomInfo.getYear());
        creditPage.nameFieldValue(randomInfo.getName());
        creditPage.cvvFieldValue(DataHelper.generateRandomInt());
        creditPage.sendFieldClick();
        creditPage.wrongFormat();
    }

    @Test
    public void emptyCvvCreditPayment() {

        var creditPage = open("http://localhost:8080", CreditPage.class);
        var randomInfo = DataHelper.generateRandomInfo(1, 1);
        creditPage.creditFieldClick();
        creditPage.cardNumberFieldValue(randomInfo.getNumber());
        creditPage.monthFieldValue(randomInfo.getMonth());
        creditPage.yearFieldValue(randomInfo.getYear());
        creditPage.nameFieldValue(randomInfo.getName());
        creditPage.sendFieldClick();
        creditPage.wrongFormat();
    }

    @Test
    public void zerosInCvcCreditPayment() {

        var creditPage = open("http://localhost:8080", CreditPage.class);
        var randomInfo = DataHelper.generateRandomInfo(1, 1);
        creditPage.creditFieldClick();
        creditPage.cardNumberFieldValue(randomInfo.getNumber());
        creditPage.monthFieldValue(randomInfo.getMonth());
        creditPage.yearFieldValue(randomInfo.getYear());
        creditPage.nameFieldValue(randomInfo.getName());
        creditPage.cvvFieldValue("000");
        creditPage.sendFieldClick();
        creditPage.wrongFormat();
    }
}


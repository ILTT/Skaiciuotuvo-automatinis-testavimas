import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class skaiciuotuvasTestNG {
    String regValidUsername, regInvalidUsername;
    String regValidPassword, regInvalidPassword, regValidConfirmPassword, regInvalidConfirmPassword;
    String loginValidUsername, loginInvalidUsername;
    String loginValidPassword, loginInvalidPassword;
    String validCalcFirst, validCalcSecond;
    String invalidCalcFirst, invalidCalcSecond;

    @BeforeMethod
    public void browserSetup() {
        testcommands.setup();
        //Informacija kuria vedame testuojant

        //Registracijos info validi
        regValidUsername = "foobaa";
        regValidPassword = "bar";
        regValidConfirmPassword = "bar";
        //Registracijos info nevalidi
        regInvalidUsername = "fo";
        regInvalidPassword = "ba";
        regInvalidConfirmPassword = "ba";
        //Prisijungimo info validi
        loginValidUsername = "foob";
        loginValidPassword = "bar";
        //Prisijungimo info nevalidi
        loginInvalidUsername = "ewfgdhnm";
        loginInvalidPassword = "ftyvgbhnjm,";
        //Skaiciavimo info validi
        validCalcFirst = "2";
        validCalcSecond = "5";
        //Skaiciavimo info nevalidi
        invalidCalcFirst = "1";
        invalidCalcSecond = "-1";
    }

    @Test(priority = 1)
    public void positiveRegisteringTest() {
        Assert.assertEquals("Vartotoja sukurti pavyko", testcommands.checkIfRegisteringWorked(regValidUsername, regValidPassword, regValidConfirmPassword));
    }

    @Test(priority = 2)
    public void negativeRegisteringTest() {
        Assert.assertEquals("Vartotojo sukurti nepavyko", testcommands.checkIfRegisteringWorked(regInvalidUsername, regInvalidPassword, regInvalidConfirmPassword));
    }

    @Test(priority = 3)
    public void positiveLoginTest() {
        Assert.assertEquals("Prisijungti pavyko", testcommands.checkIfLoginWorked(loginValidUsername, loginValidPassword));
    }

    @Test(priority = 4)
    public void negativeLoginTest() {
        Assert.assertEquals("Prisijungti nepavyko", testcommands.checkIfLoginWorked(loginInvalidUsername, loginInvalidPassword));
    }

    @Test(priority = 5)
    public void positiveNewCalculationTest() {
        testcommands.tryLoggingIn(loginValidUsername, loginValidPassword);
        Assert.assertEquals("Suskaiciuoti pavyko", testcommands.checkIfCalculatorWorked(validCalcFirst, validCalcSecond));
    }

    @Test(priority = 6)
    public void negativeNewCalculationTest() {
        testcommands.tryLoggingIn(loginValidUsername, loginValidPassword);
        Assert.assertEquals("Suskaiciuoti nepavyko", testcommands.checkIfCalculatorWorked(invalidCalcFirst, invalidCalcSecond));
    }

    //Siems testams perduodamas boolean, kuris nusprendzia ar sutinkam istrinti pirma numeri. true = taip, false = ne
    @Test(priority = 7)
    public void positiveDeletionTest() {
        testcommands.tryLoggingIn(loginValidUsername, loginValidPassword);
        Assert.assertEquals("Istrinti pavyko", testcommands.checkDeletion(true));
    }

    @Test(priority = 8)
    public void negativeDeletionTest() {
        testcommands.tryLoggingIn(loginValidUsername, loginValidPassword);
        Assert.assertEquals("Istrinti nepavyko", testcommands.checkDeletion(false));
    }

    @Test(priority = 9)
    public void positiveChangeTest() {
        testcommands.tryLoggingIn(loginValidUsername, loginValidPassword);
        Assert.assertEquals("Pakeisti pavyko", testcommands.checkIfResultsChanged(95));
    }

    @Test(priority = 10)
    public void negativeChangeTest() {
        testcommands.tryLoggingIn(loginValidUsername, loginValidPassword);
        Assert.assertEquals("Pakeisti Nepavyko", testcommands.checkIfResultsChanged(95));
    }

    @AfterMethod
    public void browserClosing() {
        testcommands.closeBrowser();
    }
}
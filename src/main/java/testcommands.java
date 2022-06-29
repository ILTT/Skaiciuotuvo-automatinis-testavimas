import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class testcommands {
    static WebDriver browser;

    public static void main(String[] args) {
    }

    public static void setup() {
        System.out.println("Pradedama egzamino programa");
        //Sujungiame webdriveri su kodu
        System.setProperty("webdriver.chrome.driver", "webdrivers/chromedriver103.exe");
        //Sukuriamas webdriverio elementas kuri naudosime ieskant elementu browseri
        browser = new ChromeDriver();
        //Prisijungiame prie skaicuotuvo web-page'o
        browser.get("http://localhost:8080/prisijungti");
    }

    public static void tryRegistering(String newUserName, String newUserPassword, String newUserConfirmPassword) {
        //Surandamas "Sukurti naują paskyrą" mygtukas ir paspaudziamas
        browser.findElement(By.cssSelector("body > div > form > div > h4 > a")).click();
        //Surandami naujo vartotojo prisijungimo vardo,Slaptazodzio ir slaptazodzio patvirtinimo field'ai
        WebElement usernameField = browser.findElement(By.cssSelector("#username"));
        WebElement passwordField = browser.findElement(By.cssSelector("#password"));
        WebElement passwordConfirm = browser.findElement(By.cssSelector("#passwordConfirm"));
        //Ivedami norima informacija prisijungimo vardo,Slaptazodzio ir slaptazodzio patvirtinimo eiliskumu
        //Informacija bus perduodama per testo kvietima
        usernameField.sendKeys(newUserName);
        passwordField.sendKeys(newUserPassword);
        passwordConfirm.sendKeys(newUserConfirmPassword);
        //Surandamas submit mygtukas ir paspaudziamas
        browser.findElement(By.cssSelector("button")).click();
    }

    public static String checkIfRegisteringWorked(String newUserName, String newUserPassword, String newUserConfirmPassword) {
        tryRegistering(newUserName, newUserPassword, newUserConfirmPassword);
        //Sekundei palaukiam ir leidziam puslapiui uzsikraut
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        if (browser.findElements(By.cssSelector("body > nav > div > ul.nav.navbar-nav.navbar-right > a")).size() != 0) {
            return "Vartotoja sukurti pavyko";
        } else {
            return "Vartotojo sukurti nepavyko";
        }

    }

    public static void tryLoggingIn(String existingUserName, String existingUserPassword) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        //Surandami naujo vartotojo prisijungimo vardo ir Slaptazodzio Field'ai
        WebElement usernameField = browser.findElement(By.name("username"));
        WebElement passwordField = browser.findElement(By.name("password"));
        //I field'us Ivedama informacija
        usernameField.sendKeys(existingUserName);
        passwordField.sendKeys(existingUserPassword);
        //surandamas ir paspaudziamas prisijungti mygtukas
        browser.findElement(By.xpath("/html/body/div/form/div/button")).click();
    }

    public static String checkIfLoginWorked(String existingUserName, String existingUserPassword) {
        tryLoggingIn(existingUserName, existingUserPassword);
        //Pasiziurima ar egzistuoja elementas su tekstu "Logout, Vardas", kuo validuojama ar prisijungeme
        if (browser.findElements(By.linkText("Logout, " + existingUserName)).size() != 0) {
            return "Prisijungti pavyko";
        } else {
            return "Prisijungti nepavyko";
        }
    }

    public static void tryCalculator(String firstNumber, String secondNumber) {
        WebElement firstNum = browser.findElement(By.cssSelector("#sk1"));
        WebElement secondNum = browser.findElement(By.cssSelector("#sk2"));
        firstNum.sendKeys(firstNumber);
        secondNum.sendKeys(secondNumber);
        WebElement selectionBox = browser.findElement(By.name("zenklas"));
        selectionBox.click();
        browser.findElement(By.xpath("//*[@id=\"number\"]/select/option[2]")).click();
        WebElement submitButton = browser.findElement(By.xpath("//*[@id=\"number\"]/input[3]"));
        submitButton.click();
    }

    public static String checkIfCalculatorWorked(String firstNumber, String secondNumber) {
        tryCalculator(firstNumber, secondNumber);
        if (browser.findElements(By.cssSelector("h4")).size() != 0) {
            return "Suskaiciuoti pavyko";
        } else {
            return "Suskaiciuoti nepavyko";
        }
    }

    public static String checkDeletion(boolean wantToDelete) {
        browser.findElement(By.linkText("Atliktos operacijos")).click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        int startingList = browser.findElements(By.cssSelector("tr")).size();
        System.out.println(startingList);
        WebElement firstDeleteButton = browser.findElement(By.xpath("/html/body/div/table/tbody/tr[2]/td[5]/a[2]"));
        firstDeleteButton.click();
        if (wantToDelete) {
            browser.switchTo().alert().accept();
        } else {
            browser.switchTo().alert().dismiss();
        }
        int finishingList = browser.findElements(By.cssSelector("tr")).size();
        System.out.println(finishingList);
        if (finishingList < startingList) {
            return "Istrinti pavyko";
        } else {
            return "Istrinti nepavyko";
        }
    }

    public static String tryChangingResults(int changeTo) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        browser.findElement(By.linkText("Atliktos operacijos")).click();
        String startingNumber = browser.findElement(By.xpath("/html/body/div/table/tbody/tr[2]/td[4]")).getText();
        WebElement keistButton = browser.findElement(By.xpath("/html/body/div/table/tbody/tr[2]/td[5]/a[1]"));
        keistButton.click();
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        WebElement rezultatasInput = browser.findElement(By.xpath("//*[@id=\"command\"]/p[4]/input"));
        String changeToString = String.valueOf(changeTo);
        rezultatasInput.clear();
        rezultatasInput.sendKeys(changeToString);
        WebElement atnaujintiButton = browser.findElement(By.xpath("//*[@id=\"command\"]/p[5]/input"));
        atnaujintiButton.click();
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        return (startingNumber);
    }

    public static String checkIfResultsChanged(int changeTo) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        String startingNumber = tryChangingResults(changeTo);
        String changedNumber = browser.findElement(By.xpath("/html/body/div/table/tbody/tr[5]/td[2]")).getText();
        System.out.println(startingNumber);
        System.out.println(changedNumber);
        if (!startingNumber.equals(String.valueOf(changedNumber))) {
            return "Pakeisti pavyko";
        } else {
            return "Pakeisti Nepavyko";
        }
    }

    public static void closeBrowser() {
        browser.close();
    }
}
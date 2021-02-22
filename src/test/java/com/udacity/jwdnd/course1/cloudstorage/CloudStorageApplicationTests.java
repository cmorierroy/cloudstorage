package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void getHomePageUnauthenticated()
	{
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertNotEquals("Home",driver.getTitle());
	}

	@Test
	public void signUpLoginAccessHomeLogout() throws InterruptedException
	{
		//create account
		driver.get("http://localhost:" + this.port + "/signup");
		SignUpPage signUpPage = new SignUpPage(driver);
		Thread.sleep(1000);
		signUpPage.signUpUser("Johnny", "Rico", "BigGunz","trooper4Life");
		Thread.sleep(1000);

		//log in
		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		Thread.sleep(1000);
		loginPage.loginUser("BigGunz", "trooper4Life");
		Thread.sleep(1000);

		//test home page and logout
		driver.get("http://localhost:" + this.port + "/home");
		HomePage homePage = new HomePage(driver);
		Assertions.assertEquals("Home", driver.getTitle());
		Thread.sleep(1000);
		homePage.logout();
		Thread.sleep(1000);

		//test no auth for home page
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertNotEquals("Home",driver.getTitle());
	}

	@Test
	public void verifyNoteCreation() throws InterruptedException
	{
		//create account
		driver.get("http://localhost:" + this.port + "/signup");
		SignUpPage signUpPage = new SignUpPage(driver);
		Thread.sleep(1000);
		signUpPage.signUpUser("Luke", "Skywalker", "saber83","d4ddy!55ues");
		Thread.sleep(1000);

		//log in
		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		Thread.sleep(1000);
		loginPage.loginUser("saber83", "d4ddy!55ues");
		Thread.sleep(1000);

		//create note
		driver.get("http://localhost:" + this.port + "/home");
		HomePage homePage = new HomePage(driver);

		WebDriverWait wait = new WebDriverWait(driver, 200);
		homePage.goToNotesTab();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("addNoteButton"))).click();
		Thread.sleep(1000);
		homePage.inputNoteInfo("activities", "-go to Tosche station to pick up some power converters");
		Thread.sleep(1000);

		//verify note details
		driver.get("http://localhost:" + this.port + "/home");
		homePage.goToNotesTab();
		Thread.sleep(1000);
		Assertions.assertEquals("activities", homePage.noteTitleText());
		Assertions.assertEquals("-go to Tosche station to pick up some power converters", homePage.noteDescriptionText());
	}

	@Test
	public void verifyNoteEdition() throws InterruptedException
	{
		//create account
		driver.get("http://localhost:" + this.port + "/signup");
		SignUpPage signUpPage = new SignUpPage(driver);
		Thread.sleep(1000);
		signUpPage.signUpUser("Bertrand", "Russell", "teapot","teapot");
		Thread.sleep(1000);

		//log in
		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		Thread.sleep(1000);
		loginPage.loginUser("teapot", "teapot");
		Thread.sleep(1000);

		//create note
		driver.get("http://localhost:" + this.port + "/home");
		HomePage homePage = new HomePage(driver);

		WebDriverWait wait = new WebDriverWait(driver, 200);
		homePage.goToNotesTab();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("addNoteButton"))).click();
		Thread.sleep(1000);
		homePage.inputNoteInfo("thoughts", "this sentence is false");
		Thread.sleep(1000);

		//edit note
		driver.get("http://localhost:" + this.port + "/home");
		homePage.goToNotesTab();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-edit"))).click();
		Thread.sleep(1000);
		homePage.inputNoteInfo("thoughts", "this sentence is true");
		Thread.sleep(1000);

		//verify note details
		driver.get("http://localhost:" + this.port + "/home");
		homePage.goToNotesTab();
		Thread.sleep(1000);
		Assertions.assertEquals("thoughts", homePage.noteTitleText());
		Assertions.assertEquals("this sentence is true", homePage.noteDescriptionText());
	}

	@Test
	public void verifyNoteDeletion() throws InterruptedException
	{
		//create account
		driver.get("http://localhost:" + this.port + "/signup");
		SignUpPage signUpPage = new SignUpPage(driver);
		Thread.sleep(1000);
		signUpPage.signUpUser("z", "z", "z","z");
		Thread.sleep(1000);

		//log in
		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		Thread.sleep(1000);
		loginPage.loginUser("z", "z");
		Thread.sleep(1000);

		//create note
		driver.get("http://localhost:" + this.port + "/home");
		HomePage homePage = new HomePage(driver);

		WebDriverWait wait = new WebDriverWait(driver, 200);
		homePage.goToNotesTab();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("addNoteButton"))).click();
		Thread.sleep(1000);
		homePage.inputNoteInfo("z", "z");
		Thread.sleep(1000);

		//delete note
		driver.get("http://localhost:" + this.port + "/home");
		homePage.goToNotesTab();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-delete"))).click();
		Thread.sleep(1000);

		//verify note details
		driver.get("http://localhost:" + this.port + "/home");
		homePage.goToNotesTab();
		Thread.sleep(1000);

		Assertions.assertFalse(driver.findElements(By.id("noteTitle")).size() > 0);
	}

	@Test
	public void verifyCredentialCreation() throws InterruptedException
	{
		//create account
		driver.get("http://localhost:" + this.port + "/signup");
		SignUpPage signUpPage = new SignUpPage(driver);
		Thread.sleep(1000);
		signUpPage.signUpUser("Melon", "Usk", "cashmoney","savetheworld");
		Thread.sleep(1000);

		//log in
		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		Thread.sleep(1000);
		loginPage.loginUser("cashmoney", "savetheworld");
		Thread.sleep(1000);

		//create credential
		driver.get("http://localhost:" + this.port + "/home");
		HomePage homePage = new HomePage(driver);

		WebDriverWait wait = new WebDriverWait(driver, 200);
		homePage.goToCredentialsTab();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("addCredentialButton"))).click();
		Thread.sleep(1000);
		homePage.inputCredentialInfo("www.tesla.com", "rocketz", "payp4lr0x");
		Thread.sleep(1000);

		//verify credential details
		driver.get("http://localhost:" + this.port + "/home");
		homePage.goToCredentialsTab();
		Thread.sleep(1000);
		Assertions.assertEquals("www.tesla.com", homePage.credentialUrlText());
		Assertions.assertEquals("rocketz", homePage.credentialUsernameText());

		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-edit"))).click();
		Assertions.assertEquals("payp4lr0x", homePage.credentialPasswordInputText());
	}

	@Test public void verifyCredentialEdition() throws InterruptedException
	{
		//create account
		driver.get("http://localhost:" + this.port + "/signup");
		SignUpPage signUpPage = new SignUpPage(driver);
		Thread.sleep(1000);
		signUpPage.signUpUser("elon", "tusk", "cybertruck","savetheworld");
		Thread.sleep(1000);

		//log in
		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		Thread.sleep(1000);
		loginPage.loginUser("cybertruck", "savetheworld");
		Thread.sleep(1000);

		//create credential
		driver.get("http://localhost:" + this.port + "/home");
		HomePage homePage = new HomePage(driver);

		WebDriverWait wait = new WebDriverWait(driver, 200);
		homePage.goToCredentialsTab();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("addCredentialButton"))).click();
		Thread.sleep(1000);
		homePage.inputCredentialInfo("www.tesla.com", "rocketz", "payp4lr0x");
		Thread.sleep(1000);

		//edit credential details
		driver.get("http://localhost:" + this.port + "/home");
		homePage.goToCredentialsTab();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-edit"))).click();
		Thread.sleep(1000);
		homePage.inputCredentialInfo("www.spacex.com", "carz", "billions");
		Thread.sleep(1000);

		//verify edition
		driver.get("http://localhost:" + this.port + "/home");
		homePage.goToCredentialsTab();
		Thread.sleep(1000);
		Assertions.assertEquals("www.spacex.com", homePage.credentialUrlText());
		Assertions.assertEquals("carz", homePage.credentialUsernameText());

		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-edit"))).click();
		Assertions.assertEquals("billions", homePage.credentialPasswordInputText());
	}

	@Test public void verifyCredentialDeletion() throws InterruptedException
	{
		//create account
		driver.get("http://localhost:" + this.port + "/signup");
		SignUpPage signUpPage = new SignUpPage(driver);
		Thread.sleep(1000);
		signUpPage.signUpUser("nole", "Sku", "mars","mars");
		Thread.sleep(1000);

		//log in
		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		Thread.sleep(1000);
		loginPage.loginUser("mars", "mars");
		Thread.sleep(1000);

		//create credential
		driver.get("http://localhost:" + this.port + "/home");
		HomePage homePage = new HomePage(driver);

		WebDriverWait wait = new WebDriverWait(driver, 200);
		homePage.goToCredentialsTab();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("addCredentialButton"))).click();
		Thread.sleep(1000);
		homePage.inputCredentialInfo("boring", "stonks", "drillz");
		Thread.sleep(1000);

		//delete credential details
		driver.get("http://localhost:" + this.port + "/home");
		homePage.goToCredentialsTab();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-delete"))).click();
		Thread.sleep(1000);
		driver.get("http://localhost:" + this.port + "/home");
		homePage.goToCredentialsTab();
		Thread.sleep(1000);

		Assertions.assertFalse(driver.findElements(By.id("credentialUrl")).size() > 0);
	}
}

package webAutomation.LoginTest.base;

import java.lang.reflect.Method;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;

public class BaseTest 
{
	protected WebDriver driver;
	protected Logger log;
	
	protected String testSuiteName;
	protected String testName;
	protected String testMethodName;

	@BeforeMethod(alwaysRun = true)
	public void setUp(Method method, ITestContext context) 
	{
		
			String testname = context.getCurrentXmlTest().getName();
			String log4jConfPath ="src/main/resources/log4j.properties";
			PropertyConfigurator.configure(log4jConfPath);
			log = LogManager.getLogger(testname);
		    // Create driver
		    log.info("Creating driver");		
			System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
			driver = new ChromeDriver();
		    driver.manage().window().maximize();
		    
		    this.testSuiteName = context.getSuite().getName();
			this.testName = testname;
			this.testMethodName = method.getName();
		    
		    log.info("Launching website");
			
			// Open URL
			String url = "http://the-internet.herokuapp.com/";
			driver.get(url);
			log.info("Main page is opened.");
			
			
			//Open form authentication link
			driver.findElement(By.linkText("Form Authentication")).click();
	}
		
	@AfterMethod(alwaysRun = true)
	public void tearDown() 
	{
		log.info("Close driver");
		// Close browser
		driver.quit();
	}
	
	
	@AfterSuite
	public void sendMail()
	{
		 final String username = "sanjivanikesale12@gmail.com";
		 final String password = "7385401676";

		    Properties props = new Properties();
		    props.put("mail.smtp.auth", true);
		    props.put("mail.smtp.starttls.enable", true);
		    props.put("mail.smtp.host", "smtp.gmail.com");
		    props.put("mail.smtp.port", "587");

		    Session session = Session.getInstance(props,
		            new javax.mail.Authenticator() {
		                protected PasswordAuthentication getPasswordAuthentication() {
		                    return new PasswordAuthentication(username, password);
		                }
		            });

		    try 
		    {
		        Message message = new MimeMessage(session);
		        message.setFrom(new InternetAddress(username));
		        message.setRecipients(Message.RecipientType.TO,
		                InternetAddress.parse(username));
		        message.setSubject("Testing Subject");
		        message.setText("PFA");

		        MimeBodyPart messageBodyPart = new MimeBodyPart();

		        Multipart multipart = new MimeMultipart();

		        messageBodyPart = new MimeBodyPart();
		        String file = "test-output";
		        String fileName = "emailable-report.html";
		        DataSource source = new FileDataSource(file);
		        messageBodyPart.setDataHandler(new DataHandler(source));
		        messageBodyPart.setFileName(fileName);
		        multipart.addBodyPart(messageBodyPart);

		        message.setContent(multipart);

		        System.out.println("Sending");

		        Transport.send(message);

		        System.out.println("Done");

		    } 
		    catch (MessagingException e)
		    {
		        e.printStackTrace();
		    }
	}	
}
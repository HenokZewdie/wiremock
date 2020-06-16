package kafka.csv.wiremock.functionalTesting;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "html:target/cucumber", "json:target/cucumber/cucumber.json",
        "junit:target/cucumber/cucumber.xml"},
        features = "src/test/java/kafka/csv/wiremock/functionalTesting/feature",
        glue = "stepDefinition",
        strict = true)

public class FunctionalTestWithWiremockRunner {
    private static WireMockServer mockServer;

    @BeforeClass
    public static void setup() {
        WireMockConfiguration configuration = new WireMockConfiguration();
        configuration.port(2131);
        configuration.usingFilesUnderClasspath("src/test/resources/mock");
        mockServer = new WireMockServer(configuration);
        mockServer.start();
    }

    @AfterClass
    public static void tearDown() {
        mockServer.stop();
    }

}

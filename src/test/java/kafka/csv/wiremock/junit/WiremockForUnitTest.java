package kafka.csv.wiremock.junit;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class WiremockForUnitTest {
    private static WireMockServer mockServer;

    @BeforeClass
    public static void setup() {
        WireMockConfiguration configuration = new WireMockConfiguration();
        configuration.port(2131);
        configuration.usingFilesUnderClasspath("src/java/resources/mock");
        mockServer = new WireMockServer(configuration);
        mockServer.start();
    }

    @AfterClass
    public static void tearDown() {
        mockServer.stop();
    }

}

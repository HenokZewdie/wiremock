package kafka.csv.wiremock.config;


import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WiremockConfig {
    private static WireMockServer mockServer;

    @Bean
    public static void setup() {
        WireMockConfiguration configuration = new WireMockConfiguration();
        configuration.port(8089);
        configuration.usingFilesUnderClasspath("src/main/resources/mock");
        mockServer = new WireMockServer(configuration);
        mockServer.start();
    }
}

package kafka.csv.wiremock.junit;

import kafka.csv.wiremock.config.WiremockConfig;
import kafka.csv.wiremock.kafka.SubscriptionAndUserDetailsToStoreIntoTheDB;
import kafka.csv.wiremock.service.AccountBillingService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@RunWith(SpringRunner.class)
public class ServiceTest {

    //Need to properly update the String for both onStarProfileSubscription and User
    private final String response = "{\"onStarProfileSubscription\": {\"userId\": \"817a2a40-838b-11e8-a14b-115b12bf7517\",\"accountId\": 10,\"accountType\": \"accountType\",\"enrollmentId\": 1},\"users\": {\"size\": 1,\"offset\": 0,\"limit\": 10,\"results\": [{\"userId\": \"817a2a40-838b-11e8-a14b-115b12bf7517\",\"firstName\": \"jane\",\"middleName\": \"janice\",\"lastName\": \"doe\",\"title\": \"ms.\",\"nameSuffix\": \"jr.\",\"phones\": [{\"number\": \"+12345678900\",\"type\": \"HOME\",\"extension\": null},{\"number\": \"+12345678901\",\"type\": \"WORK\",\"extension\": \"1234\"},{\"number\": \"+12345678921\",\"type\": \"MOBILE\",\"extension\": null}],\"emailAddress\": \"sample@gmail.com\",\"preferredLanguage\": \"EN_US\",\"addresses\": [{\"type\": \"HOME\",\"line1\": \"3909 Woodward Ave\",\"line2\": \"Apt 4\",\"city\": \"Detroit\",\"stateOrProvince\": \"MI\",\"country\": \"US\",\"postalCode\": \"48201\"},{\"type\": \"MAILING\",\"line1\": \"3909 Woodward Ave\",\"line2\": \"Apt 4\",\"city\": \"Detroit\",\"stateOrProvince\": \"MI\",\"country\": \"US\",\"postalCode\": \"48201\"}],\"gcin\": null}]}}";
    @Mock
    RestTemplate restTemplate;
    @InjectMocks
    AccountBillingService billingService;

    @Before
    public void setup() {
        billingService = new AccountBillingService();
        WiremockConfig.setup();
    }

    @Test
    public void test() throws IOException {
        BDDMockito.given(restTemplate.exchange(BDDMockito.anyString(), Mockito.eq(HttpMethod.GET), BDDMockito.any(),
                Mockito.eq(String.class))).willReturn(buildResponse());
        //Mockito.when(restTemplate.exchange(anyString(), HttpMethod.GET, any(), String.class)).thenReturn(buildResponse());
        SubscriptionAndUserDetailsToStoreIntoTheDB serviceResponse = billingService.getUsersDetails();
        Assert.assertNotNull(serviceResponse);
        Assert.assertNotNull(serviceResponse.getUsers());
    }

    private ResponseEntity<String> buildResponse() {
        return new ResponseEntity<String>(response, new HttpHeaders(), HttpStatus.OK);
    }

}

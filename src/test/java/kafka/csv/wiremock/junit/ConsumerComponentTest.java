package kafka.csv.wiremock.junit;

import kafka.csv.wiremock.controller.KafkaController;
import kafka.csv.wiremock.kafka.SubscriptionAndUserDetailsToStoreIntoTheDB;
import kafka.csv.wiremock.service.AccountBillingService;
import net.mguenther.kafka.junit.EmbeddedKafkaCluster;
import net.mguenther.kafka.junit.KeyValue;
import net.mguenther.kafka.junit.SendKeyValues;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static net.mguenther.kafka.junit.EmbeddedKafkaCluster.provisionWith;
import static net.mguenther.kafka.junit.EmbeddedKafkaClusterConfig.useDefaults;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConsumerComponentTest {

    @Rule
    public EmbeddedKafkaCluster cluster = provisionWith(useDefaults());
    List<KeyValue<String, SubscriptionAndUserDetailsToStoreIntoTheDB>> test;
    @Autowired
    KafkaController kafkaController;
    @Autowired
    AccountBillingService service;
    @Mock
    KafkaTemplate kafkaTemplate;
    @Mock
    MongoTemplate mongoTemplate;

    @Test
    public void shouldWaitForRecordsToBePublished() throws Exception {
        List<KeyValue<String, SubscriptionAndUserDetailsToStoreIntoTheDB>> records = new ArrayList<>();
        SubscriptionAndUserDetailsToStoreIntoTheDB testa = kafkaController.getAllUser();
        records.add(new KeyValue<>("I don't know what this key is", testa));
        SendKeyValues<String, SubscriptionAndUserDetailsToStoreIntoTheDB> sendRequest =
                SendKeyValues.to("GreatExample", records).useDefaults();

        System.out.println("TESTTTTTTT" + sendRequest);
    }
}
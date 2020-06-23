package kafka.csv.wiremock.junit;

import kafka.csv.wiremock.controller.KafkaController;
import kafka.csv.wiremock.kafka.SubscriptionAndUserDetailsToStoreIntoTheDB;
import kafka.csv.wiremock.service.AccountBillingService;
import net.mguenther.kafka.junit.EmbeddedKafkaCluster;
import net.mguenther.kafka.junit.KeyValue;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    MongoTemplate mongoTemplate;

    //https://mguenther.github.io/kafka-junit/
    @Test
    public void shouldWaitForRecordsToBePublished() throws Exception {
        List<KeyValue<String, SubscriptionAndUserDetailsToStoreIntoTheDB>> records = new ArrayList<>();
        SubscriptionAndUserDetailsToStoreIntoTheDB response = kafkaController.getAllUser();
        kafkaTemplate.send("GreatExample",response);
       /* records.add(new KeyValue<>("I don't know what this key is, probably EventName newe yemihonew", response));
        SendKeyValues<String, SubscriptionAndUserDetailsToStoreIntoTheDB> sendRequest =
                SendKeyValues.to("GreatExample", records).useDefaults();*/
        TimeUnit.SECONDS.sleep(5);

        List<SubscriptionAndUserDetailsToStoreIntoTheDB> newTest =
                mongoTemplate.find(new Query(Criteria.where("id").is(0)), SubscriptionAndUserDetailsToStoreIntoTheDB.class);
        Assert.assertTrue(newTest.stream().anyMatch(a->a.getOnStarProfileSubscription().getUserId()
                .equalsIgnoreCase("817a2a40-838b-11e8-a14b-115b12bf7517")));
    }
}
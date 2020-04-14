package kafka.csv.wiremock.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import kafka.csv.wiremock.kafka.SubscriptionAndUserDetailsToStoreIntoTheDB;
import kafka.csv.wiremock.service.AccountBillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class KafkaController {

	private static final String TOPIC = "GreatExample";

	@Autowired
	private final KafkaTemplate<String, SubscriptionAndUserDetailsToStoreIntoTheDB> kafkaTemplate;

	@Autowired
	AccountBillingService service;

	@Autowired
	MongoTemplate mongoTemplate;

	public KafkaController(KafkaTemplate<String, SubscriptionAndUserDetailsToStoreIntoTheDB> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	@RequestMapping(value = "/getUsersDetails", method = RequestMethod.GET)
	public SubscriptionAndUserDetailsToStoreIntoTheDB getAllUser() throws JsonParseException, JsonMappingException, IOException {
		SubscriptionAndUserDetailsToStoreIntoTheDB detailsToStoreIntoTheDB = null;
		String response = "ERROR on STORING TO THE DB";
		try {
			detailsToStoreIntoTheDB = service.getUsersDetails();
			response = "Successfully stored in the DB";
		} catch (Exception e) {

		}

		post(detailsToStoreIntoTheDB);
		return detailsToStoreIntoTheDB;
	}

	//TODO ... instead of calling the api, pass the SubscriptionAndUserDetailsToStoreIntoTheDB to this method to publish
	//@RequestMapping(value = "/publish", method = RequestMethod.GET)
	public void  post(SubscriptionAndUserDetailsToStoreIntoTheDB detailsToStoreIntoTheDB) throws JsonParseException, JsonMappingException, IOException {
		//No need to use the next line if SubscriptionAndUserDetailsToStoreIntoTheDB is passed
		//SubscriptionAndUserDetailsToStoreIntoTheDB detailsToStoreIntoTheDB = service.getUsersDetails(userId);
		kafkaTemplate.send(TOPIC, detailsToStoreIntoTheDB);
		//return detailsToStoreIntoTheDB;
	}

	@KafkaListener(topics = TOPIC ,groupId = "group_json",
			containerFactory = "userKafkaListenerFactory")
	public void consumeJson(SubscriptionAndUserDetailsToStoreIntoTheDB  subscriptionAndUserDetailsToStoreIntoTheDB)
			throws JsonParseException, JsonMappingException, IOException {
		System.out.println("Consumed JSON Message $$$$$$$$$$: " +
				subscriptionAndUserDetailsToStoreIntoTheDB.getUsers().getLimit());
		mongoTemplate.save(subscriptionAndUserDetailsToStoreIntoTheDB);
		System.out.println("Saved to DB SUCCESSFULLY");
	}
}

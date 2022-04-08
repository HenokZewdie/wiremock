package kafka.csv.wiremock.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import kafka.csv.wiremock.kafka.SubscriptionAndUserDetailsToStoreIntoTheDB;
import kafka.csv.wiremock.service.AccountBillingService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class KafkaController {

	private static final String TOPIC = "GreatExample";

	@Autowired
	private KafkaTemplate<String, SubscriptionAndUserDetailsToStoreIntoTheDB> kafkaTemplate;

	@Autowired
	AccountBillingService service;

	@Autowired
	MongoTemplate mongoTemplate;

	public KafkaController(KafkaTemplate<String, SubscriptionAndUserDetailsToStoreIntoTheDB> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	/**
	 *
	 * @return
	 */
	@RequestMapping(value = "/getUsersDetails", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.CREATED)
	public SubscriptionAndUserDetailsToStoreIntoTheDB getAllUser() {
		SubscriptionAndUserDetailsToStoreIntoTheDB detailsToStoreIntoTheDB = null;
		try {
			detailsToStoreIntoTheDB = service.getUsersDetails();
		} catch (Exception e) {
			System.out.println(e.getMessage() + "Will create a ControllerAdvice for this");
		}

		post(detailsToStoreIntoTheDB);
		return detailsToStoreIntoTheDB;
	}

	/**
	 * This method publish an event "UserDetailEvent" in the TOPIC of SubscriptionAndUserDetailsToStoreIntoTheDB object
	 * @param detailsToStoreIntoTheDB
	 */
	public void  post(SubscriptionAndUserDetailsToStoreIntoTheDB detailsToStoreIntoTheDB) {

		kafkaTemplate.send(TOPIC, "UserDetailEvent", detailsToStoreIntoTheDB);
	}

	@KafkaListener(topics = TOPIC ,groupId = "group_json",
			containerFactory = "userKafkaListenerFactory")
	public void consumeJson(ConsumerRecord<String, SubscriptionAndUserDetailsToStoreIntoTheDB>
										subscriptionAndUserDetailsToStoreIntoTheDB)
			throws JsonParseException, JsonMappingException, IOException {
		if(subscriptionAndUserDetailsToStoreIntoTheDB.key().equalsIgnoreCase("EventNames")){
			mongoTemplate.save(subscriptionAndUserDetailsToStoreIntoTheDB.value());
			List<SubscriptionAndUserDetailsToStoreIntoTheDB> newTest =
					mongoTemplate.find(new Query(Criteria.where("id").is(0)),
							SubscriptionAndUserDetailsToStoreIntoTheDB.class);
			System.out.println("Consumed and Saved to DB SUCCESSFULLY");
		}
	}
}

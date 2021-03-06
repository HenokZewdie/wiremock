package kafka.csv.wiremock.service;

import java.io.IOException;

import kafka.csv.wiremock.kafka.OnStarProfileSubscription;
import kafka.csv.wiremock.kafka.SubscriptionAndUserDetailsToStoreIntoTheDB;
import kafka.csv.wiremock.kafka.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;

@Component
public class AccountBillingService {

	RestTemplate restTemplate = new RestTemplate();

	public SubscriptionAndUserDetailsToStoreIntoTheDB getUsersDetails() throws JsonParseException, JsonMappingException, IOException{
		SubscriptionAndUserDetailsToStoreIntoTheDB detailsToStoreIntoTheDB = new SubscriptionAndUserDetailsToStoreIntoTheDB();
		Users user;
		Gson gson = new Gson();
		HttpHeaders headers = new HttpHeaders();
		String url="http://localhost:8089/users/getUserInformation";
		//TODO... get the data from subscription
		String onSiteUrl="http://localhost:8089/getDataFromSubscription";
		HttpEntity<String>  entity = new HttpEntity<String>(headers);
		ResponseEntity<String> onStarResponseEntity = null;
		ResponseEntity<String> accountBillingResponseEntity = null;
		try
		{
			accountBillingResponseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
			onStarResponseEntity = restTemplate.exchange(onSiteUrl, HttpMethod.GET, entity, String.class);
		}catch(Exception e){
			System.out.println("ERROR#######" +  e.getMessage());
		}
		user = gson.fromJson(accountBillingResponseEntity.getBody(), Users.class);
		OnStarProfileSubscription onStarProfileSubscription = gson.fromJson(onStarResponseEntity.getBody(),
				OnStarProfileSubscription.class);
		detailsToStoreIntoTheDB.setOnStarProfileSubscription(onStarProfileSubscription);
		detailsToStoreIntoTheDB.setUsers(user);
		return detailsToStoreIntoTheDB;
		
	}
}

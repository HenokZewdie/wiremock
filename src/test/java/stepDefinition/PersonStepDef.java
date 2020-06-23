package stepDefinition;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import cucumber.api.java.en.And;
import gherkin.deps.com.google.gson.Gson;
import kafka.csv.wiremock.repo.CsvRepo;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import kafka.csv.wiremock.csv.CsvEntity;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class PersonStepDef {

    RestTemplate restTemplate;
    @Autowired
    CsvRepo csvRepo;
    
    ResponseEntity<String> responseEntity = null;
    String response = null;

    CsvEntity csvEntity = new CsvEntity();

    @Given("^A person$")
    public void a_person() throws Throwable {
        restTemplate = new RestTemplate();
        CsvEntity csvEntity = wiremockTestAndConvertToClass();
        assertEquals(6, csvEntity.getAge());
    }

    @When("^the api is invoked$")
    public void the_api_is_invoked() throws Throwable {
        responseEntity = callServiceApi();
        response = responseEntity.getBody();
    }

    @Then("^the response code is \"([^\"]*)\"$")
    public void the_response_code_is(int arg1) throws Throwable {
        assertEquals(arg1, responseEntity.getStatusCode().value());
    }

    @And("^returned value is \"([^\"]*)\"$")
    public void returnedValueIs(String arg0) {
        assertTrue(arg0.equalsIgnoreCase(response));
    }

    public ResponseEntity<String> callServiceApi() {
        HttpHeaders headers = new HttpHeaders();
        String url = "http://localhost:2000/readcsv";
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = null;
        String response = null;

        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        } catch (Exception e) {
            System.out.println("#######" + e.getMessage());
        }
        return responseEntity;
    }

    private CsvEntity wiremockTestAndConvertToClass() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet("http://localhost:2131/wiremock/getperson");
        HttpResponse httpResponse = httpClient.execute(request);
        InputStream inputStream = httpResponse.getEntity().getContent();
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        String string = scanner.useDelimiter("\\Z").next();
        scanner.close();
        Gson gson = new Gson();
        CsvEntity csvEntity = gson.fromJson(string, CsvEntity.class);
        return csvEntity;
    }
}

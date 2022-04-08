package kafka.csv.wiremock.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kafka.csv.wiremock.model.EmployeeInfo;
import kafka.csv.wiremock.repo.EmployeeInfoRepo;
import kafka.csv.wiremock.model.EmployeeResponse;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

@RestController
public class EmployeeController {

    private static final String TOPIC = "Employee_Topic";

    @Autowired
    EmployeeInfoRepo infoRepo;

    @Autowired
    private KafkaTemplate<String, EmployeeInfo> kafkaEmployeeTemplate;

    public EmployeeController(KafkaTemplate<String, EmployeeInfo> kafkaEmployeeTemplate) {
        this.kafkaEmployeeTemplate = kafkaEmployeeTemplate;
    }

    @RequestMapping(value = "/tokafka", method = RequestMethod.GET)
    public EmployeeResponse dbInsertion() throws IOException, InterruptedException {
        ObjectMapper objectMapper = new ObjectMapper();
        EmployeeResponse employeeResponse = new ObjectMapper().readValue(
                new File("/Users/henokwordoffa/IdeaProjects/wiremock/src/main/resources/response.json")
                , EmployeeResponse.class);
        for(EmployeeInfo publishEmployeeObject: employeeResponse.getData()){
            kafkaEmployeeTemplate.send(TOPIC, publishEmployeeObject);
            Thread.sleep(3000L);
        }
        return employeeResponse;
    }

    @KafkaListener(topics = TOPIC ,groupId = "group_json",
            containerFactory = "employeeKafkaListenerFactory")
    public void consumeJson(ConsumerRecord<String, EmployeeInfo>
                                    employeeDataListened){
        infoRepo.save(employeeDataListened.value());
        System.out.println(employeeDataListened.value().employee_name);
    }

}

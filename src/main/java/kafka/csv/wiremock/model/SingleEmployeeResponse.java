package kafka.csv.wiremock.model;

import lombok.Data;

@Data
public class SingleEmployeeResponse {
    private String status;
    private EmployeeInfo data;
}
